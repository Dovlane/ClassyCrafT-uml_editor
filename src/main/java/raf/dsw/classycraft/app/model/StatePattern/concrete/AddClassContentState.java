package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ClassContentStateDialog;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClassContentState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
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

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {

    }

}
