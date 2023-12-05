package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

public class ProjectExplorer extends ClassyNodeComposite {

    private int nmbOfCreatedProjects;

    public ProjectExplorer(String name) {
        super(name, null);
        nmbOfCreatedProjects = 0;
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof Project) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
                return true;
            }
        }
        return false;
    }

    public void increaseCounter() {
        nmbOfCreatedProjects += 1;
    }

    public int getNmbOfCreatedProjects() {
        return nmbOfCreatedProjects;
    }

}
