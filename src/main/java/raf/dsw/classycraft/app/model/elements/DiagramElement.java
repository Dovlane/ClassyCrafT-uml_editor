package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.awt.*;

public abstract class DiagramElement extends ClassyNode {

    public DiagramElement(String name, ClassyNode parent) {
        super(name, parent);
    }
}
