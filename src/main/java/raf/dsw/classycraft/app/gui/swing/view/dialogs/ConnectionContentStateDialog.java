package raf.dsw.classycraft.app.gui.swing.view.dialogs;

import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
public class ConnectionContentStateDialog extends JFrame {
    private Connection selectedConnection;
    private JButton buttonOK;
    private JLabel labelName;
    private JPanel contentPanel;
    private JComboBox cardinalityJComboBox;
    private JComboBox accesibilityJComboBox = new JComboBox(AccessModifiers.values());
    private JComboBox<DependencyEnum> dependencyEnumJComboBox = new JComboBox<>(DependencyEnum.values());
    private JTextField attributeNameTextField;
    private int windowWidth = 350;
    private int windowHeight = 140;

    private Map<CardinalityEnum, String> cardinalityEnumDictionary = new HashMap<CardinalityEnum, String>() {{
        put(CardinalityEnum.ZERO_OR_MORE, "0..*");
        put(CardinalityEnum.ZERO_OR_ONE, "0..1");
    }};
    public ConnectionContentStateDialog(Connection connectionElement) {
        setTitle("Edit connection settings");
        setLocationRelativeTo(null);

        this.selectedConnection = connectionElement;
        initializeComponents();

        if (connectionElement instanceof Generalization)
            GeneralizationStateDialog();
        else if (connectionElement instanceof Aggregation || connectionElement instanceof Composition) {
            AggregationAndCompositionStateDialog();
        }
        else if (connectionElement instanceof Dependency) {
            DependencyStateDialog();
        }

        this.getContentPane().add(contentPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private void initializeComponents() {
        buttonOK = new JButton("OK");
        labelName = new JLabel("Name : " + selectedConnection.getName());
        labelName.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel = new JPanel();
        cardinalityJComboBox = new JComboBox();
        int i = 0;
        for (String key : cardinalityEnumDictionary.values()) {
            cardinalityJComboBox.insertItemAt(key, i++);
        }
        attributeNameTextField = new JTextField();
        attributeNameTextField.setPreferredSize(new Dimension(attributeNameTextField.getPreferredSize().width, attributeNameTextField.getPreferredSize().height));
    }

    private void GeneralizationStateDialog() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(labelName);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(buttonOK);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void AggregationAndCompositionStateDialog() {
        IAggregationAndComposition iAggregationAndComposition = null;
        if (selectedConnection instanceof Aggregation) {
            iAggregationAndComposition = (IAggregationAndComposition) ((Aggregation) selectedConnection);
        }
        else if (selectedConnection instanceof Composition) {
            iAggregationAndComposition = (IAggregationAndComposition) ((Composition) selectedConnection);
        }

        if (iAggregationAndComposition != null) {
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.add(createlabelJPanel());

            contentPanel.add(Box.createVerticalGlue());

            JPanel cardinalityJPanel = createJComboBoxPanel("Cardinality: ", cardinalityJComboBox);
            setSelectedValue(cardinalityJComboBox, cardinalityEnumDictionary.get(iAggregationAndComposition.getCardinalityEnum()));
            contentPanel.add(cardinalityJPanel);

            contentPanel.add(Box.createVerticalGlue());

            JPanel accessibilityJPanel = createJComboBoxPanel("Accessiblity: ", accesibilityJComboBox);
            setSelectedValue(accesibilityJComboBox, iAggregationAndComposition.getAttributeAccessModifier().toString());
            contentPanel.add(accessibilityJPanel);

            JPanel attributePanel = new JPanel();
            attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.X_AXIS));
            attributePanel.setMaximumSize(new Dimension(windowWidth, 20));
            attributePanel.add(new JLabel("Attribute name: "));
            attributePanel.add(attributeNameTextField);
            attributeNameTextField.setText(iAggregationAndComposition.getAttributeName());
            contentPanel.add(attributePanel);

            contentPanel.add(buttonOK);
        }
    }

    private void DependencyStateDialog() {
        Dependency dependencyElement = (Dependency) selectedConnection;

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(createlabelJPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel dempendencyJPanel = createJComboBoxPanel("Dempendancy type: ", dependencyEnumJComboBox);
        setSelectedValue(dependencyEnumJComboBox, dependencyElement.getDependencyEnum().toString());
        dempendencyJPanel.add(buttonOK);
        contentPanel.add(dempendencyJPanel);

        contentPanel.add(buttonOK);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void insertData() {
        if (selectedConnection instanceof Aggregation || selectedConnection instanceof Composition) {
            IAggregationAndComposition iAggregationAndComposition = null;
            if (selectedConnection instanceof Aggregation)
                iAggregationAndComposition = (IAggregationAndComposition) (Aggregation) selectedConnection;
            else
                iAggregationAndComposition = (IAggregationAndComposition) (Composition) selectedConnection;

            if (attributeNameTextField.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Attribute name cannot be empty!");
            }
            String attributeName = attributeNameTextField.getText();
            CardinalityEnum cardinalityEnum = getCarcinalityEnum();
            AccessModifiers accessModifier = (AccessModifiers) accesibilityJComboBox.getSelectedItem();
            iAggregationAndComposition.setAttributeNameCardinalityAndAccessibility(attributeNameTextField.getText(), cardinalityEnum, accessModifier);
        }
        else if (selectedConnection instanceof Dependency) {
            Dependency selectedDependencyConnection = (Dependency) selectedConnection;
            selectedDependencyConnection.setDependencyEnum((DependencyEnum) dependencyEnumJComboBox.getSelectedItem());
        }
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    private static void setSelectedValue(JComboBox comboBox, String value)
    {
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            if (comboBox.getItemAt(i).toString().equals(value))
            {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private JPanel createlabelJPanel() {
        JPanel jlabelPanel = new JPanel();
        jlabelPanel.setMinimumSize(new Dimension(windowWidth, 20));
        jlabelPanel.setMaximumSize(new Dimension(windowWidth, 20));
        jlabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jlabelPanel.add(Box.createHorizontalGlue());
        jlabelPanel.add(labelName);

        return  jlabelPanel;
    }

    private JPanel createJComboBoxPanel(String label_text, JComboBox jComboBox) {
        JPanel jComboBoxPanel = new JPanel();
        jComboBoxPanel.setMinimumSize(new Dimension(windowWidth, 20));
        jComboBoxPanel.setMaximumSize(new Dimension(windowWidth, 20));
        jComboBoxPanel.setLayout(new BoxLayout(jComboBoxPanel, BoxLayout.X_AXIS));
        jComboBoxPanel.add(new JLabel(label_text));
        jComboBoxPanel.add(jComboBox);

        return jComboBoxPanel;
    }

    private CardinalityEnum getCarcinalityEnum(){
        CardinalityEnum selectedCardinalityEnum = null;
        for (CardinalityEnum cardinalityEnum : CardinalityEnum.values()) {
            if (cardinalityEnumDictionary.get(cardinalityEnum).equals(cardinalityJComboBox.getSelectedItem().toString())) {
                selectedCardinalityEnum = cardinalityEnum;
                break;
            }
        }
        return selectedCardinalityEnum;
    }
}
