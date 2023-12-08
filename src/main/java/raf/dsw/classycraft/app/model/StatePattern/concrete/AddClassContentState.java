package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.ClassContentStateDialog;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClassContentState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside of AddClassContentState");

        DiagramElement diagramElementAt = diagramView.getElementAt(location);
        if (diagramElementAt != null) {
            ClassContentStateDialog classContentStateDialog = new ClassContentStateDialog(diagramElementAt);

            classContentStateDialog.getButtonAdd().addActionListener(e -> classContentStateDialog.insertRow());
            classContentStateDialog.getButtonDelete().addActionListener(e -> classContentStateDialog.deleteRow());
            classContentStateDialog.getButtonOk().addActionListener(
                    e -> {
                classContentStateDialog.insertData();
                classContentStateDialog.dispose();
            });
        }
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside of AddClassContentState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside of AddClassContentState");

    }

    @Override
    public void mouseWheelMoved(DiagramView diagramView, int wheelRotation) {
        System.out.println("mouseWheelMoved inside of AddClassContentState");

    }

}
