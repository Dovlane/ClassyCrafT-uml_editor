package raf.dsw.classycraft.app.gui.swing.view.dialogs;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

enum ClassContentStateEnum {
    CLASS_CONTENT, INTERFACE_CONTENT, ENUM_CONTENT
}
public class ClassContentStateDialog extends JFrame {

    private JButton buttonAdd;
    private JButton buttonOk;
    private Object[] fieldsForMethodsAndAttributes;
    private JTextField jTextFieldForEnumLiteral;
    private JTable jTableMethods;
    private JTable jTableAttributes;
    private JTable jTableEnumLiterals;
    private JRadioButton[] jRadioButtonsAttributeOrMethod;
    private JButton buttonDelete;
    private ClassContentStateEnum classContentStateEnum;
    private String[] columnNamesForMethodsAndAttributes = {"Name", "Access Modifiers", "Non-access Modifiers", "Type"};
    private String[] columnNamesForEnumLiterals = {"Name"};

    private JPanel importDataJPanel;

    private JComboBox<AccessModifiers> accessModifiersJComboBox = new JComboBox<>(AccessModifiers.values());
    private JComboBox<NonAccessModifiers> nonAccessModifiersJComboBox = new JComboBox<>(NonAccessModifiers.values());
    private DiagramElement selectedDiagramElement;
    public ClassContentStateDialog(DiagramElement elementDiagram) {
        super("Add Class Content");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, (int)(screenHeight / 3));
        setLocationRelativeTo(null);

        selectedDiagramElement = elementDiagram;
        if (selectedDiagramElement instanceof ClassElement) {
            classContentStateEnum = ClassContentStateEnum.CLASS_CONTENT;
        }
        else if (selectedDiagramElement instanceof InterfaceElement) {
            classContentStateEnum = ClassContentStateEnum.INTERFACE_CONTENT;
        }
        else if (selectedDiagramElement instanceof EnumElement) {
            classContentStateEnum = ClassContentStateEnum.ENUM_CONTENT;
        }

        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT) {
            setImportDataPanelForClass();
            setMethodTable();
            setAttributeTable();
        }
        else if (classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            setImportDataPanelForInterface();
            setMethodTable();
        }
        else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            setImportDataPaneForEnum();
            setEnumLiteralTable();
        }
        fillDataOfDiagramElement();

        // Pack and set visible
        pack();
        setVisible(true);
    }

    public void insertRow() {
        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT) {
            if (jRadioButtonsAttributeOrMethod[0].isSelected())
                insertRow(jTableMethods);
            else
                insertRow(jTableAttributes);
        }
        else if (classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            insertRow(jTableMethods);
        }
        else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            insertRow(jTableEnumLiterals);
        }
    }

    public void deleteRow() {
        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT) {
            if (jRadioButtonsAttributeOrMethod[0].isSelected())
                deleteRow(jTableMethods);
            else
                deleteRow(jTableAttributes);
        }
        else if (classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            deleteRow(jTableMethods);
        }
        else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            deleteRow(jTableEnumLiterals);
        }
    }

    public void insertData() {
        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT) {
            ClassElement selectedClassElement = (ClassElement) selectedDiagramElement;
            selectedClassElement.getClassContent().clear();
            if (!tableIsEmpty(jTableMethods)) {
                for (int row = 0; row < jTableMethods.getRowCount(); row++) {
                    String name = jTableMethods.getValueAt(row, 0).toString();
                    AccessModifiers accessModifier = AccessModifiers.valueOf(jTableMethods.getValueAt(row, 1).toString());
                    NonAccessModifiers nonAccessModifier = NonAccessModifiers.valueOf(jTableMethods.getValueAt(row, 2).toString());
                    String type = jTableMethods.getValueAt(row, 3).toString();
                    selectedClassElement.addClassContent(new Method(name, accessModifier, nonAccessModifier, type));
                }
            }

            if (!tableIsEmpty(jTableAttributes)) {
                for (int row = 0; row < jTableAttributes.getRowCount(); row++) {
                    String name = jTableAttributes.getValueAt(row, 0).toString();
                    AccessModifiers accessModifier = AccessModifiers.valueOf(jTableAttributes.getValueAt(row, 1).toString());
                    NonAccessModifiers nonAccessModifier = NonAccessModifiers.valueOf(jTableAttributes.getValueAt(row, 2).toString());
                    String type = jTableAttributes.getValueAt(row, 3).toString();
                    selectedClassElement.addClassContent(new Attribute(accessModifier, nonAccessModifier, name, type));
                }
            }
        }

        else if (classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            InterfaceElement selecteInterfaceElement = (InterfaceElement) selectedDiagramElement;
            selecteInterfaceElement.getInterfaceMethods().clear();
            if (!tableIsEmpty(jTableMethods)) {
                for (int row = 0; row < jTableMethods.getRowCount(); row++) {
                    String name = jTableMethods.getValueAt(row, 0).toString();
                    AccessModifiers accessModifier = AccessModifiers.valueOf(jTableMethods.getValueAt(row, 1).toString());
                    NonAccessModifiers nonAccessModifier = NonAccessModifiers.valueOf(jTableMethods.getValueAt(row, 2).toString());
                    String type = jTableMethods.getValueAt(row, 3).toString();
                    selecteInterfaceElement.addMethod(new Method(name, accessModifier, nonAccessModifier, type));
                }
            }
        }

        else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            EnumElement selectedEnumElement = (EnumElement) selectedDiagramElement;
            selectedEnumElement.getEnumLiterals().clear();
            if (!tableIsEmpty(jTableEnumLiterals)) {
                for (int row = 0; row < jTableEnumLiterals.getRowCount(); row++) {
                    String enumLiteralName = jTableEnumLiterals.getValueAt(row, 0).toString();
                    selectedEnumElement.addEnumLiteral(new EnumLiteral(enumLiteralName));
                }
            }
        }

    }

    private void fillDataOfDiagramElement() {
        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT) {
            ClassElement classElement = (ClassElement) selectedDiagramElement;
            for (Method method : classElement.getClassMethods()) {
                manageInitialRow(jTableMethods, false); // Delete the initial row if it exists
                ((DefaultTableModel) jTableMethods.getModel()).addRow(new Object[]{method.getName(), method.getAccessModifiers(), method.getNonAccessModifiers(), method.getType()});
            }
            for (Attribute attribute : classElement.getClassAttributes()) {
                manageInitialRow(jTableAttributes, false); // Delete the initial row if it exists
                ((DefaultTableModel) jTableAttributes.getModel()).addRow(new Object[]{attribute.getName(), attribute.getAccessModifiers(), attribute.getNonAccessModifiers(), attribute.getType()});
            }
        } else if (classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            InterfaceElement interfaceElement = (InterfaceElement) selectedDiagramElement;
            for (Method method : interfaceElement.getInterfaceMethods()) {
                manageInitialRow(jTableMethods, false); // Delete the initial row if it exists
                ((DefaultTableModel) jTableMethods.getModel()).addRow(new Object[]{method.getName(), method.getAccessModifiers(), method.getNonAccessModifiers(), method.getType()});
            }
        } else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            EnumElement enumElement = (EnumElement) selectedDiagramElement;
            for (EnumLiteral enumLiteral : enumElement.getEnumLiterals()) {
                manageInitialRow(jTableEnumLiterals, false); // Delete the initial row if it exists
                ((DefaultTableModel) jTableEnumLiterals.getModel()).addRow(new Object[]{ enumLiteral.toString()});
            }
        }
    }

    private void setImportDataPanelForClass() {
        importDataJPanel = new JPanel();
        importDataJPanel.setLayout(new BoxLayout(importDataJPanel, BoxLayout.Y_AXIS));

        setImportFieldsForMethodsAndAttributes();

        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new BoxLayout(buttonsJPanel, BoxLayout.X_AXIS));

        jRadioButtonsAttributeOrMethod = new JRadioButton[] {new JRadioButton("Edit methods"), new JRadioButton("Edit attributes")};
        ButtonGroup methodAndAttributeRBtnGroup = new ButtonGroup();
        JPanel jradioButtonPanel = new JPanel();
        jradioButtonPanel.setLayout(new BoxLayout(jradioButtonPanel, BoxLayout.Y_AXIS));
        jRadioButtonsAttributeOrMethod[0].setSelected(true);
        buttonsJPanel.add(new Label("Set class content:"));

        for (JRadioButton jRadioButton : jRadioButtonsAttributeOrMethod) {
            methodAndAttributeRBtnGroup.add(jRadioButton);
            jradioButtonPanel.add(jRadioButton);
        }

        buttonAdd = new JButton("ADD");
        buttonDelete = new JButton("DELETE");
        buttonOk = new JButton("OK");
        buttonsJPanel.add(jradioButtonPanel);
        buttonsJPanel.add(buttonAdd);
        buttonsJPanel.add(buttonDelete);
        buttonsJPanel.add(buttonOk);

        importDataJPanel.add(buttonsJPanel);

        getContentPane().add(importDataJPanel, BorderLayout.NORTH);
    }

    private void setImportDataPanelForInterface() {
        importDataJPanel = new JPanel();
        importDataJPanel.setLayout(new BoxLayout(importDataJPanel, BoxLayout.Y_AXIS));

        setImportFieldsForMethodsAndAttributes();


        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new BoxLayout(buttonsJPanel, BoxLayout.X_AXIS));

        buttonAdd = new JButton("ADD");
        buttonDelete = new JButton("DELETE");
        buttonOk = new JButton("OK");
        buttonsJPanel.add(buttonAdd);
        buttonsJPanel.add(buttonDelete);
        buttonsJPanel.add(buttonOk);

        importDataJPanel.add(buttonsJPanel);

        getContentPane().add(importDataJPanel, BorderLayout.NORTH);
    }

    private void setImportFieldsForMethodsAndAttributes() {
        String[] dataRowStrings = new String[] {"Name: ", "Access modifiers: ", "Non-access modifiers: ", "Type: ", };
        fieldsForMethodsAndAttributes = new Object[]{new JTextField(), accessModifiersJComboBox, nonAccessModifiersJComboBox, new JTextField()};

        JPanel dataJPanel = new JPanel();
        dataJPanel.setLayout(new BoxLayout(dataJPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < dataRowStrings.length; i++) {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
            jPanel.add(new Label(dataRowStrings[i]));
            if (fieldsForMethodsAndAttributes[i] instanceof JTextField) {
                JTextField jTextField = (JTextField)fieldsForMethodsAndAttributes[i];
                jTextField.setPreferredSize(new Dimension(jTextField.getPreferredSize().getSize().width * 2, jTextField.getPreferredSize().height));
            }
            jPanel.add((Component) fieldsForMethodsAndAttributes[i]);
            dataJPanel.add(jPanel);
        }
        importDataJPanel.add(dataJPanel);
    }


    private void setImportDataPaneForEnum() {
        importDataJPanel = new JPanel();
        String[] dataRowStrings = new String[] {"Name: ", "Access modifiers: ", "Non-access modifiers: ", "Type: ", };

        importDataJPanel = new JPanel();
        importDataJPanel.setLayout(new BoxLayout(importDataJPanel, BoxLayout.Y_AXIS));

        JPanel dataJPanel = new JPanel();
        dataJPanel.setLayout(new BoxLayout(dataJPanel, BoxLayout.X_AXIS));
        dataJPanel.add(new Label("Enum literal: "));
        jTextFieldForEnumLiteral = new JTextField();
        jTextFieldForEnumLiteral.setPreferredSize(new Dimension(jTextFieldForEnumLiteral.getPreferredSize().getSize().width * 2, jTextFieldForEnumLiteral.getPreferredSize().height));
        dataJPanel.add(jTextFieldForEnumLiteral);

        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new BoxLayout(buttonsJPanel, BoxLayout.X_AXIS));

        buttonAdd = new JButton("ADD");
        buttonDelete = new JButton("DELETE");
        buttonOk = new JButton("OK");
        buttonsJPanel.add(buttonAdd);
        buttonsJPanel.add(buttonDelete);
        buttonsJPanel.add(buttonOk);

        importDataJPanel.add(dataJPanel);
        importDataJPanel.add(buttonsJPanel);

        getContentPane().add(importDataJPanel, BorderLayout.NORTH);
    }

    private void setMethodTable() {
        DefaultTableModel tableModelMethods = new DefaultTableModel(columnNamesForMethodsAndAttributes, 1);

        jTableMethods = new JTable(tableModelMethods);
        setPreferredScrollableViewportSize(jTableMethods);

        JPanel methodPanel = new JPanel();
        methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.Y_AXIS));
        methodPanel.add(new Label("Methods:"));
        JScrollPane methodTableScrollPane = new JScrollPane(jTableMethods);
        methodTableScrollPane.setBorder(new EmptyBorder(5, 0, 0, 0));
        methodPanel.add(methodTableScrollPane);
        methodPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        getContentPane().add(methodPanel, BorderLayout.CENTER);
    }

    private void setAttributeTable() {
        DefaultTableModel tableModelAttributes = new DefaultTableModel(columnNamesForMethodsAndAttributes, 1);
        jTableAttributes = new JTable(tableModelAttributes);
        setPreferredScrollableViewportSize(jTableAttributes);
        //jTableAttributes.setPreferredScrollableViewportSize(jTableAttributes.getPreferredSize());

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));
        attributePanel.add(new Label("Attributes:"));
        JScrollPane attributeTableScrollPane = new JScrollPane(jTableAttributes);
        attributeTableScrollPane.setBorder(new EmptyBorder(5, 0, 5, 0));
        attributePanel.add(attributeTableScrollPane);
        attributePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        getContentPane().add(attributePanel, BorderLayout.SOUTH);
    }

    private void setEnumLiteralTable() {
        DefaultTableModel tableModelEnumLiterals = new DefaultTableModel(columnNamesForEnumLiterals, 1);
        jTableEnumLiterals = new JTable(tableModelEnumLiterals);
        setPreferredScrollableViewportSize(jTableEnumLiterals);
        //jTableAttributes.setPreferredScrollableViewportSize(jTableAttributes.getPreferredSize());

        JPanel enumLiteralPanel = new JPanel();
        enumLiteralPanel.setLayout(new BoxLayout(enumLiteralPanel, BoxLayout.Y_AXIS));
        enumLiteralPanel.add(new Label("Enum literals:"));
        JScrollPane attributeTableScrollPane = new JScrollPane(jTableEnumLiterals);
        attributeTableScrollPane.setBorder(new EmptyBorder(5, 0, 5, 0));
        enumLiteralPanel.add(attributeTableScrollPane);
        enumLiteralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        getContentPane().add(enumLiteralPanel, BorderLayout.SOUTH);
    }


    private void setPreferredScrollableViewportSize(JTable table) {
        int preferredRowCount = 5;
        table.setPreferredScrollableViewportSize(new Dimension(
                table.getPreferredScrollableViewportSize().width,
                table.getRowHeight() * preferredRowCount
        ));
    }
    // Method to insert a new row into the table
    private void insertRow(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Vector<String> data = new Vector<>();
        if (classContentStateEnum == ClassContentStateEnum.CLASS_CONTENT || classContentStateEnum == ClassContentStateEnum.INTERFACE_CONTENT) {
            data.add(((JTextField)fieldsForMethodsAndAttributes[0]).getText());
            data.add(accessModifiersJComboBox.getItemAt(accessModifiersJComboBox.getSelectedIndex()).toString());
            data.add(nonAccessModifiersJComboBox.getItemAt(nonAccessModifiersJComboBox.getSelectedIndex()).toString());
            data.add(((JTextField)fieldsForMethodsAndAttributes[3]).getText());

        }
        else if (classContentStateEnum == ClassContentStateEnum.ENUM_CONTENT) {
            data.add(jTextFieldForEnumLiteral.getText());
        }
        if (!rowAlreadyExists(table, data)) {
            System.out.println("!rowAlreadyExists(table, data): " + !rowAlreadyExists(table, data));
            manageInitialRow(table, false); // Delete the initial row if it exists
            model.addRow(data);
        }
    }

    // Method to delete a row from the table
    private void deleteRow(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        if (rowCount > 0) {
            int[] selectedRows = table.getSelectedRows();
            for (int i = selectedRows.length - 1; i >= 0; i--)
                model.removeRow(selectedRows[i]); // Remove the last row
            manageInitialRow(table,true); // Add an initial empty row if the table is empty
        }
    }

    // Method to manage the initial row based on the table's row count
    private void manageInitialRow(JTable table, boolean addEmptyRow) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0 && addEmptyRow) {
            model.addRow(new Object[]{"", "", ""}); // Add an initial empty row
        } else if (rowCount > 0 && rowIsEmpty(table, 0)) {
            model.removeRow(0); // Remove the initial row
        }
    }

    private boolean tableIsEmpty(JTable table) {
        return table.getRowCount() == 1 && rowIsEmpty(table, 0);
    }

    // Method to check if a specific row is empty
    private boolean rowIsEmpty(JTable table, int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int columnCount = model.getColumnCount();
        for (int col = 0; col < columnCount; col++) {
            Object cellValue = model.getValueAt(rowIndex, col);
            if (cellValue != null && !cellValue.toString().trim().isEmpty()) {
                return false; // Row is not empty
            }
        }
        return true; // Row is empty
    }

//    private void deleteFirstBlankRowIfTableIsNotEmpty(JTable table) {
//        if (!tableIsEmpty(table)) {
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//
//            model.removeRow(0);
//        }
//    }

    private boolean rowAlreadyExists(JTable table, Vector<String> rowData) {
        if (!tableIsEmpty(table)) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            System.out.println("rowData " + rowData);
            for (int row = 0; row < model.getRowCount(); row++) {
                boolean rowWithSameContent = true;
                for (int col = 0; col < model.getColumnCount(); col++) {
                    String valueOfField = model.getValueAt(row, col).toString();
                    if (!rowData.get(col).equals(valueOfField)) {
                        rowWithSameContent = false;
                        break;
                    }
                }
                if (rowWithSameContent)
                    return true;
//                if (Arrays.deepEquals(existingRowData, rowData)) {
//                    return true; // Row with the same content already exists
//                }
            }
        }
        return false; // Row with the same content does not exist
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public JButton getButtonOk() {
        return buttonOk;
    }

}

