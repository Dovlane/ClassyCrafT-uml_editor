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

    private boolean inLasso;
    private Point previousLocation;
    private ElementPainter clickedElementPainter;

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside SelectElementState");

        previousLocation = location;

        // Check if clicked happen on an ElementPainter
        inLasso = false;
        clickedElementPainter = null;
        for (ElementPainter painter: diagramView.getPainters()) {
            if (painter.elementAt(location)) {
                clickedElementPainter = painter;

                // Check if ElementPainter is inside the Lasso
                inLasso = diagramView.getSelectionModel().contains(clickedElementPainter);
            }
        }

        // Remove Lasso selection if the clickedElementPainter is NOT inside it
        if (!inLasso) {
            diagramView.clearSelectionModel();
        }
        diagramView.updateSelectionModel(location);
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside SelectElementState");
        diagramView.setLasso(null);
    }

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

}