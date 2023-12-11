package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

public class Notification {

    private ClassyNode node;
    private NotificationType type;

    public Notification(ClassyNode node, NotificationType type) {
        this.node = node;
        this.type = type;
    }

    public ClassyNode getNode() {
        return node;
    }

    public void setNode(ClassyNode node) {
        this.node = node;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

}
