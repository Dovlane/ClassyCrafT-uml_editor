package raf.dsw.classycraft.app.gui.swing.view;

public interface CommandGui {
    void updateUndoAndRedoButtons();
    void disableUndoAction();
    void disableRedoAction();
    void enableUndoAction();
    void enableRedoAction();
}
