package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;

public class EnumElement extends Interclass {

    public EnumElement(String name, ClassyNode parent) {
        super(name, parent);
    }

    public EnumElement(String name, ClassyNode parent, Point location, AccessModifiers visibility, String className) {
        super(name, parent, location, visibility, className);
    }
}
