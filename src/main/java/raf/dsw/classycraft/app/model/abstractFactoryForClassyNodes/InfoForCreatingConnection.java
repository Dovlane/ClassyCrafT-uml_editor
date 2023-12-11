package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class InfoForCreatingConnection extends InfoForCreatingClassyNode {
    private String name;
    private Interclass from;
    private ClassyTreeItem parent;
    private Interclass to;
    private ElementConnectionType elementConnectionType;

    public InfoForCreatingConnection(String name, ClassyTreeItem parent, Interclass from, Interclass to, ElementConnectionType elementConnectionType) {
        super(parent);
        this.name = name;
        this.from = from;
        this.parent = parent;
        this.to = to;
        this.elementConnectionType = elementConnectionType;
    }

    public String getName() {
        return name;
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
