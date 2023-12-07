package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

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

        diagramView.updateSelectionModel(new LassoPainter(startLocation, currentLocation));
    }

    /*
    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("From " + startLocation + " to " + currentLocation);

        if (clickedElementPainter == null) {
            diagramView.updateSelectionModel(currentLocation);
            diagramView.setLasso(new LassoPainter(startLocation, currentLocation));
        }
        else {
            DiagramElement clickedDiagramElement = clickedElementPainter.getDiagramElement();
            if (clickedDiagramElement instanceof Interclass) {
                Point t = new Point(currentLocation.x - previousLocation.x, currentLocation.y - previousLocation.y);
                if (inLasso) {
                    for (ElementPainter painter: diagramView.getSelectionModel()) {
                        if (painter instanceof InterclassPainter) {
                            ((Interclass) painter.getDiagramElement()).translate(t);
                        }
                    }
                }
                else {
                    ((Interclass) clickedDiagramElement).translate(t);
                }
                previousLocation = currentLocation;
            }
        }
    }
     */

}