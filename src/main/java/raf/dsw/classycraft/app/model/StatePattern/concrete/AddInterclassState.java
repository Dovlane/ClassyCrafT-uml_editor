package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.ClassPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;

public class AddInterclassState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        Diagram currentDiagram = diagramView.getDiagram();
        ClassElement newClass1 = new ClassElement("Test Class1", currentDiagram, new Point(200, 200), AccessModifiers.PUBLIC);
        ClassElement newClass2 = new ClassElement("Test Class2", currentDiagram, new Point(50, 50), AccessModifiers.PUBLIC);

        ClassyTreeItem classyTreeDiagram =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
        if (classyTreeDiagram ==  null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
            return;
        }
        if (MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newClass1)) {
            ElementPainter painter1 = new ClassPainter(newClass1);
            diagramView.addPainter(painter1);
        }
        if (MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newClass2)) {
            ElementPainter painter2 = new ClassPainter(newClass2);
            diagramView.addPainter(painter2);
        }

        // Debug
        ApplicationFramework.getInstance().getClassyRepository().printTree();
        System.out.println("Creating Interclass!");
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {

    }
}
