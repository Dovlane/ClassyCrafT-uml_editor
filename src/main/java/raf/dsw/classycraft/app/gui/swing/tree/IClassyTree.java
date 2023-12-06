package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

public interface IClassyTree {

    ClassyTreeView generateTree(ProjectExplorer projectExplorer);
    boolean addChild(ClassyTreeItem parent, ClassyNodeType type);
    boolean attachChild(ClassyTreeItem parent, ClassyNode child);
    void removeItem(ClassyTreeItem item);
    void renameItem(ClassyTreeItem item);
    ClassyTreeItem getSelectedNode();

}
