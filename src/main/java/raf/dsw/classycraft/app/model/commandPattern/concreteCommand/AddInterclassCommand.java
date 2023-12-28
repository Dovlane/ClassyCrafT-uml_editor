package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.InterclassStateDialog;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.ElementInterclassType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingInterclass;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import javax.swing.*;
import java.awt.*;

public class AddInterclassCommand extends AbstractCommand {

    private IClassyTree iClassyTree;
    private InfoForCreatingInterclass infoForCreatingInterclass;
    private Interclass interclass;
    private Diagram diagram;

    public AddInterclassCommand(IClassyTree iClassyTree, Diagram diagram, InfoForCreatingInterclass infoForCreatingInterclass) {
        this.iClassyTree = iClassyTree;
        this.diagram = diagram;
        this.infoForCreatingInterclass = infoForCreatingInterclass;
    }

    @Override
    public void doCommand() {
        interclass = (Interclass) iClassyTree.addChild(infoForCreatingInterclass);

        System.out.println("AddInterclass - doCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }

    @Override
    public void undoCommand() {
        interclass.setParent(diagram); // this enables comparing two classyNodes with absolutePath
        ClassyTreeItem treeItemInterclass =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(interclass);
        iClassyTree.removeItem(treeItemInterclass);

        System.out.println("AddInterclass - undoCommand");
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }
}
