package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.IOException;

public final class ProjectDeserializer extends StdDeserializer<Project> {

    public ProjectDeserializer() {
        this(null);
    }

    public ProjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Project deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String name = node.path("name").asText();
        String author = node.path("author").asText();
        int nmbOfCreatedPackages = node.path("nmbOfCreatedPackages").asInt();

        // Link to the parent
        String parentAbsolutePath = ClassyNode.getCurrentSelectedNodeAbsolutePath() + "/" + node.path("parent").asText();
        ClassyNode parent = MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);
        Project project = new Project(name, parent);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, project);
        if (!success) {
            System.out.println("Could not add a project to its parent.");
            return null;
        }

        // Write attributes to the project
        project.setAuthor(author);
        project.setNmbOfCreatedPackages(nmbOfCreatedPackages);

        // Deserialize children
        JsonNode children = node.path("children");
        if (children.isArray()) {
            for (JsonNode child: children) {
                objectMapper.treeToValue(child, Package.class);
            }
        }

        return project;
    }

}