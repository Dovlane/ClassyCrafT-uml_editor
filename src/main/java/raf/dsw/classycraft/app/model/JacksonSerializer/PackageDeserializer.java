package raf.dsw.classycraft.app.model.JacksonSerializer;

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
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.IOException;

public class PackageDeserializer extends StdDeserializer<Package> {

    public PackageDeserializer() {
        this(null);
    }

    public PackageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Package deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String name = node.path("name").asText();
        String absolutePath = node.path("absolutePath").asText();
        int nmbOfCreatedPackages = node.path("nmbOfCreatedPackages").asInt();
        int nmbOfCreatedDiagrams = node.path("nmbOfCreatedDiagrams").asInt();

        // Link to parent
        ClassyNode parent;
        Package aPackage;
        if (node.path("parent").isObject()) {
            if (node.path("parent").path("type").asText().equals("Project")) {
                parent = objectMapper.treeToValue(node.path("parent"), Project.class);
            }
            else {
                parent = objectMapper.treeToValue(node.path("parent"), Package.class);
            }
        }
        else {
            String parentAbsolutePath = node.path("parent").asText();
            parent = MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        }
        aPackage = new Package(name, parent);

        // Write attributes to the project
        aPackage.setAbsolutePath(absolutePath);
        aPackage.setNmbOfCreatedPackages(nmbOfCreatedPackages);
        aPackage.setNmbOfCreatedDiagrams(nmbOfCreatedDiagrams);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, aPackage);
        if (!success) {
            System.out.println("Package with the same name already exists.");
            return null;
        }

        // Deserialize children
        JsonNode children = node.path("children");
        if (children.isArray()) {
            for (JsonNode child: children) {
                if (child.path("type").asText().equals("Package")) {
                    objectMapper.treeToValue(child, Package.class);
                }
                else {
                    objectMapper.treeToValue(child, Diagram.class);
                }
            }
        }

        return aPackage;
    }

}
