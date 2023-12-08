package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.StatePattern.State;

import java.awt.*;

public class ZoomState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of ZoomState");

        System.out.println(location.x + " " + location.y);
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of ZoomState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of ZoomState");

    }

    @Override
    public void mouseWheelMoved(DiagramView diagramView, int wheelRotation) {
        System.out.println("mouseWheelMoved inside of ZoomState");

        diagramView.zoom(wheelRotation);
    }

}
