package raf.dsw.classycraft.app.gui.swing.view.dialogs;

import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.ElementConnectionType;

import javax.swing.*;
import java.awt.*;

public class ConnectionStateDialog extends JFrame {
    private JRadioButton[] connectionsRadioButtonOptions = new JRadioButton[]
            {new JRadioButton(ElementConnectionType.GENERALISATION.toString()),
                    new JRadioButton(ElementConnectionType.COMPOSITION.toString()),
                    new JRadioButton(ElementConnectionType.DEPENDENDCY.toString()),
                    new JRadioButton(ElementConnectionType.AGGREGATION.toString()) };
    private JButton buttonOk = new JButton("OK");
    public ConnectionStateDialog() {
        this.setName("Add connection");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 6, (int)(screenHeight / 6));
        setLocationRelativeTo(null);

        JPanel connectionJPanel = new JPanel();
        connectionJPanel.setLayout(new BoxLayout(connectionJPanel, BoxLayout.Y_AXIS));
        connectionJPanel.add(new JLabel("Choose connection type:"));
        ButtonGroup connectionsRadioButtonGroup = new ButtonGroup();
        for (int i = 0; i < connectionsRadioButtonOptions.length; i++) {
            connectionJPanel.add(connectionsRadioButtonOptions[i]);
            connectionsRadioButtonGroup.add(connectionsRadioButtonOptions[i]);
        }
        connectionsRadioButtonOptions[0].setSelected(true);
        connectionJPanel.add(buttonOk);

        add(connectionJPanel);
        setVisible(true);
    }

    public JButton getButtonOk() {
        return buttonOk;
    }

    public ElementConnectionType getElementConnectionType() {
        ElementConnectionType elementConnectionType = null;
        for (JRadioButton jRadioButton : connectionsRadioButtonOptions) {
            if (jRadioButton.isSelected()) {
                elementConnectionType = ElementConnectionType.valueOf(jRadioButton.getText());
            }
        }
        return elementConnectionType;
    }
}
