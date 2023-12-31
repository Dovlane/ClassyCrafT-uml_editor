package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public abstract class Connection extends DiagramElement {

    @JsonIdentityReference
    protected Interclass from;
    @JsonIdentityReference
    protected Interclass to;
    @JsonIgnore
    protected Point currentPointFrom;
    @JsonIgnore
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

    @JsonIgnore
    public boolean isReflexiveConnection() {
        return getFrom().equals(getTo());
    }

    public String getPlainName() {
        return from.getPlainName() + "-" + to.getPlainName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(this.getClass(), that.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, currentPointFrom, currentPointTo);
    }

    public boolean containsInterclass(Interclass interclass) {
        return getFrom().equals(interclass) || getTo().equals(interclass);
    }
}
