package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractClassyAction{

    public UndoAction() {
        putValue(SMALL_ICON, loadIcon("/images/undo.png"));
        putValue(NAME, "Undo action");
        putValue(SHORT_DESCRIPTION, "Undo last action on showed Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Undo action is pressed");
    }
}
