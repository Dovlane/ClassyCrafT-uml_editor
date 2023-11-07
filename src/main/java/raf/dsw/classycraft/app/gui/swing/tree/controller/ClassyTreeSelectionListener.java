package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClassyTreeSelectionListener implements TreeSelectionListener {

    private ArrayList<IListener> listeners = new ArrayList();
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        ClassyTreeItem treeItemSelected = (ClassyTreeItem)path.getLastPathComponent();
        System.out.println("Selektovan cvor: " + treeItemSelected);
        System.out.println("getPath: " + e.getPath());
    }
}
