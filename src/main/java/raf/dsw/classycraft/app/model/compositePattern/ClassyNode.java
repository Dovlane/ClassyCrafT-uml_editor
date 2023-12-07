package raf.dsw.classycraft.app.model.compositePattern;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassyNode implements IPublisher {

    protected String name;
    protected ClassyNode parent;
    protected List<IListener> listeners;

    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
        listeners = new ArrayList<>();
    }

    public void removeSubtree() {
        if (this instanceof ClassyNodeComposite) {
            while (((ClassyNodeComposite) this).getChildCount() > 0) {
                ((ClassyNodeComposite) this).getChildren().get(0).removeSubtree();
            }
        }
        removeFromParent();
    }

    public void removeFromParent() {

        ClassyNodeComposite parent = (ClassyNodeComposite) getParent();
        if (parent != null) {
            Notification notification =
                    new Notification(this, NotificationType.REMOVE);
            parent.notifyAllSubscribers(notification);
            parent.removeAt(this);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClassyNode) {
            return getAbsolutePath().equals(((ClassyNode) object).getAbsolutePath());
        }
        return false;
    }

    public String getAbsolutePath() {
        if (getParent() == null) {
            return getName();
        }
        return getParent().getAbsolutePath() + "/" + getName();
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {

        // Check if the same name is entered
        if (getName().equals(name)) {
            return true;
        }

        ClassyNodeComposite parent = (ClassyNodeComposite) this.parent;
        if (!name.isEmpty() && parent.getChildByName(name) == null) {

            // Set new name
            String oldName = this.name;
            this.name = name;
            System.out.println(oldName + " has been renamed to " + name + ".");

            // Notify PackageView about the potential change in Package Metadata
            Notification notification =
                    new Notification(this, NotificationType.SET);
            notifyAllSubscribers(notification);

            return true;
        }
        else if (name.isEmpty()) {
            String errorMessage = "New name cannot be an empty string.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }
        else {
            System.out.println("Ambiguous name is " + name);
            String errorMessage = "The path of the file is ambiguous.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }

        return false;
    }

    public ClassyNode getParent() {
        return parent;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

    @Override
    public void addListener(IListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {

        // There will be only 1 listener in the array of
        // listeners, because there is only one PackageView.

        // Problem with for-each loop is because every time
        // the update happens the listener should be removed
        // from the listeners array list.
        // It raises ConcurrentModificationException.

        // Solution is to only use the first element of the
        // list which is at the same time the only listener.

        listeners.get(0).update(notification);
    }

}
