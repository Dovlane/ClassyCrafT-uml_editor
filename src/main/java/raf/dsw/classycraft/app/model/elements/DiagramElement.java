package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

public abstract class DiagramElement extends ClassyNodeLeaf implements IPublisher {

    List<IListener> listeners;

    public DiagramElement(String name, Diagram parent) {
        super(name, parent);
        listeners = new ArrayList<>();
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
