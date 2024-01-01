package raf.dsw.classycraft.app.model.ClassyRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.Jackson.DiagramDeserializer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = DiagramDeserializer.class)
public class Diagram extends ClassyNodeComposite {

    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public boolean addChild(ClassyNode child) {
        if (child instanceof DiagramElement) {
            if (getChildByName(child.getName()) == null) {
                getChildren().add(child);
                Notification notification =
                        new Notification(child, NotificationType.ADD);
                notifyAllSubscribers(notification);
                changeOccurred();
                return true;
            }
        }


        return false;
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener: listeners)
            listener.update(notification);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<ClassyTreeItem> packageList = new ArrayList<>();

        ClassyTreeItem _package = MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(parent);
        while (_package.getClassyNode() instanceof Package) {
            packageList.add(_package);
            ClassyNode nodeParent = _package.getClassyNode().getParent();
            _package =  MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(nodeParent);
        }

        sb.append(getPackagePath(packageList));
        for (ClassyNode diagramElement : getChildren()) {
            if (diagramElement instanceof Connection)
                continue;
            sb.append(diagramElement.toString());
        }

        return sb.toString();
    }

    @JsonIgnore
    private String getPackagePath(List<ClassyTreeItem> packageList) {
        StringBuilder sb = new StringBuilder();
        int n = packageList.size();
        sb.append("package ");
        for (int i = n - 1; i >= 1; i--) {
            sb.append(packageList.get(i).toString() + ".");
        }
        sb.append(packageList.get(0).toString() + ";\n");
        return sb.toString();
    }
}
