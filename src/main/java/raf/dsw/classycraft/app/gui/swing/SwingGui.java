package raf.dsw.classycraft.app.gui.swing;

import raf.dsw.classycraft.app.core.Gui;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.commandPattern.CommandManager;

public class SwingGui implements Gui {
    private MainFrame instance;
    private CommandManager commandManager;


    public SwingGui() {
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        commandManager = new CommandManager();

        disableRedoAction();
        disableUndoAction();
        instance.setVisible(true);
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public void disableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);

    }

    @Override
    public void disableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);

    }
    @Override
    public void enableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);


    }
    @Override
    public void enableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);

    }
}
