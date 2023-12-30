package raf.dsw.classycraft.app.model.elements.ClassContent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Method.class, name = "Method"),
        @JsonSubTypes.Type(value = Attribute.class, name = "Attribute")
})
public abstract class ClassContent {

}
