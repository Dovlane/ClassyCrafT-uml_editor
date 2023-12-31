package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class SaveDiagramTemplateAction extends AbstractClassyAction {

    public SaveDiagramTemplateAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/SaveTemplateDiagram.png"));
        putValue(NAME, "Save Diagram Template");
        putValue(SHORT_DESCRIPTION, "Save Diagram Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Check if selected classyNode is Diagram
        if (!(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram)) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("Selected classyNode must be Diagram.", MessageType.ERROR);
            return;
        }

        Diagram node = (Diagram) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
        if (node.getChildren().size() == 0) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("Diagram cannot be empty.", MessageType.ERROR);
            return;
        }

        // Create a pop-up window
        JFrame frame = new JFrame("Diagram Template Name");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 100);

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

                if (!content.isEmpty()) {

                    try {

                        // Create a new template
                        URL diagramTemplatesURL = getClass().getResource("/DiagramTemplates");
                        Path basePath = Paths.get(Objects.requireNonNull(diagramTemplatesURL).toURI());
                        Path newFilePath = basePath.resolve(content);
                        if (!Files.exists(newFilePath)) {
                            Files.createFile(newFilePath);
                        }

                        // Save the Diagram into new template
                        node.setJSONFilePath(newFilePath.toString());
                        String originalName = node.getName();
                        node.setName("__name__");
                        ApplicationFramework.getInstance().getJackson().saveToJSONFile(node);
                        node.setName(originalName);
                        node.setChanged(false);

                        System.out.println("Diagram template has been saved: " + content);

                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Close the window after successful renaming
                    frame.dispose();

                }
            }
        });

        // Display the JFrame
        frame.setVisible(true);
    }

}
