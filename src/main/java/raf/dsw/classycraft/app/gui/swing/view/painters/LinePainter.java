package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class LinePainter {
    private Interclass interclass;
    private Point currentPoint;

    public LinePainter(Interclass interclass) {
        this.interclass = interclass;
        this.currentPoint = interclass.getLocation();

    }


    public void draw(Graphics2D graphics2D) {
        Stroke previousStroke = graphics2D.getStroke();
        Color lastColor = graphics2D.getColor();
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawLine((int)getStartingPoint().getX(), (int)getStartingPoint().getY(), (int) getCurrentPoint().getX(), (int) getCurrentPoint().getY());
//        System.out.println("getStartingPoint() " + getStartingPoint());
//        System.out.println("getCurrentPoint() " + getCurrentPoint());
        graphics2D.setStroke(previousStroke);
        graphics2D.setColor(lastColor);
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }
    private Point getCurrentPoint() {return  currentPoint; }
    private Point getStartingPoint() {return interclass.getBestStartingPoint(currentPoint); }

}
