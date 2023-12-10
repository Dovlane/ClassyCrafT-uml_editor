package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class ClassyTreeCellRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
        URL imageURL = null;

        if (((ClassyTreeItem) value).getClassyNode() instanceof ProjectExplorer) {
            imageURL = getClass().getResource("/images/ProjectExplorer.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof Project) {
            imageURL = getClass().getResource("/images/Project.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof Package) {
            imageURL = getClass().getResource("/images/Package.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof Diagram) {
            imageURL = getClass().getResource("/images/Diagram.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof ClassElement) {
            imageURL = getClass().getResource("/images/ClassElement.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof InterfaceElement) {
            imageURL = getClass().getResource("/images/InterfaceElement.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof EnumElement) {
            imageURL = getClass().getResource("/images/EnumElement.png");
        }
        else if (((ClassyTreeItem) value).getClassyNode() instanceof Connection) {
            imageURL = getClass().getResource("/images/Connection.png");
        }

        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        setIcon(icon);

        return this;
    }
}
