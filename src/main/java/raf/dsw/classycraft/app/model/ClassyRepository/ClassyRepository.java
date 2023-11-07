package raf.dsw.classycraft.app.model.ClassyRepository;

public class ClassyRepository implements IClassyRepository {
    private ProjectExplorer projectExplorer;
    public ClassyRepository() {
        projectExplorer = new ProjectExplorer("My Project Explorer");
    }

    @Override
    public ProjectExplorer getRoot() {
        return projectExplorer;
    }

    @Override
    public void printTree() {
        getRoot().printNode(0);
    }

}
