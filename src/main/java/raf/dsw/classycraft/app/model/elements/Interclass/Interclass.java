package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;

public abstract class Interclass extends DiagramElement {

    protected AccessModifiers visibility;
    protected String className;
    protected Point location;

    public Interclass(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Interclass(String name, ClassyNode parent, Point location, AccessModifiers visibility, String className) {
        super(name, parent);
        this.visibility = visibility;
        this.className = className;
        this.location = location;
    }


    // Getters and Setters
    public AccessModifiers getVisibility() {
        return visibility;
    }

    public void setVisibility(AccessModifiers visibility) {
        this.visibility = visibility;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

}
