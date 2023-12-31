package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;

import java.io.IOException;

public final class EnumLiteralDeserializer extends StdDeserializer<EnumLiteral> {

    public EnumLiteralDeserializer() {
        this(null);
    }

    public EnumLiteralDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EnumLiteral deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        // Read attributes values from JSON
        String name = node.path("name").asText();

        return new EnumLiteral(name);
    }

}
