package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.gui.swing.controller.debug.ErrorTestAction;
import raf.dsw.classycraft.app.gui.swing.controller.debug.PaintTestAction;
import raf.dsw.classycraft.app.gui.swing.controller.debug.PrintTreeAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {

    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private NewPackageAction newPackageAction;
    private NewDiagramAction newDiagramAction;
    private RemoveItemAction removeItemAction;
    private RenameAction renameAction;
    private ProjectAuthorAction projectAuthorAction;
    private SaveAction saveAction;
    private SaveDiagramTemplateAction saveDiagramTemplateAction;
    private OpenProjectAction openProjectAction;
    private OpenPackageAction openPackageAction;
    private OpenDiagramAction openDiagramAction;
    private AboutUsAction aboutUsAction;
    private RedoAction redoAction;
    private UndoAction undoAction;

    // DEBUG actions
    private ErrorTestAction errorTestAction;
    private PrintTreeAction printTreeAction;
    private PaintTestAction paintTestAction;

    public ActionManager() {
        initializeActions();
    }

    private void initializeActions() {

        // FILE actions
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        newPackageAction = new NewPackageAction();
        newDiagramAction = new NewDiagramAction();
        removeItemAction = new RemoveItemAction();
        renameAction = new RenameAction();
        projectAuthorAction = new ProjectAuthorAction();
        saveAction = new SaveAction();
        saveDiagramTemplateAction = new SaveDiagramTemplateAction();
        redoAction = new RedoAction();
        undoAction = new UndoAction();

        // OPEN actions
        openProjectAction = new OpenProjectAction();
        openPackageAction = new OpenPackageAction();
        openDiagramAction = new OpenDiagramAction();

        // EDIT actions
        aboutUsAction = new AboutUsAction();

        // DEBUG actions
        errorTestAction = new ErrorTestAction();
        printTreeAction = new PrintTreeAction();
        paintTestAction = new PaintTestAction();
    }

}
