package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingClassyNodeCompositeNodes;

import java.awt.event.ActionEvent;

public class NewPackageAction extends AbstractClassyAction {

    public NewPackageAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/AddPackage.png"));
        putValue(NAME, "New Package");
        putValue(SHORT_DESCRIPTION, "Create a new Package");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().addChild(new InfoForCreatingClassyNodeCompositeNodes(selectedNode, ClassyNodeType.PACKAGE));
        System.out.println("NewPackageAction has been performed.");
    }

}
