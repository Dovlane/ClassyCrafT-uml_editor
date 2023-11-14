package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.controller.debug.ErrorTestAction;
import raf.dsw.classycraft.app.gui.swing.controller.debug.PrintTreeAction;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import java.util.ArrayList;

public class ActionManager {

    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private NewPackageAction newPackageAction;
    private NewDiagramAction newDiagramAction;
    private RemoveItemAction removeItemAction;
    private RenameAction renameAction;
    private ProjectAuthorAction projectAuthorAction;
    private AboutUsAction aboutUsAction;

    // DEBUG actions
    private ErrorTestAction errorTestAction;
    private PrintTreeAction printTreeAction;
    public ActionManager() {
        initializeActions();
    }

    private void initializeActions() {
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        newPackageAction = new NewPackageAction();
        newDiagramAction = new NewDiagramAction();
        removeItemAction = new RemoveItemAction();
        renameAction = new RenameAction();
        projectAuthorAction = new ProjectAuthorAction();
        aboutUsAction = new AboutUsAction();

        // DEBUG actions
        errorTestAction = new ErrorTestAction();
        printTreeAction = new PrintTreeAction();
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public NewProjectAction getNewProjectAction() {
        return newProjectAction;
    }

    public NewPackageAction getNewPackageAction() {
        return newPackageAction;
    }

    public NewDiagramAction getNewDiagramAction() {
        return newDiagramAction;
    }

    public RemoveItemAction getRemoveItemAction() {
        return removeItemAction;
    }

    public RenameAction getRenameAction() {
        return renameAction;
    }

    public ProjectAuthorAction getProjectAuthorAction() {
        return projectAuthorAction;
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }

    public ErrorTestAction getErrorTestAction() {
        return errorTestAction;
    }

    public PrintTreeAction getPrintTreeAction() {
        return printTreeAction;
    }

}
