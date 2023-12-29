package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.ClassyRepository.ProjectExplorer;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes.InfoForCreatingClassyNode;

public interface IClassyTree {

    ClassyTreeView generateTree(ProjectExplorer projectExplorer);

    void loadProject(Project node);

    ClassyNode addChild(InfoForCreatingClassyNode infoForCreatingClassyNode);

    boolean attachChild(ClassyTreeItem parent, ClassyNode child);

    boolean attachChild(ClassyTreeItem parent, ClassyTreeItem child);

    void removeItem(ClassyTreeItem item);

    void renameItem(ClassyTreeItem item);

    boolean renameItem(ClassyTreeItem item, String newName);

    ClassyTreeItem getRoot();

    ClassyTreeItem getSelectedNode();

}
