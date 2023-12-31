package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpenDiagramAction extends AbstractClassyAction {

    public OpenDiagramAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        // putValue(SMALL_ICON, loadIcon("/images/Open.png"));
        putValue(NAME, "Open Diagram");
        putValue(SHORT_DESCRIPTION, "Open Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Check if any classy tree item is selected
        if (MainFrame.getInstance().getClassyTree().getSelectedNode() == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("ClassyTreeItem must be selected.", MessageType.ERROR);
            return;
        }

        // Open JFileChooser
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                ClassyNode node = ApplicationFramework.getInstance().getJackson().loadFromJSONFile(file, Diagram.class);
                if (node != null) {
                    node.setJSONFilePath(file.getPath());
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("OpenDiagramAction has been performed.");
    }

}
