package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.ConnectionContent;

import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;
import raf.dsw.classycraft.app.model.elements.Connection.CardinalityEnum;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.util.Objects;

public class AggregationContent extends ConnectionContent {
    private CardinalityEnum cardinalityEnum;
    private String attributeName;
    private AccessModifiers attributeAccessModifier;

    public AggregationContent(Aggregation aggregation) {
        super(aggregation);
        this.cardinalityEnum = aggregation.getCardinalityEnum();
        this.attributeName = aggregation.getAttributeName();
        this.attributeAccessModifier = aggregation.getAttributeAccessModifier();
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        Aggregation aggregation = (Aggregation) diagramElement;
        aggregation.setAttributeNameCardinalityAndAccessibility(attributeName, cardinalityEnum, attributeAccessModifier);
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        Aggregation aggregation = (Aggregation) diagramElement;
        return super.thereIsNothingToUpdate(diagramElement) && aggregation.getAttributeAccessModifier() == attributeAccessModifier &&
                aggregation.getCardinalityEnum() == cardinalityEnum && aggregation.getAttributeName().equals(attributeName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AggregationContent that = (AggregationContent) o;
        return cardinalityEnum == that.cardinalityEnum && Objects.equals(attributeName, that.attributeName) && attributeAccessModifier == that.attributeAccessModifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardinalityEnum, attributeName, attributeAccessModifier);
    }
}
