package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingClassyNodeCompositeNodes;

import java.awt.event.ActionEvent;

public class NewProjectAction extends AbstractClassyAction {

    public NewProjectAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/AddProject.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "Create a new Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = MainFrame.getInstance().getClassyTree().getSelectedNode();
        System.out.println("getSelectedNode " + selectedNode);
        MainFrame.getInstance().getClassyTree().addChild(new InfoForCreatingClassyNodeCompositeNodes(selectedNode, ClassyNodeType.PROJECT));
        System.out.println("NewProjectAction has been performed.");
    }

}
