package raf.dsw.classycraft.app.model.elements.ClassContent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.Jackson.EnumLiteralDeserializer;

@Getter
@Setter
@JsonDeserialize(using = EnumLiteralDeserializer.class)
public class EnumLiteral {

    private String name;

    public EnumLiteral(String name) {
        this.name = name;
    }

    // Create a Deep Copy Constructor
    public EnumLiteral(EnumLiteral enumLiteral) {
        this(enumLiteral.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
