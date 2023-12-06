package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.StatePattern.State;

import java.awt.*;

public class AddInterclassState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("Creating Interclass!");
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {

    }
}
