package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.util.ArrayList;
import java.util.List;

public class RemoveDiagramElementCommand extends AbstractCommand {

    private final IClassyTree iClassyTree;
    private List<ClassyTreeItem> interclassTreeItemsToDelete;
    private List<ClassyTreeItem> connectionTreeItemsToDelete;
    private ClassyTreeItem diagramTreeItem;

    public RemoveDiagramElementCommand(IClassyTree iClassyTree, List<Interclass> interclassesToDelete, List<Connection> connectionsToDelete, ClassyTreeItem diagramTreeItem) {
        this.iClassyTree = iClassyTree;
        this.diagramTreeItem = diagramTreeItem;
        initialize(interclassesToDelete, connectionsToDelete);
    }

    private void initialize(List<Interclass> interclassesToDelete, List<Connection> connectionsToDelete) {
        this.interclassTreeItemsToDelete = new ArrayList<>();
        for (Interclass interclass : interclassesToDelete) {
            ClassyTreeItem classyTreeItemFromClassyNode = iClassyTree.getRoot().getTreeItemFromClassyNode(interclass);
            interclassTreeItemsToDelete.add(classyTreeItemFromClassyNode);
        }
        this.connectionTreeItemsToDelete = new ArrayList<>();
        for (Connection connection : connectionsToDelete) {
            ClassyTreeItem classyTreeItemFromClassyNode = iClassyTree.getRoot().getTreeItemFromClassyNode(connection);
            interclassTreeItemsToDelete.add(classyTreeItemFromClassyNode);
        }
    }

    @Override
    public void doCommand() {
        for (ClassyTreeItem interclassTreeItem : interclassTreeItemsToDelete) {
            iClassyTree.removeItem(interclassTreeItem);
        }

        // here we are deleting connections that are not deleted indirectly by
        // removing Interclass, and we now need to remove them directly
        for (ClassyTreeItem connectionTreeItem : connectionTreeItemsToDelete) {
            iClassyTree.removeItem(connectionTreeItem);

        }
        System.out.println("Remove - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }

    @Override
    public void undoCommand() {
        ClassyNode diagram = diagramTreeItem.getClassyNode();
        for (ClassyTreeItem interclassTreeItemDeleted : interclassTreeItemsToDelete) {
            interclassTreeItemDeleted.getClassyNode().setParent(diagram);
            iClassyTree.attachChild(diagramTreeItem, interclassTreeItemDeleted);
        }
        for (ClassyTreeItem connectionTreeItemDeleted : interclassTreeItemsToDelete) {
            connectionTreeItemDeleted.getClassyNode().setParent(diagram);
            iClassyTree.attachChild(diagramTreeItem, connectionTreeItemDeleted);
        }
        System.out.println("Remove - undo command");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }
}
