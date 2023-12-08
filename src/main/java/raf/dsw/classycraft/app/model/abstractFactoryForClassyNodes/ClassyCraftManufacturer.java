package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public class ClassyCraftManufacturer extends AbstractClassyCraftManufacturer {
    @Override
    public Interclass createInterclass(InfoForCreatingInterclass infoForCreatingInterclass) {
        String name = infoForCreatingInterclass.getName();
        Diagram parent = (Diagram) infoForCreatingInterclass.getParent();
        Point point = infoForCreatingInterclass.getPoint();
        AccessModifiers visibility = infoForCreatingInterclass.getVisibility();
        NonAccessModifiers nonAccessModifiers = infoForCreatingInterclass.getNonAccessModifier();
        ElementInterclassType elementInterclassType = infoForCreatingInterclass.getElementInterclassType();

        if (elementInterclassType == ElementInterclassType.CLASS)
            return new ClassElement(name, parent, point, visibility, nonAccessModifiers);
        else if (elementInterclassType == ElementInterclassType.INTERFACE)
            return new InterfaceElement(name, parent, point, visibility, nonAccessModifiers);
        else if (elementInterclassType == ElementInterclassType.ENUM)
            return new EnumElement(name, parent, point, visibility, nonAccessModifiers);
        return null;
    }

    @Override
    public Connection createConnection(InfoForCreatingConnection infoForCreatingConnection) {
        String name = infoForCreatingConnection.getName();
        Diagram parent = (Diagram) infoForCreatingConnection.getParent();
        Interclass from = infoForCreatingConnection.getFrom();
        Interclass to = infoForCreatingConnection.getTo();
        ElementConnectionType elementConnectionType = infoForCreatingConnection.getElementConnectionType();

        if (elementConnectionType == ElementConnectionType.AGGREGATION)
            return new Aggregation(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.COMPOSITION)
            return new Composition(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.DEPENDENDCY)
            return new Dependency(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.GENERALISATION)
            return new Generalization(name, parent, from, to);
        return null;
    }


    public ClassyNode createClassyNode (InfoForCreatingClassyNodeCompositeNodes infoForCreatingClassyNodeCompositeNodes) {
        ClassyNode parent = infoForCreatingClassyNodeCompositeNodes.getParent().getClassyNode();
        ClassyNodeType type = infoForCreatingClassyNodeCompositeNodes.getType();
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
        }
        else if (parent instanceof Project) {
            if (type == ClassyNodeType.PACKAGE) {
                ((Project) parent).increaseCounter();
                String newPackageName = "Package" + ((Project) parent).getNmbOfCreatedPackages();
                while (((Project) parent).getChildByName(newPackageName) != null) {
                    ((Project) parent).increaseCounter();
                    newPackageName = "Package" + ((Project) parent).getNmbOfCreatedPackages();
                }
                return new raf.dsw.classycraft.app.model.ClassyRepository.Package(newPackageName, parent);
            } else {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Project can only contain Packages.", MessageType.ERROR);
            }
        }
        else if (parent instanceof raf.dsw.classycraft.app.model.ClassyRepository.Package) {
            if (type == ClassyNodeType.PACKAGE){
                ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).increasePackageCounter();
                String newPackageName = "Package" + ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).getNmbOfCreatedPackages();
                while (((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).getChildByName(newPackageName) != null) {
                    ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).increasePackageCounter();
                    newPackageName = "Package" + ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).getNmbOfCreatedPackages();
                }
                return new raf.dsw.classycraft.app.model.ClassyRepository.Package(newPackageName, parent);
            } else if (type == ClassyNodeType.DIAGRAM) {
                ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).increaseDiagramCounter();
                String newDiagramName = "Diagram" + ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).getNmbOfCreatedDiagrams();
                while (((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).getChildByName(newDiagramName) != null) {
                    ((raf.dsw.classycraft.app.model.ClassyRepository.Package) parent).increaseDiagramCounter();
                    newDiagramName = "Diagram" + ((Package) parent).getNmbOfCreatedDiagrams();
                }
                return new Diagram(newDiagramName, parent);
            } else {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Package can only contain Diagrams and other Packages.", MessageType.ERROR);
            }
        }
        else if (parent instanceof Diagram) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Diagram can only contain Diagram Elements.", MessageType.ERROR);
        }
        return null;
    }
}
