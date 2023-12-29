package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.ConnectionContent;

import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.DiagramElementContent;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

public abstract class ConnectionContent extends DiagramElementContent {
    public ConnectionContent(Connection connection) {
        super(connection);
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        return super.thereIsNothingToUpdate(diagramElement);
    }

}
