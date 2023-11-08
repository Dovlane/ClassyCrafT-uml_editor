package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

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
            this.classyNode.setName(name);
            if (classyNode instanceof Diagram){
                Package chosenPackage = (Package) classyNode.getParent();
                chosenPackage.notifyAllSubscribers(chosenPackage);
            }
            if (classyNode instanceof Project) {
                Project chosenProject = (Project)classyNode;
                for (ClassyNode childOfProject : chosenProject.getChildren()){
                    if (childOfProject instanceof Package)
                        notifyAllPackageSubscribers((Package)childOfProject);
                }

            }

        } else {
            String errorMessage = "The path of the file is ambiguous.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }
    }

    public void removeSubtree(){

    }
    private void notifyAllPackageSubscribers(Package chosenPackage){
        for (ClassyNode childOfPackage : chosenPackage.getChildren()) {
            if (childOfPackage instanceof Package)
                notifyAllPackageSubscribers((Package)childOfPackage);
        }
        chosenPackage.notifyAllSubscribers(chosenPackage);
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public void setClassyNode(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }

}
