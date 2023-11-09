package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class RemoveItemAction extends AbstractClassyAction {

    public RemoveItemAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/Remove.png"));
        putValue(NAME, "Remove Item");
        putValue(SHORT_DESCRIPTION, "Remove the Item from the tree.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedNode = MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().removeItem(selectedNode);
        System.out.println("RemoveItemAction has been performed.");
    }

}
