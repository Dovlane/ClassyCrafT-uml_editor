package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;

import java.awt.*;

public class SelectElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside SelectElementState");

        diagramView.updateSelectionModel(new LassoPainter(location, location));
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside SelectElementState");

        diagramView.updateSelectionModel(null);
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside SelectElementState");

        System.out.println("From " + startLocation + " to " + currentLocation);
        diagramView.updateSelectionModel(new LassoPainter(startLocation, currentLocation));
    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of SelectElementState");

    }

}