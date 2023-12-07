package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

public class Diagram extends ClassyNodeComposite implements IPublisher {

    List<IListener> listeners;

    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
        listeners = new ArrayList<>();
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof DiagramElement) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
                notifyAllSubscribers(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addListener(IListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener: listeners)
                listener.update(notification);
    }
}
