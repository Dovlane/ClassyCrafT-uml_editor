package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

public class Aggregation extends Connection implements  IAggregationAndComposition {
    private String attributeName;
    private CardinalityEnum cardinalityEnum;
    private AccessModifiers attributeAccessModifier;

    public Aggregation(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
        this.cardinalityEnum = CardinalityEnum.ONE;
        this.attributeAccessModifier = AccessModifiers.PRIVATE;
    }

    @Override
    public void setAttributeNameCardinalityAndAccessibility(String attributeName, CardinalityEnum cardinalityEnum, AccessModifiers attributeAccessModifier) {
        this.attributeName = attributeName;
        this.cardinalityEnum = cardinalityEnum;
        this.attributeAccessModifier = attributeAccessModifier;
    }

    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public CardinalityEnum getCardinalityEnum() {
        return cardinalityEnum;
    }

    @Override
    public AccessModifiers getAttributeAccessModifier() {
        return attributeAccessModifier;
    }
}