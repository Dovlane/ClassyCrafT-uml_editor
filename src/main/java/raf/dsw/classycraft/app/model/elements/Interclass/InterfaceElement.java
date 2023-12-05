package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceElement extends Interclass {

    private List<Method> methods;

    public InterfaceElement(String name, Diagram parent) {
        super(name, parent);
        methods = new ArrayList<>();
    }

    public InterfaceElement(String name, Diagram parent, Point point, AccessModifiers visibility) {
        super(name, parent, point, visibility);
        methods = new ArrayList<>();
    }


    // Getters and Setters
    public List<Method> getInterfaceMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

}
