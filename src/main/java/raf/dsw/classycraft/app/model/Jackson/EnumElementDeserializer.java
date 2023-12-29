package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.io.IOException;

public final class EnumElementDeserializer extends StdDeserializer<EnumElement> {

    public EnumElementDeserializer() {
        this(null);
    }

    public EnumElementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EnumElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String name = node.path("name").asText();
        String absolutePath = node.path("absolutePath").asText();
        AccessModifiers visibility = AccessModifiers.valueOf(node.path("visibility").asText());
        NonAccessModifiers nonAccessModifiers = NonAccessModifiers.valueOf(node.path("nonAccessModifiers").asText());
        Point location = objectMapper.treeToValue(node.path("location"), Point.class);

        // Link to parent
        Diagram parent;
        EnumElement enumElement;
        if (node.path("parent").isObject()) {
            parent = objectMapper.treeToValue(node.path("parent"), Diagram.class);
        } else {
            String parentAbsolutePath = node.path("parent").asText();
            parent = (Diagram) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        }
        enumElement = new EnumElement(name, parent, location, visibility, nonAccessModifiers);

        // Write attributes to the project
        enumElement.setAbsolutePath(absolutePath);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, enumElement);
        if (!success) {
            System.out.println("EnumElement with the same name already exists.");
            return null;
        }

        // Deserialize enumLiterals
        JsonNode enumLiterals = node.path("enumLiterals");
        if (enumLiterals.isArray()) {
            for (JsonNode enumLiteral : enumLiterals) {
                EnumLiteral newEnumLiteral = objectMapper.treeToValue(enumLiteral, EnumLiteral.class);
                enumElement.addEnumLiteral(newEnumLiteral);
            }
        }

        return enumElement;
    }

}
