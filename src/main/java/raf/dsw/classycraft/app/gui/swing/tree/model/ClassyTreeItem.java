package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class ClassyTreeItem extends DefaultMutableTreeNode {

    private ClassyNode classyNode;

    public ClassyTreeItem(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }

    public ClassyTreeItem getTreeItemFromClassyNode(ClassyNode node) {
        if (classyNode.equals(node)) {
            return this;
        }
        ClassyTreeItem returnTreeItem = null;
        for (int i = 0; i < getChildCount(); i++) {
            returnTreeItem = ((ClassyTreeItem) getChildAt(i)).getTreeItemFromClassyNode(node);
            if (returnTreeItem != null) {
                break;
            }
        }
        return returnTreeItem;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return classyNode.getName();
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

}
