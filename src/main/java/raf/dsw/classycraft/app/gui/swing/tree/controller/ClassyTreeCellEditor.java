package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;

public class ClassyTreeCellEditor extends DefaultTreeCellEditor implements ActionListener, IPublisher {

    private Object clickedOn = null;
    private JTextField edit = null;
    ArrayList<IListener> listeners = new ArrayList<>();


    public ClassyTreeCellEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
        super(arg0, arg1);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        super.getTreeCellEditorComponent(arg0,arg1,arg2,arg3,arg4,arg5);
        clickedOn = arg1;
        edit = new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }

    @Override
    public boolean isCellEditable(EventObject arg0) {
        if (arg0 instanceof MouseEvent) {
            if (((MouseEvent) arg0).getClickCount() == 3) {
                return true;
            }
            if (((MouseEvent)arg0).getClickCount() == 2) {
                MouseEvent e = (MouseEvent)arg0;
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                ClassyTreeItem treeItemSelected = (ClassyTreeItem)path.getLastPathComponent();
                if (treeItemSelected.getClassyNode() instanceof Package){
                    Package chosenPackage = (Package) treeItemSelected.getClassyNode();
                    chosenPackage.addListener(MainFrame.getInstance().getProjectView().getPackageView());
                    chosenPackage.notifyAllSubscribers(chosenPackage);
                    notifyAllSubscribers(treeItemSelected.getClassyNode());
                }
            }
        }
        return false;
    }
    public void actionPerformed(ActionEvent e){
        if (!(clickedOn instanceof ClassyTreeItem)) {
            return;
        }
        ClassyTreeItem clicked = (ClassyTreeItem) clickedOn;
        clicked.setName(e.getActionCommand());
    }
    @Override
    public void addListener(IListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener : listeners)
            listener.update(notification);
    }
}
