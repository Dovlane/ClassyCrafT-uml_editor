package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.SwingGui;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.ClassyRepository;
import raf.dsw.classycraft.app.model.JacksonSerializer.JacksonSerializer;
import raf.dsw.classycraft.app.model.JacksonSerializer.Serializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFramework {

    private static ApplicationFramework instance;
    private ClassyRepository classyRepository;
    private Serializer serializer;
    private Gui gui;
    private void run() { this.gui.start(); }

    public void initialize() {
        gui = new SwingGui();
        classyRepository = new ClassyRepository();
        serializer = new JacksonSerializer();
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

    public Serializer getSerializer() {
        return serializer;
    }

    public Gui getGui() {
        return gui;
    }
}
