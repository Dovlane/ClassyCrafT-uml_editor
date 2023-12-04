package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceElement extends Interclass {

    private List<Method> methods;

    public InterfaceElement(String name, ClassyNode parent) {
        super(name, parent);
        methods = new ArrayList<>();
    }

    public InterfaceElement(String name, ClassyNode parent, Point location, AccessModifiers visibility, String className) {
        super(name, parent, location, visibility, className);
        methods = new ArrayList<>();
    }


    // Getters and Setters
    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

}
