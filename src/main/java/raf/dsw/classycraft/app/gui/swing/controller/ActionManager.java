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
    private OpenAction openAction;
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
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        newPackageAction = new NewPackageAction();
        newDiagramAction = new NewDiagramAction();
        removeItemAction = new RemoveItemAction();
        renameAction = new RenameAction();
        projectAuthorAction = new ProjectAuthorAction();
        saveAction = new SaveAction();
        openAction = new OpenAction();
        aboutUsAction = new AboutUsAction();
        redoAction = new RedoAction();
        undoAction = new UndoAction();

        // DEBUG actions
        errorTestAction = new ErrorTestAction();
        printTreeAction = new PrintTreeAction();
        paintTestAction = new PaintTestAction();
    }

}
