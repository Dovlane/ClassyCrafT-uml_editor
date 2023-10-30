package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractClassyAction {

    public AboutUsAction(){

        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        // putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        putValue(NAME, "About Us");
        putValue(SHORT_DESCRIPTION, "It opens About Us window.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("AboutUsAction is performed.");

        JFrame frame = new JFrame("About Us");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a main panel with a vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        // Create the first panel for the top section
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Create the first image (you can replace "image1.jpg" with your image file)
        ImageIcon image1 = new ImageIcon(getClass().getResource("/images/Vladimir Ignjatijevic.png"));
        JLabel label1 = new JLabel(image1);
        panel1.add(label1, BorderLayout.CENTER);

        // Create the first text box
        JTextField textField1 = new JTextField("Vladimir Ignjatijević RN 7/22");
        textField1.setEditable(false);
        textField1.setHorizontalAlignment(JTextField.CENTER);
        panel1.add(textField1, BorderLayout.SOUTH);

        // Create the second panel for the bottom section
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        // Create the second image (you can replace "image2.jpg" with your image file)
        ImageIcon image2 = new ImageIcon(getClass().getResource("/images/Filip Bajraktari.png"));
        JLabel label2 = new JLabel(image2);
        panel2.add(label2, BorderLayout.CENTER);

        // Create the second text box
        JTextField textField2 = new JTextField("Filip Bajraktari RN 94/23");
        textField2.setEditable(false);
        textField2.setHorizontalAlignment(JTextField.CENTER);
        panel2.add(textField2, BorderLayout.SOUTH);

        // Add the top and bottom panels to the main panel
        mainPanel.add(panel1);
        mainPanel.add(panel2);

        // Add the main panel to the frame
        frame.add(mainPanel);

        frame.setSize(800, 600); // Set the size of the frame
        frame.setVisible(true);
    }

}
