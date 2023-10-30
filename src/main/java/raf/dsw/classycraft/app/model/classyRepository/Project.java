package raf.dsw.classycraft.app.model.classyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

public class Project extends ClassyNodeComposite {
    private String projectName;
    private String author;
    private String resourcePath;

    public Project(ClassyNode parent, String projectName, String author, String resourcePath) {
        super(parent);
        this.projectName = projectName;
        this.author = author;
        this.resourcePath = resourcePath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
}
