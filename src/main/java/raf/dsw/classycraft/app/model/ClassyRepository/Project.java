package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project extends ClassyNodeComposite implements IPublisher {

    private String author;
    private String folderPath;
    private boolean changed;
    private int nmbOfCreatedPackages;

    public Project(String name, ClassyNode parent) {
        super(name, parent);
        this.changed = true;
    }

    public Project(String projectName, ClassyNode parent, String author, String folderPath) {
        super(projectName, parent);
        this.author = author;
        this.folderPath = folderPath;
        this.changed = true;
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof Package) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
                Notification notification =
                        new Notification(child, NotificationType.ADD);
                notifyAllSubscribers(notification);
                setChanged(true);
                return true;
            }
        }
        return false;
    }

    public void increaseCounter() {
        nmbOfCreatedPackages += 1;
    }

    public String getAuthor() {
        if (author == null)
            return "";
        return author;
    }

    public boolean setAuthor(String author) {

        // Check if the same author name is entered
        if (getAuthor().equals(author)) {
            return true;
        }

        if (!author.isEmpty()) {

            // Set new author
            this.author = author;
            System.out.println(this.author + " has been set as an author of the project " + this.getName() + ".");

            // Notify PackageView about the potential change in Package Metadata
            Notification notification =
                    new Notification(this, NotificationType.SET);
            notifyAllSubscribers(notification);
            setChanged(true);

            return true;
        }
        else {
            String errorMessage = "Author name cannot be an empty string.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }

        return false;
    }

    @Override
    public void changeOccurred() {
        setChanged(true);
    }

}
