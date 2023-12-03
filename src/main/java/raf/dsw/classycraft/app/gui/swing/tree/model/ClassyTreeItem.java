package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class ClassyTreeItem extends DefaultMutableTreeNode {

    private ClassyNode classyNode;

    public ClassyTreeItem(ClassyNode classyNode) {
        this.classyNode = classyNode;
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
