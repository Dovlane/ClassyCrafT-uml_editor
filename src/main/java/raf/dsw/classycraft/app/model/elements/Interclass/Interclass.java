package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public abstract class Interclass extends DiagramElement {
    private static int initialBoxWidth = 200;
    private static int initialBoxHeight = 100;
    protected AccessModifiers visibility;
    protected NonAccessModifiers nonAccessModifiers;
    protected Point location;
    protected int boxWidth;
    protected int boxHeight;

    public Interclass(String name, Diagram parent) {
        super(name, parent);
    }

    public Interclass(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent);
        this.visibility = visibility;
        this.boxWidth = 200;
        this.boxHeight = 100;
        this.location = point;
        this.nonAccessModifiers = nonAccessModifiers;
    }

    // Getters and Setters
    public void updateAbsoluteLocation(Point location) {
        this.location = location;
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
    }

    public void translate(Point t) {
        updateAbsoluteLocation(new Point(location.x + t.x, location.y + t.y));
    }

    public Point getLocation() {
        return location;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }

    public static int getInitialBoxWidth() {
        return initialBoxWidth;
    }

    public static int getInitialBoxHeight() {
        return initialBoxHeight;
    }
}
