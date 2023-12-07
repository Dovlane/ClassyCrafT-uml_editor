package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceElement extends Interclass {

    private List<Method> methods;

    public InterfaceElement(String name, Diagram parent) {
        super(name, parent);
        methods = new ArrayList<>();
    }

    public InterfaceElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        methods = new ArrayList<>();
    }


    // Getters and Setters
    public List<Method> getInterfaceMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        getInterfaceMethods().add(method);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
    }


    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }


}
