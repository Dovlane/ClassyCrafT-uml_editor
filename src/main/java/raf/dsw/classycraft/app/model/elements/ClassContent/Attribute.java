package raf.dsw.classycraft.app.model.elements.ClassContent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.Jackson.AttributeDeserializer;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

@Getter
@Setter
@JsonDeserialize(using = AttributeDeserializer.class)
public class Attribute extends ClassContent {

    private AccessModifiers accessModifiers;
    private NonAccessModifiers nonAccessModifiers;
    private String name;
    private String dataType;

    public Attribute(AccessModifiers accessModifiers, NonAccessModifiers nonAccessModifiers, String name, String dataType) {
        super();
        this.accessModifiers = accessModifiers;
        this.nonAccessModifiers = nonAccessModifiers;
        this.name = name;
        this.dataType = dataType;
    }

    // Create a Deep Copy Constructor
    public Attribute(Attribute attribute) {
        this(attribute.accessModifiers, attribute.nonAccessModifiers, attribute.name, attribute.dataType);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (accessModifiers) {
            case PRIVATE -> stringBuilder.append('-');
            case PUBLIC -> stringBuilder.append('+');
            case PROTECTED -> stringBuilder.append('#');
            case DEFAULT -> stringBuilder.append('~');
        }
        stringBuilder.append(dataType).append(" ").append(name);
        return stringBuilder.toString();
    }

}
