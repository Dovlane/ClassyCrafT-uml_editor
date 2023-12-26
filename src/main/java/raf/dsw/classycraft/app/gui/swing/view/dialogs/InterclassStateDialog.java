package raf.dsw.classycraft.app.gui.swing.view.dialogs;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.NewClassAction;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class InterclassStateDialog extends JFrame {

    private JTextField textField;
    private ArrayList<JRadioButton> interclassRadioButtons = new ArrayList<>();
    private ArrayList<JRadioButton> accessModifiersRadioButtons = new ArrayList<>();
    private ArrayList<JRadioButton> nonAccessModifiersRadioButtons = new ArrayList<>();
    private JButton buttonOk;
    public InterclassStateDialog() throws HeadlessException {
        this.setName("Add class");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel topJPanel = new JPanel();
        topJPanel.setLayout( new BoxLayout(topJPanel, BoxLayout.X_AXIS));
        Label label = new Label("Class name:");
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().height, textField.getPreferredSize().width * 3));
        buttonOk = new JButton("OK");
        topJPanel.add(label);
        topJPanel.add(textField);
        topJPanel.add(buttonOk);

        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout( new BoxLayout(centerJPanel, BoxLayout.Y_AXIS) );
        String[] interclassOptions = new String[]{"Class", "Interface", "Enum"};
        ButtonGroup interclassButtonGroup = new ButtonGroup();
        for (String interclassOption : interclassOptions) {
            JRadioButton jRadioButton = new JRadioButton(interclassOption);
            interclassButtonGroup.add(jRadioButton);
            interclassRadioButtons.add(jRadioButton);
            centerJPanel.add(jRadioButton);
        }
        interclassRadioButtons.get(0).setSelected(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout( new GridLayout(0, 2));
        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.Y_AXIS));
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS));
        ButtonGroup accessModifiersButtonGroup = new ButtonGroup();
        bottomLeftPanel.add(new Label("Choose access modifier:"));
        for (AccessModifiers accessModifiers : AccessModifiers.values()) {
            JRadioButton jRadioButton = new JRadioButton(accessModifiers.toString());
            accessModifiersButtonGroup.add(jRadioButton);
            accessModifiersRadioButtons.add(jRadioButton);
            bottomLeftPanel.add(jRadioButton);
        }
        accessModifiersRadioButtons.get(0).setSelected(true);
        bottomRightPanel.add(new Label("Choose non-access modifier"));
        ButtonGroup nonAccessModifiersButtonGroup = new ButtonGroup();
        for (NonAccessModifiers nonAccessModifiers : NonAccessModifiers.values()) {
            JRadioButton jRadioButton = new JRadioButton(nonAccessModifiers.toString());
            nonAccessModifiersButtonGroup.add(jRadioButton);
            nonAccessModifiersRadioButtons.add(jRadioButton);
            bottomRightPanel.add(jRadioButton);
        }
        nonAccessModifiersRadioButtons.get(0).setSelected(true);
        bottomPanel.add(bottomLeftPanel);
        bottomPanel.add(bottomRightPanel);

        this.getContentPane().add(topJPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerJPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        buttonOk.addActionListener(new NewClassAction(this));
        setVisible(false);
    }
    public JTextField getTextField() {
        return textField;
    }

    public ArrayList<JRadioButton> getInterclassRadioButtons() {
        return interclassRadioButtons;
    }

    public ArrayList<JRadioButton> getAccessModifiersRadioButtons() {
        return accessModifiersRadioButtons;
    }

    public ArrayList<JRadioButton> getNonAccessModifiersRadioButtons() {
        return nonAccessModifiersRadioButtons;
    }

    public JButton getButtonOk() {
        return buttonOk;
    }
}
