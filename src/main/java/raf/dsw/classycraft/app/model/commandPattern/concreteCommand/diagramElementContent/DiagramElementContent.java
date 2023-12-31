package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent;

import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.util.Objects;

public abstract class DiagramElementContent implements IDiagramElementContent {
    protected String name;
    public DiagramElementContent(DiagramElement diagramElement) {
        this.name = diagramElement.getName();
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        diagramElement.setName(name);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        return name.equals(diagramElement.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramElementContent that = (DiagramElementContent) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
