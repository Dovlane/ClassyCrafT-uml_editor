package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingInterclass;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class AddInterclassCommand extends AbstractCommand {

    private IClassyTree iClassyTree;
    private InfoForCreatingInterclass infoForCreatingInterclass;
    private ClassyTreeItem treeItemInterclass;
    private Diagram diagram;
    private ClassyTreeItem treeItemDiagram;

    public AddInterclassCommand(IClassyTree iClassyTree, Diagram diagram, InfoForCreatingInterclass infoForCreatingInterclass) {
        this.iClassyTree = iClassyTree;
        this.diagram = diagram;
        this.treeItemDiagram = iClassyTree.getRoot().getTreeItemFromClassyNode(diagram);
        this.treeItemInterclass = null;
        this.infoForCreatingInterclass = infoForCreatingInterclass;
    }

    @Override
    public void doCommand() {
        if (treeItemInterclass == null) {
            Interclass interclass = (Interclass) iClassyTree.addChild(infoForCreatingInterclass);
            treeItemInterclass = iClassyTree.getRoot().getTreeItemFromClassyNode(interclass);
        }
        else {
            treeItemInterclass.getClassyNode().setParent(diagram);
            iClassyTree.attachChild(treeItemDiagram, treeItemInterclass);
        }
        System.out.println("AddInterclass - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }

    @Override
    public void undoCommand() {
        iClassyTree.removeItem(treeItemInterclass);

        System.out.println("AddInterclass - undoCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }
}
