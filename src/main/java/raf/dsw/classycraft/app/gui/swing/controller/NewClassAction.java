package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.InterclassStateDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewClassAction extends AbstractAction {
    InterclassStateDialog interclassStateDialog = null;
    public NewClassAction(InterclassStateDialog interclassStateDialog) {
        this.interclassStateDialog = interclassStateDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("new class action is performed");
        String name = interclassStateDialog.getTextField().getText();
        //Since we have radio buttons, everything else is already checked
        if (!name.isEmpty()) {
            interclassStateDialog.setVisible(false);
        }
    }
}
