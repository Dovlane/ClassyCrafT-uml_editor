package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.io.IOException;

public final class MethodDeserializer extends StdDeserializer<Method> {

    public MethodDeserializer() {
        this(null);
    }

    public MethodDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Method deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        // Read attributes values from JSON
        AccessModifiers accessModifiers = AccessModifiers.valueOf(node.path("accessModifiers").asText());
        NonAccessModifiers nonAccessModifiers = NonAccessModifiers.valueOf(node.path("nonAccessModifiers").asText());
        String name = node.path("name").asText();
        String returnType = node.path("returnType").asText();

        return new Method(name, accessModifiers, nonAccessModifiers, returnType);
    }

}
