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
import raf.dsw.classycraft.app.model.elements.Connection.CardinalityEnum;
import raf.dsw.classycraft.app.model.elements.Connection.Composition;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.io.IOException;

public class CompositionDeserializer extends StdDeserializer<Composition> {

    public CompositionDeserializer() {
        this(null);
    }

    public CompositionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Composition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String plainName = node.path("plainName").asText();
        String absolutePath = node.path("absolutePath").asText();
        String attributeName = node.path("attributeName").asText();
        CardinalityEnum cardinalityEnum = CardinalityEnum.valueOf(node.path("cardinalityEnum").asText());
        AccessModifiers attributeAccessModifier = AccessModifiers.valueOf(node.path("attributeAccessModifier").asText());

        // Find parent
        Diagram parent;
        if (node.path("parent").isObject()) {
            parent = objectMapper.treeToValue(node.path("parent"), Diagram.class);
        }
        else {
            String parentAbsolutePath = node.path("parent").asText();
            parent = (Diagram) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        }

        // Find from
        Interclass from;
        if (node.path("from").isObject()) {
            switch (node.path("from").path("type").asText()) {
                case "ClassElement" -> from = objectMapper.treeToValue(node.path("from"), ClassElement.class);
                case "InterfaceElement" -> from = objectMapper.treeToValue(node.path("from"), InterfaceElement.class);
                case "EnumElement" -> from = objectMapper.treeToValue(node.path("from"), EnumElement.class);
                default -> {
                    return null;
                }
            }
        }
        else {
            String fromAbsolutePath = node.path("from").asText();
            from = (Interclass) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(fromAbsolutePath);
        }

        // Find to
        Interclass to;
        if (node.path("to").isObject()) {
            switch (node.path("to").path("type").asText()) {
                case "ClassElement" -> to = objectMapper.treeToValue(node.path("to"), ClassElement.class);
                case "InterfaceElement" -> to = objectMapper.treeToValue(node.path("to"), InterfaceElement.class);
                case "EnumElement" -> to = objectMapper.treeToValue(node.path("to"), EnumElement.class);
                default -> {
                    return null;
                }
            }
        }
        else {
            String toAbsolutePath = node.path("to").asText();
            to = (Interclass) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(toAbsolutePath);
        }

        // Link to the parent
        Composition composition = new Composition(plainName, parent, from, to);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, composition);
        if (!success) {
            System.out.println("Composition with the same name already exists.");
            return null;
        }

        // Write attributes to the composition
        composition.setAttributeNameCardinalityAndAccessibility(attributeName, cardinalityEnum, attributeAccessModifier);

        return composition;
    }

}
