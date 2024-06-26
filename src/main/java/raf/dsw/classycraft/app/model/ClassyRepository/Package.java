package raf.dsw.classycraft.app.model.ClassyRepository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.Jackson.PackageDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

@Getter
@Setter
@JsonDeserialize(using = PackageDeserializer.class)
public class Package extends ClassyNodeComposite implements IPublisher {

    private int nmbOfCreatedPackages;
    private int nmbOfCreatedDiagrams;

    public Package(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof Package || child instanceof Diagram) {
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

    public Project findParentProject() {
        ClassyNode tmp = this;
        while ((tmp != null) && !(tmp instanceof Project)) {
            tmp = tmp.getParent();
        }
        return (Project) tmp;
    }

    public void increasePackageCounter() {
        nmbOfCreatedPackages += 1;
    }

    public void increaseDiagramCounter() {
        nmbOfCreatedDiagrams += 1;
    }

    public void display() {
        Notification notification =
                new Notification(this, NotificationType.SET);
        notifyAllSubscribers(notification);
    }

}
