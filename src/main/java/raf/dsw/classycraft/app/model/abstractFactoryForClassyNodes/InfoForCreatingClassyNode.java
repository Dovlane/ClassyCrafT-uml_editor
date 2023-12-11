package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

public abstract class InfoForCreatingClassyNode {
    protected ClassyTreeItem parent;
    public InfoForCreatingClassyNode(ClassyTreeItem parent) {
        this.parent = parent;
    }

    public ClassyTreeItem getParent() {
        return parent;
    }
}
