package raf.dsw.classycraft.app.gui.swing.controller;

public class ActionManager {

    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private AboutUsAction aboutUsAction;

    public ActionManager() {
        initializeActions();
    }

    private void initializeActions() {
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        aboutUsAction = new AboutUsAction();
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public NewProjectAction getNewProjectAction() {
        return newProjectAction;
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }
}
