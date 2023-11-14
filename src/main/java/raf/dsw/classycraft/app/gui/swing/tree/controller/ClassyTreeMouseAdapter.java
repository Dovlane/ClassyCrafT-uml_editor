package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;

import javax.swing.*;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassyTreeMouseAdapter extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JTree tree = MainFrame.getInstance().getClassyTree().getTreeView();
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            ClassyTreeItem treeItemSelected = (ClassyTreeItem) path.getLastPathComponent();
            if (treeItemSelected.getClassyNode() instanceof Package) {
                Package chosenPackage = (Package) treeItemSelected.getClassyNode();
                chosenPackage.notifyAllSubscribers(chosenPackage);
            }
        }
    }
}
