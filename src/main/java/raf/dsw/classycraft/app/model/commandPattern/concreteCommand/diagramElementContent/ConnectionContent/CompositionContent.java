package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.ConnectionContent;

import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;
import raf.dsw.classycraft.app.model.elements.Connection.CardinalityEnum;
import raf.dsw.classycraft.app.model.elements.Connection.Composition;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.util.Objects;

public class CompositionContent extends ConnectionContent{
    private CardinalityEnum cardinalityEnum;
    private String attributeName;
    private AccessModifiers attributeAccessModifier;
    public CompositionContent(Composition composition) {
        super(composition);
        this.cardinalityEnum = composition.getCardinalityEnum();
        this.attributeName = composition.getAttributeName();
        this.attributeAccessModifier = composition.getAttributeAccessModifier();
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        Composition composition = (Composition) diagramElement;
        composition.setAttributeNameCardinalityAndAccessibility(attributeName, cardinalityEnum, attributeAccessModifier);
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        Composition composition = (Composition) diagramElement;
        return super.thereIsNothingToUpdate(diagramElement) && composition.getAttributeAccessModifier() == attributeAccessModifier &&
                composition.getCardinalityEnum() == cardinalityEnum && composition.getAttributeName().equals(attributeName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompositionContent that = (CompositionContent) o;
        return cardinalityEnum == that.cardinalityEnum && Objects.equals(attributeName, that.attributeName) && attributeAccessModifier == that.attributeAccessModifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardinalityEnum, attributeName, attributeAccessModifier);
    }
}
