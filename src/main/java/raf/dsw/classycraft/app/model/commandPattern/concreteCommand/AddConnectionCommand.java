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
    private Connection connection;
    private Diagram diagram;

    public AddConnectionCommand(IClassyTree iClassyTree, InfoForCreatingConnection infoForCreatingConnection, Diagram diagram) {
        this.iClassyTree = iClassyTree;
        this.infoForCreatingConnection = infoForCreatingConnection;
        this.diagram = diagram;
    }

    @Override
    public void doCommand() {
        connection = (Connection) iClassyTree.addChild(infoForCreatingConnection);

        System.out.println("AddConnection - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }

    @Override
    public void undoCommand() {
        connection.setParent(diagram); // this enables comparing two classyNodes with absolutePath
        ClassyTreeItem treeItemConnection =
                iClassyTree.getRoot().getTreeItemFromClassyNode(connection);
        iClassyTree.removeItem(treeItemConnection);

        System.out.println("AddConnection - undoCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }
}
