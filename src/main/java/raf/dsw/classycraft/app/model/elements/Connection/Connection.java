package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

@Getter
@Setter
public abstract class Connection extends DiagramElement {

    @JsonIdentityReference
    protected Interclass from;
    @JsonIdentityReference
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
        setAbsolutePath(createAbsolutePath());
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

}
