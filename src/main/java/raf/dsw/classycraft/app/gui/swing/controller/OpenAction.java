package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class OpenAction extends AbstractClassyAction {

    public OpenAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/Open.png"));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                Project p = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                MainFrame.getInstance().getClassyTree().loadProject(p);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("OpenAction has been performed.");
    }

}
