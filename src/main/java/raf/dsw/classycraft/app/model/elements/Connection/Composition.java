package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.Jackson.CompositionDeserializer;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

@Getter
@Setter
@JsonDeserialize(using = CompositionDeserializer.class)
public class Composition extends Connection implements IAggregationAndComposition {

    private CardinalityEnum cardinalityEnum;
    private String attributeName;
    private AccessModifiers attributeAccessModifier;

    public Composition(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
        this.cardinalityEnum = CardinalityEnum.ZERO_OR_ONE;
        this.attributeName = "default_name";
        this.attributeAccessModifier = AccessModifiers.PRIVATE;
    }

    public void setAttributeNameCardinalityAndAccessibility(String attributeName, CardinalityEnum cardinalityEnum, AccessModifiers attributeAccessModifier) {
        if ((this.attributeName.equals(attributeName)) && (this.cardinalityEnum == cardinalityEnum) && (this.attributeAccessModifier == attributeAccessModifier)) {
            return;
        }
        this.attributeName = attributeName;
        this.cardinalityEnum = cardinalityEnum;
        this.attributeAccessModifier = attributeAccessModifier;
        changeOccurred();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "com-" + from.getName() + "-" + to.getName();
    }

}
