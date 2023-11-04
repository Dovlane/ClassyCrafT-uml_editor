package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;

import java.awt.event.ActionEvent;

public class NewProjectAction extends AbstractClassyAction {

    public NewProjectAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "Create a new Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = ApplicationFramework.getInstance().getClassyTree().getSelectedNode();
        ApplicationFramework.getInstance().getClassyTree().addChild(selectedNode, ClassyNodeType.PROJECT);
        System.out.println("NewProjectAction has been performed.");
    }

}
