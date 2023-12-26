package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

public interface IAggregationAndComposition {
    void setAttributeNameCardinalityAndAccessibility(String attributeName, CardinalityEnum cardinalityEnum, AccessModifiers attributeAccessModifier);
    String getAttributeName();
    CardinalityEnum getCardinalityEnum();
    AccessModifiers getAttributeAccessModifier();
}
