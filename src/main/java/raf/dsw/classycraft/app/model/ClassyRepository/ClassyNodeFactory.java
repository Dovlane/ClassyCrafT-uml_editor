package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

public class ClassyNodeFactory {

    public static ClassyNode createClassyNode (ClassyNode parent, ClassyNodeType type) {
        if (parent instanceof ProjectExplorer) {
            if (type == ClassyNodeType.PROJECT) {
                ((ProjectExplorer) parent).increaseCounter();
                String newProjectName = "Project" + ((ProjectExplorer) parent).getNmbOfCreatedProjects();
                while (((ProjectExplorer) parent).getChildByName(newProjectName) != null) {
                    ((ProjectExplorer) parent).increaseCounter();
                    newProjectName = "Project" + ((ProjectExplorer) parent).getNmbOfCreatedProjects();
                }
                return new Project(newProjectName, parent);
            } else {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "ProjectExplorer can only contain Projects.", MessageType.ERROR);
            }
        } else if (parent instanceof Project) {
            if (type == ClassyNodeType.PACKAGE) {
                ((Project) parent).increaseCounter();
                String newPackageName = "Package" + ((Project) parent).getNmbOfCreatedPackages();
                while (((Project) parent).getChildByName(newPackageName) != null) {
                    ((Project) parent).increaseCounter();
                    newPackageName = "Package" + ((Project) parent).getNmbOfCreatedPackages();
                }
                return new Package(newPackageName, parent);
            } else {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Project can only contain Packages.", MessageType.ERROR);
            }
        } else if (parent instanceof Package) {
            if (type == ClassyNodeType.PACKAGE){
                ((Package) parent).increasePackageCounter();
                String newPackageName = "Package" + ((Package) parent).getNmbOfCreatedPackages();
                while (((Package) parent).getChildByName(newPackageName) != null) {
                    ((Package) parent).increasePackageCounter();
                    newPackageName = "Package" + ((Package) parent).getNmbOfCreatedPackages();
                }
                return new Package(newPackageName, parent);
            } else if (type == ClassyNodeType.DIAGRAM) {
                ((Package) parent).increaseDiagramCounter();
                String newDiagramName = "Diagram" + ((Package) parent).getNmbOfCreatedDiagrams();
                while (((Package) parent).getChildByName(newDiagramName) != null) {
                    ((Package) parent).increaseDiagramCounter();
                    newDiagramName = "Diagram" + ((Package) parent).getNmbOfCreatedDiagrams();
                }
                return new Diagram(newDiagramName, parent);
            } else {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Package can only contain Diagrams and other Packages.", MessageType.ERROR);
            }
        }
        return null;
    }

}
