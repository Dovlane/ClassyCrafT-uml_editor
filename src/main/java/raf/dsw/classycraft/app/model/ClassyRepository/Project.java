package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

public class Project extends ClassyNodeComposite implements IPublisher {

    private String author;
    private String resourcePath;
    private int nmbOfCreatedPackages;

    public Project(String name, ClassyNode parent) {
        super(name, parent);
        nmbOfCreatedPackages = 0;
    }

    public Project(String projectName, ClassyNode parent, String author, String resourcePath) {
        super(projectName, parent);
        this.author = author;
        this.resourcePath = resourcePath;
        this.nmbOfCreatedPackages = 0;
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof Package) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
                Notification notification =
                        new Notification(child, NotificationType.ADD);
                notifyAllSubscribers(notification);
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

            return true;
        }
        else {
            String errorMessage = "Author name cannot be an empty string.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }

        return false;
    }

    public int getNmbOfCreatedPackages() {
        return nmbOfCreatedPackages;
    }

}
