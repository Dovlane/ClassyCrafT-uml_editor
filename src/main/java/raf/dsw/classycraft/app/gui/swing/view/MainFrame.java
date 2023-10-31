package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MainFrame extends JFrame implements IListener {

    private static MainFrame instance;
    private MessageGenerator msgGenerator;
    private ActionManager actionManager;

    private MainFrame(){
        msgGenerator = new MessageGenerator();
        msgGenerator.addListener(this);
        actionManager = new ActionManager(msgGenerator);
    }

    private void initialize() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ClassyCrafT");

        MyMenuBar menu = new MyMenuBar();
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);
    }

    public static MainFrame getInstance() {
        if(instance == null)
        {
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public void update(Object notification) {
        Message msg = (Message) notification;

        LocalDateTime dateTime = msg.getTimestamp().toLocalDateTime();
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String dateString = String.format("%02d.%02d.%d. %02d:%02d", day, month, year, hour, minute);

        String messageText = String.format("[%s][%s] %s", msg.getType().toString(), dateString, msg.getDescription());
        if (msg.getType() == MessageType.ERROR)
            JOptionPane.showMessageDialog(this, messageText, "ERROR", JOptionPane.ERROR_MESSAGE);
        else if (msg.getType() == MessageType.WARNING)
            JOptionPane.showMessageDialog(this, messageText, "WARNING", JOptionPane.WARNING_MESSAGE);
        else if (msg.getType() == MessageType.INFO)
            JOptionPane.showMessageDialog(this, messageText, "INFO", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, messageText, "NO ERROR, NO WARNING, NO INFO", JOptionPane.PLAIN_MESSAGE);
    }
}
