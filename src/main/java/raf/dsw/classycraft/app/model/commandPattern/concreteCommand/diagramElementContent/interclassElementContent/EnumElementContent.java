package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent;

import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnumElementContent extends InterclassElementContent{
    private List<EnumLiteral> enumLiterals;

    public EnumElementContent(EnumElement enumElement) {
        super(enumElement);
        this.enumLiterals = new ArrayList<>();
        for (EnumLiteral enumLiteral : enumElement.getEnumLiterals()) {
            enumLiterals.add(new EnumLiteral(enumLiteral));
        }
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        EnumElement enumElement = (EnumElement) diagramElement;
        enumElement.setEnumLiterals(enumLiterals);
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        EnumElement enumElement = (EnumElement) diagramElement;
        return super.thereIsNothingToUpdate(diagramElement) && listEqualsIgnoreOrder(enumElement.getEnumLiterals(), enumLiterals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EnumElementContent that = (EnumElementContent) o;
        return listEqualsIgnoreOrder(enumLiterals, that.enumLiterals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), enumLiterals);
    }
}
