package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class LoadDiagramTemplateAction extends AbstractClassyAction {

    public LoadDiagramTemplateAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        // putValue(SMALL_ICON, loadIcon("/images/Open.png"));
        putValue(NAME, "Load Diagram Template");
        putValue(SHORT_DESCRIPTION, "Load Diagram Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Check if any classy tree item is selected
        ClassyTreeItem selectedNode = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if (selectedNode == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("ClassyTreeItem must be selected.", MessageType.ERROR);
            return;
        }

        // Check if selected classyNode is Diagram
        if (!(selectedNode.getClassyNode() instanceof Diagram)) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("Selected classyNode must be Diagram.", MessageType.ERROR);
            return;
        }

        Diagram oldNode = (Diagram) selectedNode.getClassyNode();
        Package oldNodeParent = (Package) oldNode.getParent();
        ClassyTreeItem oldNodeParentItem = (ClassyTreeItem) selectedNode.getParent();
        if (oldNode.getChildren().size() > 0) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("Selected diagram must be empty.", MessageType.ERROR);
            return;
        }

        // Open JFileChooser and set default directory
        JFileChooser jfc = new JFileChooser();
        URL diagramTemplatesURL = getClass().getResource("/DiagramTemplates");
        try {
            File diagramTemplateDirectory = new File(Objects.requireNonNull(diagramTemplatesURL).toURI());
            jfc.setCurrentDirectory(diagramTemplateDirectory);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                // Get the file path from JFileChooser
                File file = jfc.getSelectedFile();

                // Remove the oldNode so there is place for new one
                ClassyNode.setCurrentSelectedNode(oldNodeParent);
                MainFrame.getInstance().getClassyTree().removeItem(selectedNode);

                // Load new Diagram from a template
                ClassyNode newNode = ApplicationFramework.getInstance().getJackson().loadFromJSONFile(file, Diagram.class);

                // Loaded successfully
                if (newNode != null) {
                    newNode.setName(oldNode.getName());
                    newNode.setJSONFilePath(file.getPath());
                    ClassyNode.setCurrentSelectedNode(newNode);
                }
                // Failed to load
                else {
                    MainFrame.getInstance().getClassyTree().attachChild(oldNodeParentItem, oldNode);
                    ClassyNode.setCurrentSelectedNode(oldNode);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("LoadDiagramTemplateAction has been performed.");
    }

}
