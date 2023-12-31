package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.InterfaceElementDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.ArrayList;
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

    @JsonIgnore
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String firstLine = String.format("%s %s %s %s { \n" , visibility.toString().toLowerCase(), nonAccessModifiers.toString().toLowerCase(), "interface", getPlainName());
        stringBuilder.append(firstLine);

        for (Method method : getInterfaceMethods()) {
            String stringMethod = String.format("\t%s %s %s %s();\n", method.getAccessModifiers().toString().toLowerCase(), method.getNonAccessModifiers().toString().toLowerCase(), method.getReturnType(), method.getName());
            stringBuilder.append(stringMethod);
        }

        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }
}
