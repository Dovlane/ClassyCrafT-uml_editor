package raf.dsw.classycraft.app.model.compositePattern;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

public abstract class ClassyNode {

    private String name;
    private ClassyNode parent;

    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
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

        // If current Classy Node is displayed, it should
        // notify the PackageView about its removal.
        if (this == Package.getDisplayedPackage()) {
            Package.getDisplayedPackage().notifyAllSubscribers(Package.getDefaultPackage());
            Package.setDisplayedPackage(Package.getDefaultPackage());
        }

        ClassyNodeComposite parent = (ClassyNodeComposite) getParent();
        if (parent != null) {

            if (this instanceof Diagram) {
                Package.getDisplayedPackage().notifyAllSubscribers(this);
            }

            if (this instanceof DiagramElement) {
                ((Diagram) parent).notifyAllSubscribers(this);
            }

            // Remove the unwanted item
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

            // Check if the Project of the displayed package is renamed
            if ((this instanceof Project) && (this == Package.getDisplayedPackage().findParentProject())) {

                // null is equivalent to updatePackageView
                Package.getDisplayedPackage().notifyAllSubscribers(null);

            }

            // Check if the Diagram of the displayed package is renamed
            if ((this instanceof Diagram) && (getParent() == Package.getDisplayedPackage())) {

                // null is equivalent to updatePackageView
                Package.getDisplayedPackage().notifyAllSubscribers(null);

            }

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

}
