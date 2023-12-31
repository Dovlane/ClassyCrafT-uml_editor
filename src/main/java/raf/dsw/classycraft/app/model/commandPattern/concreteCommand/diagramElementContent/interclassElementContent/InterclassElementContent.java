package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent;

import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.DiagramElementContent;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public abstract class InterclassElementContent extends DiagramElementContent {
    protected AccessModifiers visibility;
    protected NonAccessModifiers nonAccessModifiers;

    protected static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public InterclassElementContent(Interclass interclass) {
        super(interclass);
        this.visibility = interclass.getVisibility();
        this.nonAccessModifiers = interclass.getNonAccessModifiers();
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        super.setDiagramElementContent(diagramElement);
        Interclass interclass = (Interclass) diagramElement;
        interclass.setVisibility(visibility);
        interclass.setNonAccessModifiers(nonAccessModifiers);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        Interclass interclass = (Interclass) diagramElement;
        return super.thereIsNothingToUpdate(diagramElement) && interclass.getVisibility().equals(visibility) && interclass.getNonAccessModifiers() == nonAccessModifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InterclassElementContent that = (InterclassElementContent) o;
        return visibility == that.visibility && nonAccessModifiers == that.nonAccessModifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visibility, nonAccessModifiers);
    }
}
