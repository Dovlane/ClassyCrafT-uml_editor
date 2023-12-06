package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;

public abstract class Interclass extends DiagramElement {

    protected AccessModifiers visibility;
    protected Point location;
    protected int boxWidth;
    protected int boxHeight;


    public Interclass(String name, Diagram parent) {
        super(name, parent);
    }

    public Interclass(String name, Diagram parent, Point point, AccessModifiers visibility) {
        super(name, parent);
        this.visibility = visibility;
        this.boxWidth = 200;
        this.boxHeight = 100;
        this.location = point;
    }


    // Getters and Setters
    public AccessModifiers getVisibility() {
        return visibility;
    }

    public void setVisibility(AccessModifiers visibility) {
        this.visibility = visibility;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }

}
