package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.util.List;

public class RemoveDiagramElementCommand extends AbstractCommand {

    private final IClassyTree iClassyTree;
    private List<Interclass> interclassesToDelete;
    private List<Connection> connectionsToDelete;
    private ClassyTreeItem diagramTreeItem;

    public RemoveDiagramElementCommand(IClassyTree iClassyTree, List<Interclass> interclassesToDelete, List<Connection> connectionsToDelete, ClassyTreeItem diagramTreeItem) {
        this.iClassyTree = iClassyTree;
        this.interclassesToDelete = interclassesToDelete;
        this.connectionsToDelete = connectionsToDelete;
        this.diagramTreeItem = diagramTreeItem;
    }

    @Override
    public void doCommand() {
        for (Interclass interclassToDelete : interclassesToDelete) {
            interclassToDelete.setParent(diagramTreeItem.getClassyNode());
            ClassyTreeItem treeItemInterclass = iClassyTree.getRoot().getTreeItemFromClassyNode(interclassToDelete);
            iClassyTree.removeItem(treeItemInterclass);
        }

        // here we are deleting connections that are not deleted indirectly by
        // removing Interclass, and we now need to remove them directly
        for (Connection connectionToDelete : connectionsToDelete) {
            if (!connectionIsDeleted(connectionToDelete)) {
                connectionToDelete.setParent(diagramTreeItem.getClassyNode());
                ClassyTreeItem treeItemConnection = iClassyTree.getRoot().getTreeItemFromClassyNode(connectionToDelete);
                iClassyTree.removeItem(treeItemConnection);
            }
        }
        System.out.println("Remove - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }

    @Override
    public void undoCommand() {
        for (Interclass interclassToGetBack : interclassesToDelete) {
            interclassToGetBack.setParent(diagramTreeItem.getClassyNode());
            iClassyTree.attachChild(diagramTreeItem, interclassToGetBack);
        }
        for (Connection connectionsToGetBack : connectionsToDelete) {
            connectionsToGetBack.setParent(diagramTreeItem.getClassyNode());
            iClassyTree.attachChild(diagramTreeItem, connectionsToGetBack);
        }
        System.out.println("Remove - undo command");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }

    private boolean connectionIsDeleted(Connection connection) {
        for (Interclass interclass : interclassesToDelete) {
            if (connection.containsInterclass(interclass))
                return true;
        }
        return false;
    }
}
