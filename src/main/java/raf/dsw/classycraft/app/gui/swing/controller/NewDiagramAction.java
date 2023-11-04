package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;

import java.awt.event.ActionEvent;

public class NewDiagramAction extends AbstractClassyAction {

    public NewDiagramAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "New Diagram");
        putValue(SHORT_DESCRIPTION, "Create a new Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = ApplicationFramework.getInstance().getClassyTree().getSelectedNode();
        ApplicationFramework.getInstance().getClassyTree().addChild(selectedNode, ClassyNodeType.DIAGRAM);
        System.out.println("NewDiagramAction has been performed.");
    }
}
