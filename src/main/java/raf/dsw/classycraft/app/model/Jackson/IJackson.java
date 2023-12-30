package raf.dsw.classycraft.app.model.Jackson;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.File;

public interface IJackson {

    Project loadProject(File file);
    void saveProject(Project node);
    Diagram loadDiagram(File file);
    void saveDiagram(Diagram node, String diagramTemplateName);

}
