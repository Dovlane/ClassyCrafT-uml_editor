package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.io.IOException;

public final class AttributeDeserializer extends StdDeserializer<Attribute> {

    public AttributeDeserializer() {
        this(null);
    }

    public AttributeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Attribute deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        // Read attributes values from JSON
        AccessModifiers accessModifiers = AccessModifiers.valueOf(node.path("accessModifiers").asText());
        NonAccessModifiers nonAccessModifiers = NonAccessModifiers.valueOf(node.path("nonAccessModifiers").asText());
        String name = node.path("name").asText();
        String type = node.path("type").asText();

        return new Attribute(accessModifiers, nonAccessModifiers, name, type);
    }

}
