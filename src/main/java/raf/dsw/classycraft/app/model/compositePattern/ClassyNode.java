package raf.dsw.classycraft.app.model.compositePattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.JacksonSerializer.ClassyNodeDeserializer;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "Project"),
        @JsonSubTypes.Type(value = Package.class, name = "Package"),
        @JsonSubTypes.Type(value = Diagram.class, name = "Diagram"),

        @JsonSubTypes.Type(value = ClassElement.class, name = "ClassElement"),
        @JsonSubTypes.Type(value = InterfaceElement.class, name = "InterfaceElement"),
        @JsonSubTypes.Type(value = EnumElement.class, name = "EnumElement"),

        @JsonSubTypes.Type(value = Aggregation.class, name = "Aggregation"),
        @JsonSubTypes.Type(value = Composition.class, name = "Composition"),
        @JsonSubTypes.Type(value = Dependency.class, name = "Dependency"),
        @JsonSubTypes.Type(value = Generalization.class, name = "Generalization")
})
@JsonDeserialize(using = ClassyNodeDeserializer.class)
public abstract class ClassyNode implements IPublisher {

    protected String name;
    @JsonIgnore
    protected ClassyNode parent;
    @JsonIgnore
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

            // If Interclass element is removed than all its connections should be removed
            if (this instanceof Interclass) {

                // Create a list of all connections that should be removed
                // Avoid ConcurrentModificationException
                List<ClassyNode> connectionsForRemoval = new ArrayList<>();
                for (ClassyNode child: parent.getChildren()) {
                    if (child instanceof Connection) {
                        Connection connection = (Connection) child;
                        if (connection.getFrom().equals(this) || connection.getTo().equals(this)) {
                            connectionsForRemoval.add(child);
                        }
                    }
                }

                // Actually remove all obsolete connections
                for (ClassyNode child: connectionsForRemoval) {
                    ClassyTreeItem treeItemDiagramElement =
                            MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(child);
                    MainFrame.getInstance().getClassyTree().removeItem(treeItemDiagramElement);
                }

            }

            // Notify about the child removal
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

        // Recursive base case
        if (getParent() == null) {
            return getName();
        }

        // Check type of Interclass
        String suffix = "";
        if (this instanceof ClassElement) {
            suffix = "Class-";
        }
        else if (this instanceof InterfaceElement) {
            suffix = "Interface-";
        }
        else if (this instanceof EnumElement) {
            suffix = "Enum-";
        }

        return getParent().getAbsolutePath() + "/" + suffix + getName();
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
            parent.notifyAllSubscribers(notification);
            changeOccurred();

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

    public abstract void changeOccurred();

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
