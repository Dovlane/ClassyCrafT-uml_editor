package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.LineElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LinePainter extends ElementPainter {

    public LinePainter(LineElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }

    @Override
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

    private LineElement getLineElement() {return (LineElement) diagramElement;}

    private Interclass getInterclass() {return getLineElement().getInterclassElement();}

    private Point getCurrentPoint() {return  getLineElement().getCurrentPoint(); }

    private Point getStartingPoint() {return getInterclass().getBestStartingPoint(getCurrentPoint()); }

}
