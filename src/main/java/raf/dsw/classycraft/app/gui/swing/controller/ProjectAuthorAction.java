package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectAuthorAction extends AbstractClassyAction {

    public ProjectAuthorAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/SetProjectAuthor.png"));
        putValue(NAME, "Set Project Author");
        putValue(SHORT_DESCRIPTION, "Set the Project Author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selectedItem = MainFrame.getInstance().getClassyTree().getSelectedNode();
        ClassyNode node = selectedItem.getClassyNode();

        if (!(node instanceof Project)) {
            String errorMessage = "Only Project can have an author.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
            return;
        }

        JFrame frame = new JFrame("Set Project Author");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);

        // Create a JPanel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Create a JTextField (text box)
        JTextField textField = new JTextField(20);
        panel.add(textField);

        // Create a JButton
        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        // Add an ActionListener to the button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText();

                if (((Project) node).setAuthor(content)) {

                    // Close the window after successful changing the author name
                    frame.dispose();

                } else {
                    String errorMessage = "Project Author cannot be an empty string.";
                    MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
                }
            }
        });

        // Display the JFrame
        frame.setVisible(true);

        System.out.println("ProjectAuthorAction has been performed.");
    }

}
