package raf.dsw.classycraft.app.model.elements.abstractFactoryElements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public class InfoForCreatingConnection {
    private String name;
    private Diagram parent;
    private Interclass from;
    private Interclass to;
    private ElementConnectionType elementConnectionType;

    public InfoForCreatingConnection(String name, Diagram parent, Interclass from, Interclass to, ElementConnectionType elementConnectionType) {
        this.name = name;
        this.parent = parent;
        this.from = from;
        this.to = to;
        this.elementConnectionType = elementConnectionType;
    }

    public String getName() {
        return name;
    }

    public Diagram getParent() {
        return parent;
    }

    public Interclass getFrom() {
        return from;
    }

    public Interclass getTo() {
        return to;
    }

    public ElementConnectionType getElementConnectionType() {
        return elementConnectionType;
    }
}
