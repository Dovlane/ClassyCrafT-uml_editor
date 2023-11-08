package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameAction extends AbstractClassyAction {

    public RenameAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/Rename.png"));
        putValue(NAME, "Rename ClassyNode");
        putValue(SHORT_DESCRIPTION, "Rename the selected ClassyNode");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedItem = ApplicationFramework.getInstance().getClassyTree().getSelectedNode();
        ApplicationFramework.getInstance().getClassyTree().renameItem(selectedItem);
        System.out.println("RenameAction has been performed.");
    }
}
