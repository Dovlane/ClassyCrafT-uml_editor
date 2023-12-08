package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class LineElement extends DiagramElement {
    private Interclass interclassElement;
    private Point currentPoint;
    public LineElement(String name, Diagram parent, Interclass interclassElement) {
        super(name, parent);
        this.currentPoint = interclassElement.getLocation();
        this.interclassElement = interclassElement;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
        Notification notification = new Notification(this, NotificationType.SET);
        notifyAllSubscribers(notification);
    }

    public Interclass getInterclassElement() {
        return interclassElement;
    }
}
