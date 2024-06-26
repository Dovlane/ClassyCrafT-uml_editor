package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ConnectionStateDialog;
import raf.dsw.classycraft.app.gui.swing.view.painters.LinePainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.AddConnectionCommand;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.ElementConnectionType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddConnectionState implements State {

    DiagramElement selectedDiagramElementFrom;
    LinePainter linePainter;

    ConnectionStateDialog connectionStateDialog;

    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of AddConnectionState");

        selectedDiagramElementFrom = diagramView.getElementAt(location);

        if (selectedDiagramElementFrom != null && selectedDiagramElementFrom instanceof Interclass) {
            linePainter = new LinePainter((Interclass) selectedDiagramElementFrom);
            System.out.println("linePainter " + linePainter);
            diagramView.updateLinePainter(linePainter);
        }
        else {
            selectedDiagramElementFrom = null; // this is case when selectedDiagramElement is not type of Interclass
        }
    }


    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of AddConnectionState");

        DiagramElement selectedDiagramElementTo = diagramView.getElementAt(location);
        Diagram currentDiagram = diagramView.getDiagram();
        if (selectedDiagramElementFrom != null) {
            if (selectedDiagramElementTo != null && selectedDiagramElementTo instanceof Interclass) {
                connectionStateDialog = new ConnectionStateDialog();
                connectionStateDialog.getButtonOk().addActionListener(e -> {
                    ClassyTreeItem classyTreeDiagram =
                            MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
                    if (classyTreeDiagram == null) {
                        MainFrame.getInstance().getMessageGenerator().generateMessage(
                                "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
                        return;
                    }
                    ElementConnectionType elementConnectionType = connectionStateDialog.getElementConnectionType();
                    if (elementConnectionType == ElementConnectionType.GENERALISATION && selectedDiagramElementFrom.equals(selectedDiagramElementTo)) {
                        MainFrame.getInstance().getMessageGenerator().generateMessage("Generalisation connection can't be reflexive!", MessageType.ERROR);
                        return;
                    }
                    InfoForCreatingConnection infoForCreatingConnection = new InfoForCreatingConnection("aggrg", classyTreeDiagram, (Interclass) selectedDiagramElementFrom, (Interclass) selectedDiagramElementTo, elementConnectionType);
                    IClassyTree iClassyTree = MainFrame.getInstance().getClassyTree();
                    AbstractCommand addConnectionCommand = new AddConnectionCommand(iClassyTree, infoForCreatingConnection, currentDiagram);
                    diagramView.getCommandManager().addCommand(addConnectionCommand);
                    connectionStateDialog.dispose();
                });
            }
            linePainter = null;
            diagramView.updateLinePainter(null);
        }
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        if (linePainter != null) {
            System.out.println("mouseDragged inside of AddConnectionState from " + startLocation + " to " + currentLocation);
            System.out.println("Optimal location: " + currentLocationOptimal);

            linePainter.setCurrentPoint(currentLocationOptimal);
            diagramView.updateLinePainter(linePainter);
        }
    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of AddConnectionState");

    }
}
