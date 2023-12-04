package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.awt.*;

public abstract class DiagramElement extends ClassyNode {

    protected Color color;
    protected int stroke;

    public DiagramElement(String name, ClassyNode parent) {
        super(name, parent);
    }
}
