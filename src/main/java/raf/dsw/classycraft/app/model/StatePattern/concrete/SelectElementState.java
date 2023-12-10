package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;

import java.awt.*;

public class SelectElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside SelectElementState");

        int offset = 2;
        Point upperLeftPoint = new Point(location.x - offset, location.y - offset);
        Point bottomRightPoint = new Point(location.x + offset, location.y + offset);
        diagramView.updateSelectionModel(new LassoPainter(upperLeftPoint, bottomRightPoint));
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside SelectElementState");

        diagramView.updateSelectionModel(null);
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of SelectElementState from " + startLocation + " to " + currentLocation);
        System.out.println("Optimal location: " + currentLocationOptimal);

        diagramView.updateSelectionModel(new LassoPainter(startLocation, currentLocationOptimal));
    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of SelectElementState");

    }

}