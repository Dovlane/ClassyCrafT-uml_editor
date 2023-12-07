package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

public class Diagram extends ClassyNodeComposite {

    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof DiagramElement) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
                Notification notification =
                        new Notification(child, NotificationType.ADD);
                notifyAllSubscribers(notification);
                return true;
            }
        }
        return false;
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener: listeners)
            listener.update(notification);
    }
}
