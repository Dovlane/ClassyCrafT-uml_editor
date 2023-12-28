package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RedoAction extends AbstractClassyAction{

    public RedoAction() {
        putValue(SMALL_ICON, loadIcon("/images/redo.png"));
        putValue(NAME, "Redo action");
        putValue(SHORT_DESCRIPTION, "Redo last action on showed Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Redo action is pressed");
    }
}
