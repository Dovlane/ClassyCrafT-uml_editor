package raf.dsw.classycraft.app.model.JacksonSerializer;

import raf.dsw.classycraft.app.model.ClassyRepository.Project;

import java.io.File;

public interface Serializer {

    Project loadProject(File file);
    void saveProject(Project node);

}
