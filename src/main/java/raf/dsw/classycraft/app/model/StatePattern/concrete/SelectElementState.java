package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;

import java.awt.*;

public class SelectElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside SelectElementState");

        diagramView.clearSelectionModel();
        diagramView.updateSelectionModel(location);
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside SelectElementState");
        diagramView.setLasso(null);
        diagramView.setSelectionFinished(true);
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("From " + startLocation + " to " + currentLocation);

        diagramView.updateSelectionModel(currentLocation);
        diagramView.setLasso(new LassoPainter(startLocation, currentLocation));
    }

}