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
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Connection.Dependency;
import raf.dsw.classycraft.app.model.elements.Connection.DependencyEnum;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.io.IOException;

public class DependencyDeserializer extends StdDeserializer<Dependency> {

    public DependencyDeserializer() {
        this(null);
    }

    public DependencyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Dependency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String plainName = node.path("plainName").asText();
        DependencyEnum dependencyEnum = DependencyEnum.valueOf(node.path("dependencyEnum").asText());

        // Find parent
        String parentAbsolutePath = ClassyNode.getCurrentSelectedNodeAbsolutePath() + "/" + node.path("parent").asText();
        Diagram parent = (Diagram) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(parentAbsolutePath);

        // Find from
        String fromAbsolutePath = ClassyNode.getCurrentSelectedNodeAbsolutePath() + "/" + node.path("from").asText();
        Interclass from = (Interclass) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(fromAbsolutePath);

        // Find to
        String toAbsolutePath = ClassyNode.getCurrentSelectedNodeAbsolutePath() + "/" + node.path("to").asText();
        Interclass to = (Interclass) MainFrame.getInstance().getClassyTree().getNodeFromAbsolutePath(toAbsolutePath);

        // Link to the parent
        Dependency dependency = new Dependency(plainName, parent, from, to);

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, dependency);
        if (!success) {
            System.out.println("Dependency with the same name already exists.");
            return null;
        }

        // Write attributes to the dependency
        dependency.setDependencyEnum(dependencyEnum);

        return dependency;
    }

}
