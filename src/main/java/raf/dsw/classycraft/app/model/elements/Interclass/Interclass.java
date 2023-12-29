package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingConnection;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.Dictionary;

public abstract class Interclass extends DiagramElement {

    private static int initialBoxWidth = 200;
    private static int initialBoxHeight = 100;
    protected AccessModifiers visibility;
    protected NonAccessModifiers nonAccessModifiers;

    protected Point location;
    protected int boxWidth;
    protected int boxHeight;
    private int numberOfCopies;
    @JsonIgnore
    protected Point[] connectionAttachingPoints;

    public Interclass(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent);
        this.visibility = visibility;
        this.boxWidth = 200;
        this.boxHeight = 100;
        this.location = point;
        this.nonAccessModifiers = nonAccessModifiers;
    }

    // Create a Deep Copy Constructor
    public Interclass(Interclass interclass) {
        this(interclass.getName() + "Copy" + interclass.getNumberOfCopies(true),
                (Diagram) interclass.getParent(),
                new Point(interclass.getLocation()),
                interclass.visibility,
                interclass.nonAccessModifiers);
    }

    // Getters and Setters
    public void updateAbsoluteLocation(Point location) {
        this.location = location;
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    public void translate(Point t) {
        updateAbsoluteLocation(new Point(location.x + t.x, location.y + t.y));
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
        changeOccurred();
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
        changeOccurred();
    }

    public static int getInitialBoxWidth() {
        return initialBoxWidth;
    }

    public static int getInitialBoxHeight() {
        return initialBoxHeight;
    }

    public Point[] getConnectionAttachingPoints() {
        int locationX = (int)location.getX();
        int locationY = (int)location.getY();


        int numberOfPointsPerSide = 21;
        int width = getBoxWidth();
        int height = getBoxHeight();
        int delta_x = width / (numberOfPointsPerSide - 1);
        int delta_y = height / (numberOfPointsPerSide - 1);
        Point[] points = new Point[numberOfPointsPerSide * 4];

        for (int i = 0; i < numberOfPointsPerSide; i++) {
            int x = locationX + delta_x * i;
            int y = locationY;
            points[i] = new Point(x, y);

            x = locationX + delta_x * i;
            y = locationY + height;
            points[i + numberOfPointsPerSide] = new Point(x, y);

            x = locationX;
            y = locationY + delta_y * i;
            points[i + 2 * numberOfPointsPerSide] = new Point(x, y);

            x = locationX + width;
            y = locationY + delta_y * i;
            points[i + 3 * numberOfPointsPerSide] = new Point(x, y);
        }

        return points;
    }

    public AccessModifiers getVisibility() {
        return visibility;
    }

    public Point getBestStartingPoint(Point endPoint) {
        Point[] potentialStartingPoints = getConnectionAttachingPoints();
        Point bestStartingPoint = potentialStartingPoints[0];
        double minDistance = Point.distance(bestStartingPoint.getX(), bestStartingPoint.getY(), endPoint.getX(), endPoint.getY());
        for (int i = 1; i < potentialStartingPoints.length; i++) {
            Point startingPoint = potentialStartingPoints[i];
            double distance = Point.distance(startingPoint.getX(), startingPoint.getY(), endPoint.getX(), endPoint.getY());
            if (minDistance > distance) {
                minDistance = distance;
                bestStartingPoint = startingPoint;

            }
        }
        return bestStartingPoint;
    }

    public NonAccessModifiers getNonAccessModifiers() {
        return nonAccessModifiers;
    }

    public int getNumberOfCopies(boolean updateNumberOfCopies) {
        if (updateNumberOfCopies)
            numberOfCopies++;
        return numberOfCopies;
    }

    public void setVisibility(AccessModifiers visibility) {
        this.visibility = visibility;
        notifyAllSubscribers(new Notification(this, NotificationType.SET));
        changeOccurred();
    }

    public void setNonAccessModifiers(NonAccessModifiers nonAccessModifiers) {
        this.nonAccessModifiers = nonAccessModifiers;
        notifyAllSubscribers(new Notification(this, NotificationType.SET));
        changeOccurred();
    }
}
