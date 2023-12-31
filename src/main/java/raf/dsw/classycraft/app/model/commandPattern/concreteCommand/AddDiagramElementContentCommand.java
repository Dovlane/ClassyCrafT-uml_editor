package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.DiagramElementContent;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

public class AddDiagramElementContentCommand extends AbstractCommand {

    private DiagramElement diagramElementContentChanged;
    private DiagramElementContent startContent;
    private DiagramElementContent finalContent;

    public AddDiagramElementContentCommand(DiagramElement diagramElementContentChanged, DiagramElementContent startContent, DiagramElementContent finalContent) {
        this.diagramElementContentChanged = diagramElementContentChanged;
        this.startContent = startContent;
        this.finalContent = finalContent;
    }

    @Override
    public void doCommand() {
        finalContent.setDiagramElementContent(diagramElementContentChanged);
        System.out.println("AddDiagramElementContentCommand - doCommand()");
    }

    @Override
    public void undoCommand() {
        startContent.setDiagramElementContent(diagramElementContentChanged);
        System.out.println("AddDiagramElementContentCommand - undoCommand()");
    }
}
