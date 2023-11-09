package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeCellEditor;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class ClassyTreeView extends JTree {

    private ClassyTreeSelectionListener classyTreeSelectionListener;
    private ClassyTreeCellEditor classyTreeCellEditor;

    public ClassyTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);
        ClassyTreeCellRenderer classyTreeCellRenderer = new ClassyTreeCellRenderer();
        classyTreeSelectionListener = new ClassyTreeSelectionListener();
        addTreeSelectionListener(classyTreeSelectionListener);

        classyTreeCellEditor = new ClassyTreeCellEditor(this, classyTreeCellRenderer);
        setCellEditor(classyTreeCellEditor);
        setCellRenderer(classyTreeCellRenderer);
        setEditable(true);

        setToggleClickCount(0);
    }

    public ClassyTreeCellEditor getClassyTreeCellEditor() {
        return classyTreeCellEditor;
    }

}
