package raf.dsw.classycraft.app.model.JacksonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Project loadProject(File file) {
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
