package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyRepository;

public class ApplicationFramework {

    private static ApplicationFramework instance;
    private ClassyRepository classyRepository;

    private ApplicationFramework() {
        ;
    }

    public void initialize() {
        classyRepository = new ClassyRepository();
        MainFrame.getInstance().setVisible(true);
    }

    public static ApplicationFramework getInstance() {
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public ClassyRepository getClassyRepository() {
        return classyRepository;
    }

}
