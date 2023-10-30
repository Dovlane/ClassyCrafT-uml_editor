package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.model.logger.ConsoleLogger;
import raf.dsw.classycraft.app.model.logger.FileLogger;
import raf.dsw.classycraft.app.model.logger.Logger;
import raf.dsw.classycraft.app.model.logger.LoggerFactory;
import raf.dsw.classycraft.app.model.messageGenerator.Message;
import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.messageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDateTime;

public class MainFrame extends JFrame implements IListener {

    private static MainFrame instance;
    private MessageGenerator msgGenerator;
    private LoggerFactory loggerFactory;
    private Logger consoleLogger;
    private Logger fileLogger;
    private ActionManager actionManager;

    private MainFrame(){
        msgGenerator = new MessageGenerator();
        msgGenerator.addListener(this);
        Logger consoleLogger = LoggerFactory.createLogger("CONSOLELOGGER", msgGenerator);
        fileLogger = LoggerFactory.createLogger("FILELOGGER", msgGenerator);
        actionManager = new ActionManager(msgGenerator);
    }

    private void initialize(){
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

    public static MainFrame getInstance()
    {
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
    public void update(Object notification) throws ParseException {
        Message msg = (Message)notification;


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
