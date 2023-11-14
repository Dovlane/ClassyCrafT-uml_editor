package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

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

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNmbOfCreatedPackages() {
        return nmbOfCreatedPackages;
    }

}
