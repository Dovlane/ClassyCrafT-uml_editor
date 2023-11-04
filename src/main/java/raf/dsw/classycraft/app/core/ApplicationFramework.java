package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.IClassyTree;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.classyRepository.ClassyRepository;
import raf.dsw.classycraft.app.model.classyRepository.IClassyRepository;

public class ApplicationFramework {

    private static ApplicationFramework instance;
    private IClassyRepository classyRepository;
    private IClassyTree classyTree;

    private ApplicationFramework() {

    }

    public void initialize() {
        classyRepository = new ClassyRepository();
        classyTree = new ClassyTreeImplementation();
        MainFrame.getInstance().setVisible(true);
    }

    public static ApplicationFramework getInstance(){
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public IClassyRepository getClassyRepository() {
        return classyRepository;
    }

    public void setClassyRepository(IClassyRepository classyRepository) {
        this.classyRepository = classyRepository;
    }

    public IClassyTree getClassyTree() {
        return classyTree;
    }

    public void setClassyTree(IClassyTree classyTree) {
        this.classyTree = classyTree;
    }
}
