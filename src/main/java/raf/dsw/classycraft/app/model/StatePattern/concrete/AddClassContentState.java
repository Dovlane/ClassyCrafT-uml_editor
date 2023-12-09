package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ClassContentStateDialog;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import javax.swing.*;
import java.awt.*;

public class AddClassContentState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of AddClassContentState");

        DiagramElement diagramElementAt = diagramView.getElementAt(location);
        if (diagramElementAt != null) {
            ClassContentStateDialog classContentStateDialog = new ClassContentStateDialog(diagramElementAt);

            classContentStateDialog.getButtonAdd().addActionListener(
                    e -> {
                        try {
                            classContentStateDialog.insertRow();
                        }
                        catch (Exception exception) {
                            JOptionPane.showMessageDialog(classContentStateDialog,
                                    exception.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
            classContentStateDialog.getButtonDelete().addActionListener(e -> classContentStateDialog.deleteRow());
            classContentStateDialog.getButtonOk().addActionListener(
                    e ->  {classContentStateDialog.insertData();
                    classContentStateDialog.dispose();
                    });

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

}
