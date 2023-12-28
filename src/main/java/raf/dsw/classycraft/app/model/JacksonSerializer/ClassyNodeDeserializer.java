package raf.dsw.classycraft.app.model.JacksonSerializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.IOException;

public final class ClassyNodeDeserializer extends JsonDeserializer<ClassyNode> {

    @Override
    public ClassyNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        String name = node.path("name").asText();
        return new Project(name, ApplicationFramework.getInstance().getClassyRepository().getRoot());
    }

}