package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.Jackson.AggregationDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

@Getter
@Setter
@JsonDeserialize(using = AggregationDeserializer.class)
public class Aggregation extends Connection implements IAggregationAndComposition {

    private String attributeName;
    private CardinalityEnum cardinalityEnum;
    private AccessModifiers attributeAccessModifier;

    public Aggregation(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
        this.cardinalityEnum = CardinalityEnum.ZERO_OR_ONE;
        this.attributeName = "default_name";
        this.attributeAccessModifier = AccessModifiers.PRIVATE;
    }

    @Override
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
        return  "agg-" + from.getName() + "-" + to.getName();
    }

}
