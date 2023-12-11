package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public abstract class Connection extends DiagramElement {

    protected Interclass from;
    protected Interclass to;
    protected Point currentPointFrom;
    protected Point currentPointTo;

    public Connection(String name, Diagram parent) {
        super(name, parent);
    }

    public Connection(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent);
        this.from = from;
        this.to = to;
    }

    public void getBestPairOfStartAndEndPoint() {
        Point[] fromPoints = from.getConnectionAttachingPoints();
        Point[] toPoints = to.getConnectionAttachingPoints();
        currentPointFrom = fromPoints[0];
        currentPointTo = toPoints[0];
        double minDistance = distanceBetween(currentPointFrom, currentPointTo);

        for (int i = 0; i < fromPoints.length; i++) {
            for (int j = 1; j < toPoints.length; j++) {
                Point fromPoint = fromPoints[i];
                Point toPoint = toPoints[j];
                double distance = distanceBetween(fromPoint, toPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    currentPointFrom = fromPoint;
                    currentPointTo = toPoint;
                }
            }
        }
    }

    private static double distanceBetween(Point fromPoint, Point toPoint) {
        return Point.distance(fromPoint.getX(), fromPoint.getY(), toPoint.getX(), toPoint.getY());
    }

    public boolean isReflexiveConnection() {
        return getFrom().equals(getTo());
    }

    @Override
    public String getName() {
        String prefix = "";
        if (this instanceof Aggregation)
            prefix = "agg";
        else if (this instanceof Composition)
            prefix = "com";
        else if (this instanceof Dependency)
            prefix = "dep";
        else
            prefix = "gen";
        return prefix + "-" + from.getName() +
                 "-" + to.getName();
    }


    public Point getCurrentPointFrom() {
        return currentPointFrom;
    }

    public Point getCurrentPointTo() {
        return currentPointTo;
    }

    // Getters and Setters
    public Interclass getFrom() {
        return from;
    }

    public void setFrom(Interclass from) {
        this.from = from;
    }

    public Interclass getTo() {
        return to;
    }

    public void setTo(Interclass to) {
        this.to = to;
    }

}
