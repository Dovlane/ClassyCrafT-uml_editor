package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

public class Project extends ClassyNodeComposite {

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
    public void addChild(ClassyNode child) {
        if (child instanceof Package) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
            }
        }
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

            // Check if the Project of the displayed package got a new author
            if (this == Package.getDisplayedPackage().findParentProject()) {
                Package.getDisplayedPackage().notifyAllSubscribers(null);
            }

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
