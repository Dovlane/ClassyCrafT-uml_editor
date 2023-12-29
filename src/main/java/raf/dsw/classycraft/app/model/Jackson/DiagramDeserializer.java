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
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;
import raf.dsw.classycraft.app.model.elements.Connection.Composition;
import raf.dsw.classycraft.app.model.elements.Connection.Dependency;
import raf.dsw.classycraft.app.model.elements.Connection.Generalization;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

import java.io.IOException;

public final class DiagramDeserializer extends StdDeserializer<Diagram> {

    public DiagramDeserializer() {
        this(null);
    }

    public DiagramDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Diagram deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String name = node.path("name").asText();
        String absolutePath = node.path("absolutePath").asText();

        // Link to the parent
        Package parent;
        Diagram diagram;
        if (node.path("parent").isObject()) {
            parent = objectMapper.treeToValue(node.path("parent"), Package.class);
        }
        else {
            String parentAbsolutePath = node.path("parent").asText();
            parent = (Package) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        }
        diagram = new Diagram(name, parent);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, diagram);
        if (!success) {
            System.out.println("Diagram with the same name already exists.");
            return null;
        }

        // Write attributes to the diagram

        // Deserialize children
        JsonNode children = node.path("children");
        if (children.isArray()) {

            // Create first all interclass objects
            for (JsonNode child: children) {
                switch (child.path("type").asText()) {
                    case "ClassElement"     -> objectMapper.treeToValue(child, ClassElement.class);
                    case "InterfaceElement" -> objectMapper.treeToValue(child, InterfaceElement.class);
                    case "EnumElement"      -> objectMapper.treeToValue(child, EnumElement.class);
                }
            }

            // Create all connections
            for (JsonNode child: children) {
                switch (child.path("type").asText()) {
                    case "Aggregation"      -> objectMapper.treeToValue(child, Aggregation.class);
                    case "Composition"      -> objectMapper.treeToValue(child, Composition.class);
                    case "Dependency"       -> objectMapper.treeToValue(child, Dependency.class);
                    case "Generalization"   -> objectMapper.treeToValue(child, Generalization.class);
                }
            }
        }

        return diagram;
    }

}
