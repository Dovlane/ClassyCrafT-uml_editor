package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class MoveState implements State {

    Point previousLocation;
    ElementPainter clickedElementPainter;

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of MoveState");

        previousLocation = location;
        clickedElementPainter = diagramView.getPainterAt(location);
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of MoveState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of MoveState from " + startLocation + " to " + currentLocation);

        // Move selected DiagramElements around
        if (clickedElementPainter != null) {
            DiagramElement clickedDiagramElement = clickedElementPainter.getDiagramElement();

            // Only update locations of Interclass elements because connections will automatically adjust
            if (clickedDiagramElement instanceof Interclass) {
                Point t = new Point(currentLocation.x - previousLocation.x, currentLocation.y - previousLocation.y);

                // Move the whole Selection Model if the clicked painter is part of it
                if (diagramView.getSelectionModel().contains(clickedElementPainter)) {
                    for (ElementPainter painter: diagramView.getSelectionModel()) {
                        if (painter instanceof InterclassPainter) {
                            ((Interclass) painter.getDiagramElement()).translate(t);
                        }
                    }
                }
                else {
                    ((Interclass) clickedDiagramElement).translate(t);
                }

                // Change the previous location so the new vector can latter on be properly calculated
                previousLocation = currentLocation;
            }
        }

        // Move work are around
        else {
            ;
        }
    }

    @Override
    public void mouseWheelMoved(DiagramView diagramView, int wheelRotation) {
        System.out.println("mouseWheelMoved inside of MoveState");

    }

}
