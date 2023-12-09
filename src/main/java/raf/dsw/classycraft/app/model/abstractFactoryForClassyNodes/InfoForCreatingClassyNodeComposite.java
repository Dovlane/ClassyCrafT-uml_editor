package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;

public class InfoForCreatingClassyNodeComposite extends InfoForCreatingClassyNode {
    private ClassyTreeItem parent;
    private ClassyNodeType type;

    public InfoForCreatingClassyNodeComposite(ClassyTreeItem parent, ClassyNodeType type) {
        super(parent);
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
