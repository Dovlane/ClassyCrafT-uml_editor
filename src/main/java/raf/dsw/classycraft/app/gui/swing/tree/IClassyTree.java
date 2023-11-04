package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.model.classyRepository.ClassyNodeType;
import raf.dsw.classycraft.app.model.classyRepository.ProjectExplorer;

public interface IClassyTree {

    ClassyTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(ClassyTreeItem parent, ClassyNodeType type);
    void removeItem(ClassyTreeItem item);
    void renameItem(ClassyTreeItem item);
    ClassyTreeItem getSelectedNode();

}
