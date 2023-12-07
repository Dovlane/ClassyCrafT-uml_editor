package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;

import javax.swing.*;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassyTreeMouseAdapter extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            // Fetch a Classy Tree Item
            JTree tree = MainFrame.getInstance().getClassyTree().getTreeView();
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) {
                System.out.println("[INFO] Valid Classy Tree Item must be selected.");
                return;
            }

            // Execute double click action
            ClassyTreeItem treeItemSelected = (ClassyTreeItem) path.getLastPathComponent();
            if (treeItemSelected.getClassyNode() instanceof Package) {
                Package chosenPackage = (Package) treeItemSelected.getClassyNode();
                Notification notification =
                        new Notification(chosenPackage, NotificationType.SET);
                chosenPackage.notifyAllSubscribers(notification);
            }
        }
    }
}
