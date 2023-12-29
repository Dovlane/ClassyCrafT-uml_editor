package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.RemoveDiagramElementCommand;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.util.List;

import java.awt.*;
import java.util.ArrayList;

public class RemoveElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside RemoveElementState");

        List<Interclass> interclassesToDelete = new ArrayList<>();
        List<Connection> connectionsToDelete = new ArrayList<>();
        boolean inSelectionModel = false;
        //List<DiagramElement> diagramElementListToDelete = new ArrayList<>();
        for (ElementPainter elementPainter: diagramView.getPainters()) {
            if (elementPainter.elementAt(location)) {
                DiagramElement diagramElement = elementPainter.getDiagramElement();
                inSelectionModel = diagramView.getSelectionModel().contains(elementPainter);
                // we only add those which are not in selection model, because those in selection model will be added later
                if (!inSelectionModel) {
                    if (diagramElement instanceof Interclass) {
                        interclassesToDelete.add((Interclass) diagramElement);
                    }
                    else if (diagramElement instanceof Connection) {
                        connectionsToDelete.add((Connection) diagramElement);
                    }
                }
            }
        }

        if (inSelectionModel) {
            // now we are adding diagramElements from selectionModel that should be deleted
            for (ElementPainter selectedElementPainter : diagramView.getSelectionModel()) {
                DiagramElement diagramElement = selectedElementPainter.getDiagramElement();
                if (diagramElement instanceof Interclass) {
                    interclassesToDelete.add((Interclass) diagramElement);
                }
                else if (diagramElement instanceof Connection) {
                    connectionsToDelete.add((Connection) diagramElement);
                }
            }
        }

        // at the end, we are adding connections that are connected to interclasses that are going to be deleted
        for (Interclass interclassToDelete : interclassesToDelete) {
            for (ElementPainter elementPainter : diagramView.getPainters()) {
                if (elementPainter instanceof ConnectionPainter) {
                    Connection connection = (Connection) elementPainter.getDiagramElement();
                    if (connection.containsInterclass(interclassToDelete) && !connectionsToDelete.contains(connection)) {
                        connectionsToDelete.add(connection);
                    }
                }
            }
        }


        if (interclassesToDelete.size() > 0 || connectionsToDelete.size() > 0) {
            IClassyTree iClassyTree =  MainFrame.getInstance().getClassyTree();
            Diagram diagram = diagramView.getDiagram();
            ClassyTreeItem diagramTreeItem = MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(diagram);
            AbstractCommand removeDiagramElementCommand = new RemoveDiagramElementCommand(iClassyTree, interclassesToDelete, connectionsToDelete, diagramTreeItem);
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