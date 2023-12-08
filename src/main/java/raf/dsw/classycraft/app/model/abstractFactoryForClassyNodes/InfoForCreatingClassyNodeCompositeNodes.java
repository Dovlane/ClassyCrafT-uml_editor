package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;

public class InfoForCreatingClassyNodeCompositeNodes extends InfoForCreatingClassyNode {
    private ClassyTreeItem parent;
    private ClassyNodeType type;

    public InfoForCreatingClassyNodeCompositeNodes(ClassyTreeItem parent, ClassyNodeType type) {
        this.parent = parent;
        this.type = type;
    }

    public ClassyTreeItem getParent() {
        return parent;
    }

    public ClassyNodeType getType() {
        return type;
    }
}
