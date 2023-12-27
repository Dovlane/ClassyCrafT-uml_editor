package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OpenAction extends AbstractClassyAction {

    public OpenAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/Open.png"));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open Folder");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("OpenAction has been performed.");
    }

}
