package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.PackageView;

public class ClassyRepository implements IClassyRepository {

    private ProjectExplorer projectExplorer;
    private PackageView packageView;

    public ClassyRepository() {
        projectExplorer = new ProjectExplorer("My Project Explorer");
        packageView = new PackageView();
    }

    @Override
    public ProjectExplorer getRoot() {
        return projectExplorer;
    }

    @Override
    public void printTree() {
        getRoot().printNode(0);
    }

    public PackageView getPackageView() {
        return packageView;
    }

}
