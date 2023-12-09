package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

import java.awt.*;

public class DuplicateElementState implements State {

    int defaultShift = 50;

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of DuplicateElementState");

        Diagram currentDiagram = diagramView.getDiagram();
        DiagramElement diagramElement = diagramView.getPainterAt(location).getDiagramElement();
        if (diagramElement instanceof Interclass) {

            // Attach a new Interclass object in the whole Model
            ClassyTreeItem classyTreeDiagram =
                    MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
            if (classyTreeDiagram ==  null) {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
                return;
            }

            Interclass newInterclass;
            if (diagramElement instanceof ClassElement) {
                newInterclass = new ClassElement((ClassElement) diagramElement);
                System.out.println(newInterclass);
                System.out.println(newInterclass.getAbsolutePath());
                System.out.println(newInterclass == diagramElement);
                MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newInterclass);
            }
            else if (diagramElement instanceof InterfaceElement) {
                newInterclass = new InterfaceElement((InterfaceElement) diagramElement);
                MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newInterclass);
            }
            else {
                newInterclass = new EnumElement((EnumElement) diagramElement);
                MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newInterclass);
            }

            int shift = defaultShift * ((Interclass) diagramElement).getNumberOfCopies(false);
            newInterclass.translate(new Point(shift, shift));
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
