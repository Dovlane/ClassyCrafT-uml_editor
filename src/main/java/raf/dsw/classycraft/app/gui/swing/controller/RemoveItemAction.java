package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class RemoveItemAction extends AbstractClassyAction {
    ArrayList<IListener> listeners = new ArrayList<>();
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
