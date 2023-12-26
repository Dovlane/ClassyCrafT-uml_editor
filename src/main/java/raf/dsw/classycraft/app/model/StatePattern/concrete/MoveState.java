package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MoveState implements State {

    Point previousLocation;
    Point previousLocationWorld;
    AffineTransform initialTransform;
    ElementPainter clickedElementPainter;

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of MoveState");

        clickedElementPainter = diagramView.getPainterAt(location);
        previousLocationWorld = new Point(location);

        initialTransform = diagramView.getTransform();
        initialTransform.transform(location, location);
        previousLocation = location;
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of MoveState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of MoveState from " + startLocation + " to " + currentLocation);
        System.out.println("Optimal location: " + currentLocationOptimal);

        // Move selected DiagramElements around
        if (clickedElementPainter != null) {
            DiagramElement clickedDiagramElement = clickedElementPainter.getDiagramElement();

            // Only update locations of Interclass elements because connections will automatically adjust
            if (clickedDiagramElement instanceof Interclass) {
                Point t = new Point(currentLocation.x - previousLocationWorld.x, currentLocation.y - previousLocationWorld.y);

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
                previousLocationWorld = currentLocation;
            }
        }

        // Move work area around
        else {
            initialTransform.transform(currentLocation, currentLocation);
            diagramView.move(previousLocation, currentLocation);
            previousLocation = currentLocation;
        }
    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of MoveState");

    }

}