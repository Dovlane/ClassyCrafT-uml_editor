package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

import javax.swing.*;
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

    public void setName(String name) {
        ClassyNodeComposite parent = (ClassyNodeComposite) this.getClassyNode().getParent();

        if (parent == null) {
            String errorMessage = "The ProjectExplorer cannot be renamed.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
            return;
        }

        if (!name.isEmpty() && parent.getChildByName(name) == null) {
            classyNode.setName(name);

            // Refresh GUI
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getClassyTree().getTreeView());
            ApplicationFramework.getInstance().getClassyRepository().getPackageView().updatePackageView();
        } else {
            String errorMessage = "The path of the file is ambiguous.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

}
