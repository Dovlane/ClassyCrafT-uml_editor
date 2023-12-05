package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassyTree implements IClassyTree {

    private ClassyTreeItem root;
    private ClassyTreeView treeView;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        root = new ClassyTreeItem(projectExplorer);
        treeView = new ClassyTreeView(new DefaultTreeModel(root));
        return treeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent, ClassyNodeType type) {

        if (parent == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Parent Node must be selected.", MessageType.ERROR);
            return;
        }

        if (parent.getClassyNode() instanceof ClassyNodeLeaf) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Leaf Node cannot contain any other node.", MessageType.ERROR);
            return;
        }

        ClassyNode child = createChild(parent.getClassyNode(), type);
        attachChild(parent, child);
    }

    @Override
    public void attachChild(ClassyTreeItem parent, ClassyNode child) {

        if (parent == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Parent Node must be selected.", MessageType.ERROR);
            return;
        }

        if (child == null)
            return;

        // Add child to both JTree and Model
        parent.add(new ClassyTreeItem(child));
        ((ClassyNodeComposite) parent.getClassyNode()).addChild(child);

        // Refresh GUI - Classy Tree
        treeView.expandPath(new TreePath(parent.getPath()));
        SwingUtilities.updateComponentTreeUI(treeView);

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

        node.removeSubtree();
        item.removeFromParent();

        // Refresh GUI
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void renameItem(ClassyTreeItem item) {
        ClassyNode node = item.getClassyNode();
        ClassyNodeComposite nodeParent = (ClassyNodeComposite) node.getParent();
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
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    private ClassyNode createChild(ClassyNode parent, ClassyNodeType type) {
        return ClassyNodeFactory.createClassyNode(parent, type);
    }

    public ClassyTreeItem getRoot() {
        return root;
    }

    public ClassyTreeView getTreeView() {
        return treeView;
    }

}
