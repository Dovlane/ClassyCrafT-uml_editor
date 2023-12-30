package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

public abstract class DiagramElement extends ClassyNodeLeaf implements IPublisher {

    public DiagramElement(String name, Diagram parent) {
        super(name, parent);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener: listeners)
            listener.update(notification);
    }

}
