package raf.dsw.classycraft.app.model.elements.abstractFactoryElements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;
import raf.dsw.classycraft.app.model.elements.abstractFactoryElements.ElementInterclassType;

import java.awt.*;

public class InfoForCreatingInterclass {
    private String name;
    private Diagram parent;
    private Point point;
    private AccessModifiers visibility;
    private NonAccessModifiers nonAccessModifier;
    private ElementInterclassType elementInterclassType;

    public InfoForCreatingInterclass(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifier, ElementInterclassType elementInterclassType) {
        this.name = name;
        this.parent = parent;
        this.point = point;
        this.visibility = visibility;
        this.nonAccessModifier = nonAccessModifier;
        this.elementInterclassType = elementInterclassType;
    }

    public String getName() {
        return name;
    }

    public Diagram getParent() {
        return parent;
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
