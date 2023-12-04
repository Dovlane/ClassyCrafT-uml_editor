package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;

import java.util.ArrayList;
import java.util.List;

public class ClassyRepository implements IClassyRepository {

    private ProjectExplorer projectExplorer;
    private PackageView packageView;
    private List<DiagramView> diagramViewList;

    public ClassyRepository() {
        projectExplorer = new ProjectExplorer("My Project Explorer");
        packageView = new PackageView();
        diagramViewList = new ArrayList<>();
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

    public void addDiagramView(Diagram diagram) {
        DiagramView diagramView = new DiagramView(diagram);
        diagramViewList.add(diagramView);
    }

    public List<DiagramView> getDiagramViewList() {
        return diagramViewList;
    }
}
