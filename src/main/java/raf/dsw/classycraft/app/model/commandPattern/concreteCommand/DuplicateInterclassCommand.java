package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DuplicateInterclassCommand extends AbstractCommand {

    private static int defaultShift = 50;
    private Interclass originalInterclass;
    private ClassyTreeItem duplicatedInterclassTreeItem;
    private IClassyTree iClassyTree;
    private ClassyTreeItem diagramTreeItem;
    private ClassyNode diagram;
    public DuplicateInterclassCommand(IClassyTree iClassyTree, ClassyTreeItem diagramTreeItem, Interclass originalInterclass) {
        this.originalInterclass = originalInterclass;
        this.iClassyTree = iClassyTree;
        this.diagramTreeItem = diagramTreeItem;
        this.diagram = diagramTreeItem.getClassyNode();
        this.duplicatedInterclassTreeItem = null;
    }

    @Override
    public void doCommand() {
        if (duplicatedInterclassTreeItem == null) {
            Interclass duplicatedInterclass = null;
            if (originalInterclass instanceof InterfaceElement) {
                duplicatedInterclass = new InterfaceElement((InterfaceElement) originalInterclass);
            } else if (originalInterclass instanceof EnumElement) {
                duplicatedInterclass = new EnumElement((EnumElement) originalInterclass);
            } else if (originalInterclass instanceof ClassElement) {
                duplicatedInterclass = new ClassElement((ClassElement) originalInterclass);
            }
            int shift = defaultShift * originalInterclass.getNumberOfCopies(false);
            duplicatedInterclass.translate(new Point(shift, shift));
            iClassyTree.attachChild(diagramTreeItem, duplicatedInterclass);
            duplicatedInterclassTreeItem = iClassyTree.getRoot().getTreeItemFromClassyNode(duplicatedInterclass);
        }
        else {
            duplicatedInterclassTreeItem.getClassyNode().setParent(diagram);
            iClassyTree.attachChild(diagramTreeItem, duplicatedInterclassTreeItem);
        }


        System.out.println("DuplicateInterclassCommand - doCommand");
    }

    @Override
    public void undoCommand() {
        iClassyTree.removeItem(duplicatedInterclassTreeItem);

        System.out.println("DuplicateInterclassCommand - undoCommand");
    }
}
