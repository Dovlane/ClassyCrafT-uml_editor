package raf.dsw.classycraft.app.model.ClassyRepository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.Jackson.DiagramDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

@Getter
@Setter
@JsonDeserialize(using = DiagramDeserializer.class)
public class Diagram extends ClassyNodeComposite {

    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof DiagramElement) {
            if (getChildByName(child.getName()) == null) {
                getChildren().add(child);
                Notification notification =
                        new Notification(child, NotificationType.ADD);
                notifyAllSubscribers(notification);
                changeOccurred();
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

    @Override
    public void changeOccurred() {
        getParent().changeOccurred();
    }

}
