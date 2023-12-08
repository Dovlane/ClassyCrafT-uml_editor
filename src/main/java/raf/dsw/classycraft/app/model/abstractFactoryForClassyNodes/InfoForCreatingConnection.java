package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class InfoForCreatingConnection extends InfoForCreatingClassyNode {
    private String name;
    private Interclass from;
    private Diagram parent;
    private Interclass to;
    private ElementConnectionType elementConnectionType;

    public InfoForCreatingConnection(String name, Diagram parent, Interclass from, Interclass to, ElementConnectionType elementConnectionType) {
        this.name = name;
        this.from = from;
        this.parent = parent;
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
