package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.ClassElementDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Connection.CardinalityEnum;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Connection.Generalization;
import raf.dsw.classycraft.app.model.elements.Connection.IAggregationAndComposition;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.*;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = ClassElementDeserializer.class)
public class ClassElement extends Interclass {

    private List<ClassContent> classContent;

    public ClassElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        classContent = new ArrayList<>();
    }

    // Create a Deep Copy Constructor
    public ClassElement(ClassElement classElement) {
        super(classElement);
        classContent = new ArrayList<>();
        for (ClassContent aClassContent: classElement.getClassContent()) {
            if (aClassContent instanceof Method) {
                addClassContent(new Method((Method) aClassContent));
            }
            else if (aClassContent instanceof Attribute) {
                addClassContent(new Attribute((Attribute) aClassContent));
            }
        }
    }

    public void addClassContent(ClassContent classContent) {
        getClassContent().add(classContent);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    public void setClassContent(List<ClassContent> classContent) {
        this.classContent = classContent;
        Notification notification = new Notification(this, NotificationType.SET);
        notifyAllSubscribers(notification);
    }
    @JsonIgnore
    public List<Attribute> getClassAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (ClassContent classContent : getClassContent()) {
            if (classContent instanceof Attribute) {
                attributes.add((Attribute) classContent);
            }
        }
        return attributes;
    }

    @JsonIgnore
    public List<Method> getClassMethods() {
        ArrayList<Method> methods = new ArrayList<>();
        for (ClassContent classContent : getClassContent()) {
            if (classContent instanceof Method) {
                methods.add((Method) classContent);
            }
        }
        return methods;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "Class-" + getPlainName();
    }

    @JsonIgnore
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Diagram diagram = (Diagram) parent;
        List<InterfaceElement> implementedInterfaces = new ArrayList<>();

        ClassElement superClass = getSuperClassAndImplementedInterfaces(implementedInterfaces, diagram);
        String extensionAndImplementations = superClassAndImplementedInterfacesToString(superClass, implementedInterfaces);
        String firstLine = String.format("%s %s %s %s%s { \n" , visibility.toString().toLowerCase(), nonAccessModifiers.toString().toLowerCase(), "class", getPlainName(), extensionAndImplementations);
        stringBuilder.append(firstLine);


        addCompositionAndAggregationAttributes(stringBuilder, diagram);
        for (Attribute attribute : getClassAttributes()) {
            String stringAttribute = String.format("\t%s %s %s %s;\n", attribute.getAccessModifiers().toString().toLowerCase(), attribute.getNonAccessModifiers().toString().toLowerCase(), attribute.getDataType(), attribute.getName());
            stringBuilder.append(stringAttribute);
        }

        HashMap<Method, Boolean> methodsOverriden = new HashMap<>();
        List<Method> methodsFromSuperClassAndInterfaces = new ArrayList<>();
        getUnimplementedMethodsFromSuperclassAndInterfaces(methodsFromSuperClassAndInterfaces, methodsOverriden, superClass, implementedInterfaces);
        for (Method method : getClassMethods()) {
            stringBuilder.append(methodToString(method, false));
        }
        for (Method method : methodsFromSuperClassAndInterfaces) {
            stringBuilder.append(methodToString(method, false));
        }

        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }

    private String methodToString(Method method ,boolean override) {
        StringBuilder stringBuilder = new StringBuilder();
        if (override)
            stringBuilder.append("@Override\n");

        String end;
        if (method.getNonAccessModifiers() == NonAccessModifiers.ABSTRACT)
            end = "();";
        else
            end = "() {};";

        String stringMethod = String.format("\t%s %s %s %s%s\n", method.getAccessModifiers().toString().toLowerCase(), method.getNonAccessModifiers().toString().toLowerCase(), method.getReturnType(), method.getName(), end);

        return stringMethod;
    }

    @JsonIgnore
    private ClassElement getSuperClassAndImplementedInterfaces(List<InterfaceElement> implementedInterfaces, Diagram diagram) {
        ClassElement superClass = null;
        for (ClassyNode diagramElement : diagram.getChildren()) {
            if (diagramElement instanceof Generalization) {
                Generalization generalization = (Generalization) diagramElement;
                if (generalization.getFrom().equals(this)) {
                    if (generalization.getTo() instanceof ClassElement) {
                        superClass = (ClassElement) generalization.getTo();
                    }
                    else if (generalization.getTo() instanceof InterfaceElement) {
                        implementedInterfaces.add((InterfaceElement) generalization.getTo());
                    }
                }
            }
        }
        return superClass;
    }

    private String superClassAndImplementedInterfacesToString(ClassElement superClass, List<InterfaceElement> implementedInterfaces) {
        StringBuilder stringBuilderForExtensionsAndImplementations = new StringBuilder();
        String extension = "";
        if (superClass != null) {
            extension = " extends " + superClass.getPlainName();
            stringBuilderForExtensionsAndImplementations.append(extension);
        }

        if (implementedInterfaces.size() > 0) {
            stringBuilderForExtensionsAndImplementations.append(" implements ");
            int n = implementedInterfaces.size();
            for (int i = 0; i < n - 1; i++) {
                InterfaceElement interfaceElement = implementedInterfaces.get(i);
                stringBuilderForExtensionsAndImplementations.append(interfaceElement.getPlainName() + ", ");
            }
            InterfaceElement lastInterfaceElement = implementedInterfaces.get(n - 1);
            stringBuilderForExtensionsAndImplementations.append(lastInterfaceElement.getPlainName());
        }

        return stringBuilderForExtensionsAndImplementations.toString();
    }

    private void addCompositionAndAggregationAttributes(StringBuilder sb, Diagram diagram) {
        for (ClassyNode diagramElement : diagram.getChildren()) {
            if (diagramElement instanceof IAggregationAndComposition) {
                IAggregationAndComposition iAggregationAndComposition = (IAggregationAndComposition) diagramElement;
                Connection connection = (Connection) diagramElement;

                if (connection.getTo().equals(this)) {
                    AccessModifiers accessModifier = iAggregationAndComposition.getAttributeAccessModifier();
                    String dataType = connection.getFrom().getPlainName().toString();
                    CardinalityEnum cardinalityEnum = iAggregationAndComposition.getCardinalityEnum();

                    if (cardinalityEnum == CardinalityEnum.ZERO_OR_MORE)
                        dataType = String.format("List<%s>", dataType);

                    String name = iAggregationAndComposition.getAttributeName();
                    String stringAttribute = String.format("\t%s %s %s;\n", accessModifier.toString().toLowerCase(), dataType, name);

                    sb.append(stringAttribute);
                }
            }
        }
    }

    @JsonIgnore
    private void getUnimplementedMethodsFromSuperclassAndInterfaces(List<Method> methodsFromSuperClassAndInterfaces, HashMap<Method, Boolean> methodsOverriden, ClassElement superClass, List<InterfaceElement> implementedInterfaces) {
        if (superClass != null) {
            for (Method method : superClass.getClassMethods()) {
                if (method.getNonAccessModifiers() == NonAccessModifiers.ABSTRACT) {
                    if (getClassMethods().contains(method))
                        methodsOverriden.put(method, true);
                    else
                        methodsFromSuperClassAndInterfaces.add(method);
                }
            }
        }
        for (InterfaceElement interfaceElement : implementedInterfaces) {
            for (Method method : interfaceElement.getInterfaceMethods()) {
                if (getClassMethods().contains(method))
                    methodsOverriden.put(method, true);
                else
                    methodsFromSuperClassAndInterfaces.add(method);
            }
        }
    }
}
