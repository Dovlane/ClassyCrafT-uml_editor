package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.dialogs.InterclassStateDialog;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.ElementInterclassType;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingInterclass;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import javax.swing.*;
import java.awt.*;

public class AddInterclassCommand extends AbstractCommand {

    private DiagramView diagramView;
    private Point location;
    public AddInterclassCommand(Point location, DiagramView diagramView) {
        this.diagramView = diagramView;
        this.location = location;
    }

    @Override
    public void doCommand() {
        System.out.println("mousePressed inside of AddInterclassState");

        DiagramElement diagramElementAt = diagramView.getElementAt(location);
        if (diagramElementAt != null) {
            return;
        }
        InterclassStateDialog interclassStateDialog = new InterclassStateDialog();
        interclassStateDialog.setVisible(true);
        final InfoForCreatingInterclass[] infoForCreatingInterclass = {null};
        Diagram currentDiagram = diagramView.getDiagram();

        interclassStateDialog.getButtonOk().addActionListener(
                e -> {
                    if (interclassStateDialog.getTextField().getText().equals("")) {
                        MainFrame.getInstance().getMessageGenerator().generateMessage("Interclass name cannot be empty!", MessageType.ERROR);
                        return;
                    }
                    String interclassName = interclassStateDialog.getTextField().getText();
                    AccessModifiers accessModifier = AccessModifiers.DEFAULT;
                    for (JRadioButton jRadioButton : interclassStateDialog.getAccessModifiersRadioButtons()) {
                        if (jRadioButton.isSelected()) {
                            accessModifier = AccessModifiers.valueOf(jRadioButton.getText());
                        }
                    }
                    NonAccessModifiers nonAccessModifier = NonAccessModifiers.ABSTRACT;
                    for (JRadioButton jRadioButton : interclassStateDialog.getNonAccessModifiersRadioButtons()) {
                        if (jRadioButton.isSelected()) {
                            nonAccessModifier = NonAccessModifiers.valueOf(jRadioButton.getText());
                        }
                    }
                    for (JRadioButton jRadioButton : interclassStateDialog.getInterclassRadioButtons()) {
                        if (jRadioButton.isSelected()) {
                            ClassyTreeItem classyTreeDiagram =
                                    MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
                            if (classyTreeDiagram == null) {
                                MainFrame.getInstance().getMessageGenerator().generateMessage(
                                        "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
                                return;
                            }
                            ElementInterclassType elementInterclassType = null;
                            if (jRadioButton.getText().equals("Class")) {
                                elementInterclassType = ElementInterclassType.CLASS;
                            } else if (jRadioButton.getText().equals("Interface")) {
                                elementInterclassType = ElementInterclassType.INTERFACE;
                            } else if (jRadioButton.getText().equals("Enum")) {
                                elementInterclassType = ElementInterclassType.ENUM;
                            }
                            infoForCreatingInterclass[0] = new InfoForCreatingInterclass(interclassName, classyTreeDiagram, location, accessModifier, nonAccessModifier, elementInterclassType);
                            break;
                        }
                    }


                    // Attach a new Interclass object in the whole Model
                    MainFrame.getInstance().getClassyTree().addChild(infoForCreatingInterclass[0]);
                    interclassStateDialog.dispose();
                });

    }

    @Override
    public void undoCommand() {

    }
}
