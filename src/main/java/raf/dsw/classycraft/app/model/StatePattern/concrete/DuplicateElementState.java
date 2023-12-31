package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.DuplicateInterclassCommand;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class DuplicateElementState implements State {

    int defaultShift = 50;

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of DuplicateElementState");

        Diagram currentDiagram = diagramView.getDiagram();
        DiagramElement diagramElement = diagramView.getPainterAt(location).getDiagramElement();
        if (diagramElement != null) {
            if (diagramElement instanceof Interclass) {

                Interclass selectedInterclass = (Interclass) diagramElement;
                IClassyTree iClassyTree = MainFrame.getInstance().getClassyTree();
                ClassyTreeItem classyTreeDiagram =
                        iClassyTree.getRoot().getTreeItemFromClassyNode(currentDiagram);

                if (classyTreeDiagram == null) {
                    MainFrame.getInstance().getMessageGenerator().generateMessage(
                            "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
                    return;
                }

                AbstractCommand duplicateInterclassCommand = new DuplicateInterclassCommand(iClassyTree, classyTreeDiagram, selectedInterclass);
                diagramView.getCommandManager().addCommand(duplicateInterclassCommand);
            }
        }
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of DuplicateElementState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of DuplicateElementState from " + startLocation + " to " + currentLocation);
        System.out.println("Optimal location: " + currentLocationOptimal);

    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of DuplicateElementState");

    }

}
