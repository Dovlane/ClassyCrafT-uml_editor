package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.ConnectionContent;

import raf.dsw.classycraft.app.model.elements.Connection.Dependency;
import raf.dsw.classycraft.app.model.elements.Connection.DependencyEnum;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.util.Objects;

public class DependencyContent extends ConnectionContent {
    private DependencyEnum dependencyEnum;
    public DependencyContent(Dependency dependency) {
        super(dependency);
        this.dependencyEnum = dependency.getDependencyEnum();
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        Dependency dependency = (Dependency) diagramElement;
        dependency.setDependencyEnum(dependencyEnum);
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DependencyContent that = (DependencyContent) o;
        return dependencyEnum == that.dependencyEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dependencyEnum);
    }
}
