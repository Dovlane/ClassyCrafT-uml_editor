package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.util.List;

public class RemoveDiagramElementCommand extends AbstractCommand {

    private final IClassyTree iClassyTree;
    private List<DiagramElement> diagramElementListToDelete;
    private ClassyTreeItem diagramTreeItem;
    public RemoveDiagramElementCommand(IClassyTree iClassyTree, ClassyTreeItem diagram, List<DiagramElement> diagramElementListToDelete) {
        this.iClassyTree = iClassyTree;
        this.diagramTreeItem = diagram;
        this.diagramElementListToDelete = diagramElementListToDelete;
    }

    @Override
    public void doCommand() {
        int index = 0;
        while (index < diagramElementListToDelete.size()) {
            DiagramElement diagramElement = diagramElementListToDelete.get(index);
            diagramElement.setParent(diagramTreeItem.getClassyNode());
            if (diagramElement instanceof Connection) {
                Connection connection = (Connection) diagramElement;
                if (connectionIsDeleted(connection)) {
                    index++;
                    continue;
                }
            }
            ClassyTreeItem treeItemDiagramElement =
                    iClassyTree.getRoot().getTreeItemFromClassyNode(diagramElement);
            iClassyTree.removeItem(treeItemDiagramElement);
            index++;
        }
        System.out.println("Remove - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }

    @Override
    public void undoCommand() {
        for (DiagramElement diagramElement : diagramElementListToDelete) {
            diagramElement.setParent(diagramTreeItem.getClassyNode());
            if (diagramElement instanceof Connection)
                System.out.println("STANI");
            iClassyTree.attachChild(diagramTreeItem, diagramElement);
        }
        System.out.println("Remove - undo command");
        ApplicationFramework.getInstance().getClassyRepository().printTree();

    }

    private boolean connectionIsDeleted(Connection connection) {
        for (DiagramElement diagramElement : diagramElementListToDelete) {
            if (diagramElement instanceof Interclass) {
                Interclass interclass = (Interclass) diagramElement;
                if (connection.containsInterclass(interclass))
                    return true;
            }
        }
        return false;
    }
}
