package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;

public class ActionManager {

    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private AboutUsAction aboutUsAction;
    private ErrorTestAction errorTestAction;
    private MessageGenerator msgGenerator;
    public ActionManager(MessageGenerator msgGenerator) {
        this.msgGenerator = msgGenerator;
        initializeActions();
    }

    private void initializeActions() {
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        aboutUsAction = new AboutUsAction();
        errorTestAction = new ErrorTestAction(msgGenerator);
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

    public ErrorTestAction getErrorTestAction() {
        return errorTestAction;
    }
}
