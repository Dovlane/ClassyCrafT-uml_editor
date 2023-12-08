package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingClassyNodeCompositeNodes;

import java.awt.event.ActionEvent;

public class NewDiagramAction extends AbstractClassyAction {

    public NewDiagramAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/AddDiagram.png"));
        putValue(NAME, "New Diagram");
        putValue(SHORT_DESCRIPTION, "Create a new Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().addChild(new InfoForCreatingClassyNodeCompositeNodes(selectedNode, ClassyNodeType.DIAGRAM));
        System.out.println("NewDiagramAction has been performed.");
    }
}
