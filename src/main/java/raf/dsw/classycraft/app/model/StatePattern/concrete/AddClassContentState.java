package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ClassContentStateDialog;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ConnectionContentStateDialog;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.AddDiagramElementContentCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.DiagramElementContent;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.DiagramElementContentFactory;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import javax.swing.*;
import java.awt.*;

public class AddClassContentState implements State {

    DiagramElementContent startContent = null;
    DiagramElementContent finalContent = null;

    private DiagramElementContentFactory diagramElementContentFactory = new DiagramElementContentFactory();
    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of AddClassContentState");

        DiagramElement diagramElementAt = diagramView.getElementAt(location);
        if (diagramElementAt != null) {

            testToString(diagramElementAt);

            startContent = diagramElementContentFactory.createDiagramElementContent(diagramElementAt);
            if (diagramElementAt instanceof Interclass) {
                ClassContentStateDialog classContentStateDialog = new ClassContentStateDialog(diagramElementAt);
                classContentStateDialog.getButtonAdd().addActionListener(
                        e -> {
                            try {
                                classContentStateDialog.insertRow();
                            } catch (Exception exception) {
                                MainFrame.getInstance().getMessageGenerator().generateMessage(exception.getMessage(), MessageType.ERROR);
                            }
                        });
                classContentStateDialog.getButtonDelete().addActionListener(e -> classContentStateDialog.deleteRow());
                classContentStateDialog.getButtonOk().addActionListener(
                        e -> {
                            try {
                                classContentStateDialog.insertData();
                                finalContent = diagramElementContentFactory.createDiagramElementContent(diagramElementAt);
                                if (!startContent.equals(finalContent)) {
                                    AbstractCommand addDiagramElementContentCommand = new  AddDiagramElementContentCommand(diagramElementAt, startContent, finalContent);
                                    diagramView.getCommandManager().addCommand(addDiagramElementContentCommand);
                                }
                                startContent = null;
                                finalContent = null;
                            } catch (Exception exception) {
                                MainFrame.getInstance().getMessageGenerator().generateMessage(exception.getMessage(), MessageType.ERROR);
                                return;
                            }
                            classContentStateDialog.dispose();
                        });
            }
            else if (diagramElementAt instanceof Connection) {
                ConnectionContentStateDialog connectionContentStateDialog = new ConnectionContentStateDialog((Connection) diagramElementAt);
                connectionContentStateDialog.getButtonOK().addActionListener(
                        e -> {
                            try {
                                connectionContentStateDialog.insertData();
                                finalContent = diagramElementContentFactory.createDiagramElementContent(diagramElementAt);
                                if (!startContent.equals(finalContent)) {
                                    AbstractCommand addDiagramElementContentCommand = new AddDiagramElementContentCommand(diagramElementAt, startContent, finalContent);
                                    diagramView.getCommandManager().addCommand(addDiagramElementContentCommand);
                                }
                                startContent = null;
                                finalContent = null;
                            }
                            catch (Exception exception) {
                                MainFrame.getInstance().getMessageGenerator().generateMessage(exception.getMessage(), MessageType.ERROR);
                                return;
                            }
                            connectionContentStateDialog.dispose();
                        }
                );
            }
        }
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of AddClassContentState");
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocationOptimal, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of AddClassContentState from " + startLocation + " to " + currentLocation);
        System.out.println("Optimal location: " + currentLocationOptimal);
    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of AddClassContentState");

    }

    private void testToString(DiagramElement diagramElement) {
        System.out.println(diagramElement.toString());
    }
}
