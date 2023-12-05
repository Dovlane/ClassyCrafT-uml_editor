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

import java.awt.*;
import java.util.Random;

public class AddInterclassState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        Diagram currentDiagram = diagramView.getDiagram();
        ClassElement newClass = new ClassElement("Test Class" + (new Random()).nextInt(1), currentDiagram);

        ElementPainter painter = new ClassPainter(newClass);
        diagramView.addPainter(painter);

        ClassyTreeItem classyTreeDiagram =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
        if (classyTreeDiagram ==  null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
            return;
        }
        MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, newClass);

        // Debug
        ApplicationFramework.getInstance().getClassyRepository().printTree();
        System.out.println("Creating Interclass!");
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {

    }

    @Override
    public void mouseDragged(Point start, Point location, DiagramView diagramView) {

    }
}
