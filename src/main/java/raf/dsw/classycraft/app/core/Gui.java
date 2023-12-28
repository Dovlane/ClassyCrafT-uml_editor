package raf.dsw.classycraft.app.core;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.commandPattern.CommandManager;

public interface Gui {
    void start();
    void disableUndoAction();
    void disableRedoAction();

    void enableUndoAction();
    void enableRedoAction();

    CommandManager getCommandManager();
}
