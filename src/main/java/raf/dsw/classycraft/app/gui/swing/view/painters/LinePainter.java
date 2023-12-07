package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class LinePainter extends ElementPainter {
    private Point currentPoint;

    public LinePainter(DiagramElement diagramElement) {
        super(diagramElement);
        currentPoint = ((Interclass)diagramElement).getLocation();
    }

    public LinePainter(Interclass diagramElement, Point b) {
        super(diagramElement);
        currentPoint = b;
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }



    public void draw(Graphics2D graphics2D) {
        Stroke previousStroke = graphics2D.getStroke();
        Color lastColor = graphics2D.getColor();
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawLine((int)getStartingPoint().getX(), (int)getStartingPoint().getY(), (int) getCurrentPoint().getX(), (int) getCurrentPoint().getY());
        graphics2D.setStroke(previousStroke);
        graphics2D.setColor(lastColor);
    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    @Override
    public DiagramElement getDiagramElement() {
        return super.getDiagramElement();
    }

    private Interclass getInterclass() {return (Interclass) diagramElement;}

    private Point getStartingPoint() {return getInterclass().getBestStartingPoint(getCurrentPoint()); }
    private Point getCurrentPoint() {
        return currentPoint;
    }
    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }


}
