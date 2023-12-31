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
import raf.dsw.classycraft.app.model.elements.Connection.Generalization;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.io.IOException;

public class GeneralizationDeserializer extends StdDeserializer<Generalization> {

    public GeneralizationDeserializer() {
        this(null);
    }

    public GeneralizationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Generalization deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        // Parser
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read attributes values from JSON
        String plainName = node.path("plainName").asText();

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
        Generalization generalization = new Generalization(plainName, parent, from, to);

        // Write attributes to the generalization

        // Add a child to its parent
        ClassyTreeItem classyTreeParent =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        boolean success = MainFrame.getInstance().getClassyTree().attachChild(classyTreeParent, generalization);
        if (!success) {
            System.out.println("Could not add a generalization to its parent.");
            return null;
        }

        return generalization;
    }

}
