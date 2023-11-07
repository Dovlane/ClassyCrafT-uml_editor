package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeCellEditor;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;

public class PackageView extends JTabbedPane implements IListener {
    private Label labelProjectName;
    private Label labelAuthorName;
    public PackageView(Label labelProjectName, Label labelAuthorName) {
        this.labelProjectName = labelProjectName;
        this.labelAuthorName = labelAuthorName;
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof Package)
            updatePackageView((Package)notification);
    }
    public void updatePackageView(Package chosenPackage) {
        clearRightPanel();
        if (chosenPackage.getParent() == null) {
            chosenPackage = null;
            deleteProjectData();
            deleteTabView();
            return;
        }
        refreshProjectData(chosenPackage);

        for (ClassyNode child: chosenPackage.getChildren()) {
            if (child instanceof Diagram) {
                Diagram diagram = (Diagram)child;
                DiagramView diagramView = new DiagramView();
                diagram.addListener(diagramView);
                addTab(child.getName(), new DiagramView());
            }
        }
    }
    private ClassyTreeCellEditor getClassyTreeCellEditor(){
        ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation)  ApplicationFramework.getInstance().getClassyTree();
        ClassyTreeView classyTreeView = classyTreeImplementation.getTreeView();
        return classyTreeView.getClassyTreeCellEditor();
    }

    public void clearRightPanel(){
        deleteTabView();
        deleteProjectData();
    }
    public void deleteTabView(){
        this.removeAll();
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }
    public void refreshProjectData(Package chosenPackage){
        Project chosenProject = (Project) chosenPackage.getParent();
        labelAuthorName.setText(chosenProject.getAuthor());
        labelProjectName.setText(chosenProject.getName());
    }
    public void deleteProjectData(){
        labelAuthorName.setText("Author");
        labelProjectName.setText("Project name");
    }
}
