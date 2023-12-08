package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.InterclassStateDialog;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInterclassState implements State {

    private InterclassStateDialog interclassStateDialog;
    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        DiagramElement diagramElementAt = diagramView.getElementAt(location);
        if (diagramElementAt != null) {
            return;
        }
        interclassStateDialog = new InterclassStateDialog();
        interclassStateDialog.setVisible(true);
        final Interclass[] interclass = {null};
        Diagram currentDiagram = diagramView.getDiagram();

        interclassStateDialog.getButtonOk().addActionListener(
                e -> {
            if (interclassStateDialog.getTextField().getText().equals("")){
                JOptionPane.showMessageDialog(interclassStateDialog,
                        "Interclass name cannot be empty!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String interclassName = interclassStateDialog.getTextField().getText();
            if (interclassName.equals("")){

            }
            AccessModifiers accessModifier = AccessModifiers.DEFAULT;
            for (JRadioButton jRadioButton : interclassStateDialog.getAccessModifiersRadioButtons()){
                if (jRadioButton.isSelected()) {
                    accessModifier = AccessModifiers.valueOf(jRadioButton.getText());
                }
            }
            NonAccessModifiers nonAccessModifier = NonAccessModifiers.ABSTRACT;
            for (JRadioButton jRadioButton : interclassStateDialog.getNonAccessModifiersRadioButtons()){
                if (jRadioButton.isSelected()) {
                    nonAccessModifier = NonAccessModifiers.valueOf(jRadioButton.getText());
                }
            }
            for (JRadioButton jRadioButton : interclassStateDialog.getInterclassRadioButtons()){
                if (jRadioButton.isSelected()) {
                    if (jRadioButton.getText().equals("Class")) {
                        interclass[0] = new ClassElement(interclassName, currentDiagram, location, accessModifier, nonAccessModifier);
                    }
                    else if (jRadioButton.getText().equals("Interface")) {
                        interclass[0] = new InterfaceElement(interclassName, currentDiagram, location, accessModifier, nonAccessModifier);
                        System.out.println("interface");
                    }
                    else if (jRadioButton.getText().equals("Enum")) {
                        interclass[0] = new EnumElement(interclassName, currentDiagram, location, accessModifier, nonAccessModifier);
                    }
                    break;
                }
            }

            // Attach a new Interclass object in the whole Model
            ClassyTreeItem classyTreeDiagram =
                    MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
            if (classyTreeDiagram ==  null) {
                MainFrame.getInstance().getMessageGenerator().generateMessage(
                        "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
                return;
            }
            MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, interclass[0]);

            interclassStateDialog.dispose();
        });

        System.out.println("Creating Interclass!");
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {

    }

    public InterclassStateDialog getInterclassStateDialog() {
        return interclassStateDialog;
    }
}