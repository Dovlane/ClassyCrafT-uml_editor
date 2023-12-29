package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.*;

public class Jackson implements IJackson {

    private final ObjectMapper objectMapper;

    public Jackson() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }

    @Override
    public ClassyNode loadProject(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            return objectMapper.readValue(fileReader, Project.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveProject(Project project) {
        try (FileWriter writer = new FileWriter(project.getFolderPath())) {
            objectMapper.writeValue(writer, project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
