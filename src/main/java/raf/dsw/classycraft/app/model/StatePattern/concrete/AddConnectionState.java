package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.LinePainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class AddConnectionState implements State {
    DiagramElement selectedDiagramElementFrom;
    public void mousePressed(Point location, DiagramView diagramView) {
        selectedDiagramElementFrom = diagramView.getElementAt(location);
        if (selectedDiagramElementFrom != null && selectedDiagramElementFrom instanceof Interclass) {
            diagramView.setLinePainter(new LinePainter(selectedDiagramElementFrom));
        }
        System.out.println("Adding Connection!");
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        DiagramElement selectedDiagramElementTo = diagramView.getElementAt(location);
        Diagram currentDiagram = diagramView.getDiagram();
        if (selectedDiagramElementTo != null && selectedDiagramElementTo instanceof Interclass) {
            Aggregation aggregation = new Aggregation("aggregation", currentDiagram, (Interclass) selectedDiagramElementFrom, (Interclass)selectedDiagramElementTo);
            diagramView.addPainter(aggregation);
        }
        diagramView.removeLinePainter();
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        diagramView.setLinePainterCoordinates(currentLocation);
    }

}
