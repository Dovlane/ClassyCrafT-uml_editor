package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public class InfoForCreatingInterclass extends InfoForCreatingClassyNode{
    private String name;
    private Point point;
    private ClassyTreeItem parent;
    private AccessModifiers visibility;
    private NonAccessModifiers nonAccessModifier;
    private ElementInterclassType elementInterclassType;

    public InfoForCreatingInterclass(String name, ClassyTreeItem parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifier, ElementInterclassType elementInterclassType) {
        super(parent);
        this.name = name;
        this.point = point;
        this.parent = parent;
        this.visibility = visibility;
        this.nonAccessModifier = nonAccessModifier;
        this.elementInterclassType = elementInterclassType;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }

    public AccessModifiers getVisibility() {
        return visibility;
    }

    public NonAccessModifiers getNonAccessModifier() {
        return nonAccessModifier;
    }

    public ElementInterclassType getElementInterclassType() {
        return elementInterclassType;
    }
}
