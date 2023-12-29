package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingConnection;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingInterclass;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class AddConnectionCommand extends AbstractCommand {

    private IClassyTree iClassyTree;
    private InfoForCreatingConnection infoForCreatingConnection;
    private ClassyTreeItem treeItemConnection;
    private Diagram diagram;
    private ClassyTreeItem treeItemDiagram;

    public AddConnectionCommand(IClassyTree iClassyTree, InfoForCreatingConnection infoForCreatingConnection, Diagram diagram) {
        this.iClassyTree = iClassyTree;
        this.infoForCreatingConnection = infoForCreatingConnection;
        this.treeItemConnection = null;
        this.treeItemDiagram = iClassyTree.getRoot().getTreeItemFromClassyNode(diagram);
        this.diagram = diagram;
    }

    @Override
    public void doCommand() {
        if (treeItemConnection == null) {
            Connection connection = (Connection) iClassyTree.addChild(infoForCreatingConnection);
            treeItemConnection = iClassyTree.getRoot().getTreeItemFromClassyNode(connection);
        }
        else {
            treeItemConnection.getClassyNode().setParent(diagram);
            iClassyTree.attachChild(treeItemDiagram, treeItemConnection);
        }
        System.out.println("AddConnection - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }

    @Override
    public void undoCommand() {
        iClassyTree.removeItem(treeItemConnection);

        System.out.println("AddConnection - undoCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }
}
