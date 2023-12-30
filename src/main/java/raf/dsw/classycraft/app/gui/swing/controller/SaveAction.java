package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAction extends AbstractClassyAction {

    public SaveAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/Save.png"));
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof ProjectExplorer) {
            return;
        }

        ClassyNode node = MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
        File nodeFile = null;

        if (!node.isChanged()) {
            System.out.println("Nothing has changed since the last time you had saved the project.");
            return;
        }

        if (node.getJSONFilePath() == null || node.getJSONFilePath().isEmpty()) {
            if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                nodeFile = jfc.getSelectedFile();
                node.setJSONFilePath(nodeFile.getPath());
            } else {
                return;
            }
        }

        ApplicationFramework.getInstance().getJackson().saveToJSONFile(node);
        node.setChanged(false);

//        else if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram) {
//
//            // Create a pop-up window
//            JFrame frame = new JFrame("Diagram Template Name");
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.setSize(350, 100);
//
//            // Create a JPanel to hold components
//            JPanel panel = new JPanel();
//            frame.add(panel);
//
//            // Create a JTextField (text box)
//            JTextField textField = new JTextField(20);
//            panel.add(textField);
//
//            // Create a JButton
//            JButton saveButton = new JButton("Save");
//            panel.add(saveButton);
//
//            // Add an ActionListener to the button
//            saveButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String content = textField.getText();
//
//                    if (!content.isEmpty()) {
//
//                        System.out.println(content);
//
//                        // Close the window after successful renaming
//                        frame.dispose();
//
//                    }
//                }
//            });
//
//            // Display the JFrame
//            frame.setVisible(true);
//        }
    }

}
