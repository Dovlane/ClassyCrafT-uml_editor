package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;

import java.awt.*;

public class LassoElement extends DiagramElement{
    private Point firstPoint;
    private Point secondPoint;
    private Point lassoUpperLeft;
    private Point lassoBottomRight;
    public LassoElement(String name, Diagram parent, Point firstPoint, Point secondPoint) {
        super(name, parent);
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        setCorners();
    }
    private void setCorners() {
        lassoUpperLeft = new Point(
                Math.min(firstPoint.x, secondPoint.x),
                Math.min(firstPoint.y, secondPoint.y)
        );
        lassoBottomRight = new Point(
                Math.max(firstPoint.x, secondPoint.x),
                Math.max(firstPoint.y, secondPoint.y)
        );
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
        setCorners();
        Notification notification = new Notification(this, NotificationType.SET);
        notifyAllSubscribers(notification);
    }

    public Point getLassoUpperLeft() {
        setCorners();
        return lassoUpperLeft;
    }

    public Point getLassoBottomRight() {
        setCorners();
        return lassoBottomRight;
    }
}
