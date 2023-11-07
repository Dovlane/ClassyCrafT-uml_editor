package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.ActionManager;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MainFrame extends JFrame implements IListener {

    private static MainFrame instance;
    private MessageGenerator messageGenerator;
    private ActionManager actionManager;
    private ProjectView projectView;
    private MainFrame() {
        messageGenerator = new MessageGenerator();
        messageGenerator.addListener(this);
        actionManager = new ActionManager();
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


        JTree projectExplorer = ApplicationFramework.getInstance().getClassyTree().generateTree(ApplicationFramework.getInstance().getClassyRepository().getRoot());
        Label labelProjectName = new Label("Project name");
        Label labelAuthorName = new Label("Author name");
        projectView = new ProjectView(new PackageView(labelProjectName, labelAuthorName), labelProjectName, labelAuthorName);

        ((ClassyTreeView)projectExplorer).getClassyTreeCellEditor().addListener(projectView.getPackageView());

        JScrollPane scroll = new JScrollPane(projectExplorer);
        scroll.setMinimumSize(new Dimension(200,150));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, projectView);
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

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
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

    public ProjectView getProjectView() {
        return projectView;
    }
}
