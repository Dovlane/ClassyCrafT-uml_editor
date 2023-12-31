package raf.dsw.classycraft.app.model.elements.ClassContent;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnumLiteral that = (EnumLiteral) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
