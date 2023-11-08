package raf.dsw.classycraft.app.gui.swing.tree;

//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.ClassyRepository.*;
//import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClassyTreeImplementation implements IClassyTree {

    private ClassyTreeView treeView;
    private DefaultTreeModel treeModel;

    private ArrayList<IListener> listeners;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        listeners = new ArrayList<>();
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
        if (child != null) {
            parent.add(new ClassyTreeItem(child));
            ((ClassyNodeComposite) parent.getClassyNode()).addChild(child);
            treeView.expandPath(treeView.getSelectionPath());
            SwingUtilities.updateComponentTreeUI(treeView);
        }
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
        ClassyTreeItem parent = (ClassyTreeItem) item.getParent();

        node.removeFromParent();
        item.removeFromParent();
        if (item.getClassyNode() instanceof Diagram) {
            //System.out.println("Obrisali smo DIAGRAM");
            Package chosenPackage = (Package)parent.getClassyNode();
            chosenPackage.notifyAllSubscribers(chosenPackage);
        }
        if (item.getClassyNode() instanceof Package) {
            //System.out.println("Obrisali smo PACKAGE");
            Package chosenPackage = (Package)item.getClassyNode();
            chosenPackage.notifyAllSubscribers(chosenPackage);
        }

        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
        ApplicationFramework.getInstance().getClassyRepository().printTree();

        //notifyAllSubscribers(new ClassyTreeImplementationEvent(parent, ClassyTreeImplementationEvents.REMOVE));
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

                if (!content.isEmpty() && nodeParent.getChildByName(content) == null) {

                    // Rename
                    node.setName(content);
                    SwingUtilities.updateComponentTreeUI(treeView);
                    System.out.println(node.getName() + " has been renamed to " + content);

                    // Close the window after successful renaming
                    frame.dispose();

                } else if (content.isEmpty()) {
                    String errorMessage = "New name cannot be an empty string.";
                    MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
                } else {
                    System.out.println(content);
                    String errorMessage = "The path of the file is ambiguous.";
                    MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
                }
            }
        });

        // Display the JFrame
        frame.setVisible(true);

        if (item.getClassyNode() instanceof Diagram) {
            System.out.println("RENAMING DIAGRAM");
            Package chosenPackage = (Package)item.getClassyNode().getParent();
            chosenPackage.notifyAllSubscribers(chosenPackage);
        }

        System.out.println("RenameAction has been performed.");
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    private ClassyNode createChild(ClassyNode parent, ClassyNodeType type) {
        return ClassyNodeFactory.createClassyNode(parent, type);
    }

    public ClassyTreeView getTreeView() {
        return treeView;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }


    public ArrayList<IListener> getListeners() {
        return listeners;
    }


}
