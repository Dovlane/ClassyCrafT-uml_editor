package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTree;
import raf.dsw.classycraft.app.model.Logger.LoggerFactory;
import raf.dsw.classycraft.app.model.Logger.LoggerType;
import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MainFrame extends JFrame implements IListener {

    private static MainFrame instance;
    private final MessageGenerator messageGenerator;
    private final ActionManager actionManager;
    private final ClassyTree classyTree;

    private MainFrame() {
        messageGenerator = new MessageGenerator();
        messageGenerator.addListener(this);
        actionManager = new ActionManager();
        classyTree = new ClassyTree();
    }

    private void initialize() {

        // Create Loggers
        LoggerFactory.createLogger(LoggerType.CONSOLE_LOGGER);
        LoggerFactory.createLogger(LoggerType.FILE_LOGGER);

        // Create Graphics
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

        // Generate ProjectExplorer
        JTree projectExplorer = classyTree.generateTree(ApplicationFramework.getInstance().getClassyRepository().getRoot());
        JScrollPane scroll = new JScrollPane(projectExplorer);
        scroll.setMinimumSize(new Dimension(200,150));

        // Merge ProjectExplorer and Right Panel
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scroll, ApplicationFramework.getInstance().getClassyRepository().getPackageView());
        getContentPane().add(split, BorderLayout.CENTER);
        split.setDividerLocation(250);
        split.setOneTouchExpandable(true);
    }

    public static MainFrame getInstance() {
        if(instance == null)
        {
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
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

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public ClassyTree getClassyTree() {
        return classyTree;
    }

}
