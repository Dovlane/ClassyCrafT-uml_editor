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
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.IOException;

public final class PackageDeserializer extends StdDeserializer<Package> {

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
        int nmbOfCreatedPackages = node.path("nmbOfCreatedPackages").asInt();
        int nmbOfCreatedDiagrams = node.path("nmbOfCreatedDiagrams").asInt();

        // Link to the parent
        String parentAbsolutePath = ClassyNode.getCurrentSelectedNodeAbsolutePath() + "/" + node.path("parent").asText();
        ClassyNode parent = MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        Package aPackage = new Package(name, parent);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, aPackage);
        if (!success) {
            System.out.println("Could not add a package to its parent.");
            return null;
        }

        // Write attributes to the package
        aPackage.setNmbOfCreatedPackages(nmbOfCreatedPackages);
        aPackage.setNmbOfCreatedDiagrams(nmbOfCreatedDiagrams);

        // Deserialize children
        JsonNode children = node.path("children");
        if (children.isArray()) {
            for (JsonNode child: children) {
                switch (child.path("type").asText()) {
                    case "Package" -> objectMapper.treeToValue(child, Package.class);
                    case "Diagram" -> objectMapper.treeToValue(child, Diagram.class);
                }
            }
        }

        return aPackage;
    }

}
