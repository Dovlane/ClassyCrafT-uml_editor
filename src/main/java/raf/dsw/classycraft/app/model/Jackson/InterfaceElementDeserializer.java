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
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.io.IOException;

public final class InterfaceElementDeserializer extends StdDeserializer<InterfaceElement> {

    public InterfaceElementDeserializer() {
        this(null);
    }

    public InterfaceElementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public InterfaceElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
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
        InterfaceElement interfaceElement;
        if (node.path("parent").isObject()) {
            parent = objectMapper.treeToValue(node.path("parent"), Diagram.class);
        } else {
            String parentAbsolutePath = node.path("parent").asText();
            parent = (Diagram) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        }
        interfaceElement = new InterfaceElement(name, parent, location, visibility, nonAccessModifiers);

        // Write attributes to the project
        interfaceElement.setAbsolutePath(absolutePath);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, interfaceElement);
        if (!success) {
            System.out.println("InterfaceElement with the same name already exists.");
            return null;
        }

        // Deserialize classContent
        JsonNode interfaceMethods = node.path("interfaceMethods");
        if (interfaceMethods.isArray()) {
            for (JsonNode method : interfaceMethods) {
                Method newMethod = objectMapper.treeToValue(method, Method.class);
                interfaceElement.addMethod(newMethod);
            }
        }

        return interfaceElement;
    }

}
