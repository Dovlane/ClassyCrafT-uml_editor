package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.AddInterclassCommand;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.*;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassyTree implements IClassyTree {

    private ClassyTreeItem root;
    private ClassyTreeView treeView;
    private AbstractClassyCraftManufacturer abstractClassyCraftManufacturer;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        root = new ClassyTreeItem(projectExplorer);
        treeView = new ClassyTreeView(new DefaultTreeModel(root));
        abstractClassyCraftManufacturer = new ClassyCraftManufacturer();
        return treeView;
    }

    @Override
    public void loadProject(Project node) {
        ClassyTreeItem loadedProject = new ClassyTreeItem(node);
        root.add(loadedProject);

        ClassyNodeComposite classyNode = (ClassyNodeComposite) root.getClassyNode();
        classyNode.addChild(node);

        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public ClassyNode addChild(InfoForCreatingClassyNode infoForCreatingClassyNode) {
        ClassyTreeItem parent = infoForCreatingClassyNode.getParent();

        ClassyNode child;

        if (infoForCreatingClassyNode instanceof InfoForCreatingClassyNodeComposite)
            child = abstractClassyCraftManufacturer.createClassyNodeComposite((InfoForCreatingClassyNodeComposite) infoForCreatingClassyNode);
        else if (infoForCreatingClassyNode instanceof InfoForCreatingInterclass) {
            child = abstractClassyCraftManufacturer.createInterclass((InfoForCreatingInterclass) infoForCreatingClassyNode);
        }
        else
            child = abstractClassyCraftManufacturer.createConnection((InfoForCreatingConnection) infoForCreatingClassyNode);

        if (attachChild(parent, child))
            return child;
        else
            return null;
    }

    @Override
    public boolean attachChild(ClassyTreeItem parent, ClassyNode child) {

        if (parent == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Parent Node must be selected.", MessageType.ERROR);
            return false;
        }

        // Add child to both Model and JTree
        boolean success = ((ClassyNodeComposite) parent.getClassyNode()).addChild(child);
        if (success) {

            // Update JTree
            parent.add(new ClassyTreeItem(child));

            // Refresh GUI - Classy Tree
            treeView.expandPath(new TreePath(parent.getPath()));
            SwingUtilities.updateComponentTreeUI(treeView);
        }
        return success;
    }

    @Override
    // unlike
    public boolean attachChild(ClassyTreeItem parent, ClassyTreeItem child) {
        if (parent == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Parent Node must be selected.", MessageType.ERROR);
            return false;
        }
        boolean success = ((ClassyNodeComposite) parent.getClassyNode()).addChild(child.getClassyNode());

        if (success) {
            // Update JTree
            parent.add(child);

            // Refresh GUI - Classy Tree
            treeView.expandPath(new TreePath(parent.getPath()));
            SwingUtilities.updateComponentTreeUI(treeView);
        }
        return success;
    }

    @Override
    public void removeItem(ClassyTreeItem item) {
        if (item == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "ClassyTreeItem must be first selected.", MessageType.ERROR);
            return;
        }

        ClassyNode node = item.getClassyNode();
        if (node instanceof ProjectExplorer) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "ProjectExplorer cannot be deleted.", MessageType.ERROR);
            return;
        }

        // connections can (i.e. their parents can be removed) if removeItem for interclass From or To is called before
        if (node instanceof Connection  && node.getParent() == null) {
            return;
        }

        node.changeOccurred();
        node.removeSubtree();
        item.removeFromParent();


        // Refresh GUI
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void renameItem(ClassyTreeItem item) {

        // Find ClassyNode of the ClassyTreeItem
        ClassyNode node = item.getClassyNode();
        ClassyNodeComposite nodeParent = (ClassyNodeComposite) node.getParent();

        // Project Explorer cannot be renamed
        if (nodeParent == null) {
            String errorMessage = "The ProjectExplorer cannot be renamed.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
            return;
        }

        JFrame frame = new JFrame("Rename");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);

        // Create a JPanel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Create a JTextField (text box)
        JTextField textField = new JTextField(20);
        panel.add(textField);

        // Create a JButton
        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        // Add an ActionListener to the button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText();

                if (node.setName(content)) {

                    // Refresh GUI
                    SwingUtilities.updateComponentTreeUI(treeView);

                    // Close the window after successful renaming
                    frame.dispose();

                }
            }
        });

        // Display the JFrame
        frame.setVisible(true);

        System.out.println("RenameAction has been performed.");
    }

    @Override
    public boolean renameItem(ClassyTreeItem item, String newName) {

        // Find ClassyNode of the ClassyTreeItem
        ClassyNode node = item.getClassyNode();
        ClassyNodeComposite nodeParent = (ClassyNodeComposite) node.getParent();

        // Project Explorer cannot be renamed
        if (nodeParent == null) {
            String errorMessage = "The ProjectExplorer cannot be renamed.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
            return false;
        }

        // Try to set a new name
        if (node.setName(newName)) {
            SwingUtilities.updateComponentTreeUI(treeView);
            return true;
        }

        return false;
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public ClassyTreeItem getRoot() {
        return root;
    }

    public ClassyTreeView getTreeView() {
        return treeView;
    }
}
