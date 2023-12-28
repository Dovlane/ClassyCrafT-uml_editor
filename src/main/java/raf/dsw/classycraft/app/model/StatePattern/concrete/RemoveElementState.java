package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.RemoveDiagramElementCommand;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;

public class RemoveElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside RemoveElementState");

        // Find the DiagramElement which was clicked
        boolean inSelectionModel = false;
        List<DiagramElement> diagramElementListToDelete = new ArrayList<>();
        for (ElementPainter elementPainter: diagramView.getPainters()) {
            if (elementPainter.elementAt(location)) {
                DiagramElement diagramElement = elementPainter.getDiagramElement();
                inSelectionModel = diagramView.getSelectionModel().contains(elementPainter);
                if (!inSelectionModel)
                    diagramElementListToDelete.add(diagramElement);
            }
        }

        if (inSelectionModel) {
            for (ElementPainter selectedElementPainter : diagramView.getSelectionModel()) {
                DiagramElement diagramElement = selectedElementPainter.getDiagramElement();
                diagramElementListToDelete.add(diagramElement);
            }
        }

        if (diagramElementListToDelete.size() > 0) {
            IClassyTree iClassyTree =  MainFrame.getInstance().getClassyTree();
            Diagram diagram = diagramView.getDiagram();
            ClassyTreeItem diagramTreeItem = MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(diagram);
            AbstractCommand removeDiagramElementCommand = new RemoveDiagramElementCommand(iClassyTree, diagramTreeItem, diagramElementListToDelete);
            diagramView.getCommandManager().addCommand(removeDiagramElementCommand);
        }
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside RemoveElementState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of RemoveElementState from " + startLocation + " to " + currentLocation);
        System.out.println("Optimal location: " + currentLocationOptimal);

    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of RemoveElementState");

    }

}