package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.InterfaceElementDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Connection.Generalization;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = InterfaceElementDeserializer.class)
public class InterfaceElement extends Interclass {

    @JsonIgnore
    private List<Method> methods;

    public InterfaceElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        methods = new ArrayList<>();
    }

    // Create a Deep Copy Constructor
    public InterfaceElement(InterfaceElement interfaceElement) {
        super(interfaceElement);
        methods = new ArrayList<>();
        for (Method aMethod: interfaceElement.getInterfaceMethods()) {
            addMethod(new Method(aMethod));
        }
    }

    public void addMethod(Method method) {
        getInterfaceMethods().add(method);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    // Special InterfaceElement getter
    public List<Method> getInterfaceMethods() {
        return methods;
    }

    public void setInterfaceMethods(List<Method> methods) {
        this.methods = methods;
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "Interface-" + getPlainName();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Diagram diagram = (Diagram) parent;
        List<InterfaceElement> implementedInterfaces = new ArrayList<>();

        getImplementedInterfaces(implementedInterfaces, diagram);
        String implementations = implementedInterfacesToString(implementedInterfaces);
        String firstLine = String.format("%s%s %s %s%s { \n" , modifiersStringHashMap.get(visibility), nonAccessModifiers.toString().toLowerCase(), "interface", getPlainName(), implementations);
        stringBuilder.append(firstLine);

        List<Method> methodsFromInterfaces = new ArrayList<>();
        getUnimplementedMethodsFromInterfaces(methodsFromInterfaces, implementedInterfaces);
        for (Method method : methods) {
            stringBuilder.append(methodToString(method));
        }
        for (Method method : methodsFromInterfaces) {
            stringBuilder.append(methodToString(method));
        }

        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }

    private String methodToString(Method method) {
        StringBuilder stringBuilder = new StringBuilder();

        String end;
        if (method.getNonAccessModifiers() == NonAccessModifiers.ABSTRACT)
            end = "();";
        else
            end = "() {};";

        String stringMethod = String.format("\t%s%s %s %s%s\n", modifiersStringHashMap.get(method.getAccessModifiers()), method.getNonAccessModifiers().toString().toLowerCase(), method.getReturnType(), method.getName(), end);

        return stringMethod;
    }

    @JsonIgnore
    private void getImplementedInterfaces(List<InterfaceElement> implementedInterfaces, Diagram diagram) {
        for (ClassyNode diagramElement : diagram.getChildren()) {
            if (diagramElement instanceof Generalization) {
                Generalization generalization = (Generalization) diagramElement;
                if (generalization.getFrom().equals(this)) {
                    if (generalization.getTo() instanceof InterfaceElement) {
                        implementedInterfaces.add((InterfaceElement) generalization.getTo());
                    }
                }
            }
        }
    }

    private String implementedInterfacesToString(List<InterfaceElement> implementedInterfaces) {
        StringBuilder stringBuilderForImplementations = new StringBuilder();

        if (implementedInterfaces.size() > 0) {
            stringBuilderForImplementations.append(" implements ");
            int n = implementedInterfaces.size();
            for (int i = 0; i < n - 1; i++) {
                InterfaceElement interfaceElement = implementedInterfaces.get(i);
                stringBuilderForImplementations.append(interfaceElement.getPlainName() + ", ");
            }
            InterfaceElement lastInterfaceElement = implementedInterfaces.get(n - 1);
            stringBuilderForImplementations.append(lastInterfaceElement.getPlainName());
        }

        return stringBuilderForImplementations.toString();
    }

    @JsonIgnore
    private void getUnimplementedMethodsFromInterfaces(List<Method> methodsFromSuperClassAndInterfaces, List<InterfaceElement> implementedInterfaces) {
        for (InterfaceElement interfaceElement : implementedInterfaces) {
            for (Method method : interfaceElement.getInterfaceMethods()) {
                methodsFromSuperClassAndInterfaces.add(method);
            }
        }
    }

}
