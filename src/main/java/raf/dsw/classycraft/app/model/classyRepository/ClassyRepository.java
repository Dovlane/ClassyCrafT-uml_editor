package raf.dsw.classycraft.app.model.classyRepository;

public class ClassyRepository implements IClassyRepository{
    private ProjectExplorer projectExplorer;

    public ClassyRepository() {
        this.projectExplorer = new ProjectExplorer(null);
    }

    public ProjectExplorer getRoot() {
        return projectExplorer;
    }
}
