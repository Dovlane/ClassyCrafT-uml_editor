package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.classyRepository.ClassyNodeType;

import java.awt.event.ActionEvent;

public class RemoveItemAction extends AbstractClassyAction {

    public RemoveItemAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/add.png"));
        putValue(NAME, "Remove Item");
        putValue(SHORT_DESCRIPTION, "Remove the Item from the tree.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = ApplicationFramework.getInstance().getClassyTree().getSelectedNode();
        ApplicationFramework.getInstance().getClassyTree().removeItem(selectedNode);
        System.out.println("RemoveItemAction has been performed.");
    }

}
