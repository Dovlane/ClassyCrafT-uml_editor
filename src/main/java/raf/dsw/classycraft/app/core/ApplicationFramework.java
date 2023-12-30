package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyRepository;
import raf.dsw.classycraft.app.model.Jackson.Jackson;
import raf.dsw.classycraft.app.model.Jackson.IJackson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFramework {

    private static ApplicationFramework instance;
    private ClassyRepository classyRepository;
    private IJackson jackson;

    public void initialize() {
        classyRepository = new ClassyRepository();
        jackson = new Jackson();
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
