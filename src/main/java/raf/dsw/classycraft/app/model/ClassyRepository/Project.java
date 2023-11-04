package raf.dsw.classycraft.app.model.ClassyRepository;

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
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public int getNmbOfCreatedPackages() {
        return nmbOfCreatedPackages;
    }

    public void setNmbOfCreatedPackages(int nmbOfCreatedPackages) {
        this.nmbOfCreatedPackages = nmbOfCreatedPackages;
    }

}
