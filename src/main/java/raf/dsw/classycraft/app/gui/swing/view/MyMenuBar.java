package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar() {

        // Add a File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewPackageAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewDiagramAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getRemoveItemAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getRenameAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getProjectAuthorAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getOpenAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());

        add(fileMenu);

        // Add a Edit Menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(MainFrame.getInstance().getActionManager().getAboutUsAction());
        add(editMenu);

        // Add a Debug Menu
        JMenu debugMenu = new JMenu("Debug");
        debugMenu.setMnemonic(KeyEvent.VK_D);
        debugMenu.add(MainFrame.getInstance().getActionManager().getErrorTestAction());
        debugMenu.add(MainFrame.getInstance().getActionManager().getPrintTreeAction());
        debugMenu.add(MainFrame.getInstance().getActionManager().getPaintTestAction());
        add(debugMenu);
    }

}
