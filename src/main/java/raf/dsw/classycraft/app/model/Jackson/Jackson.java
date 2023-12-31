package raf.dsw.classycraft.app.model.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.*;

public class Jackson implements IJackson {

    private final ObjectMapper objectMapper;

    public Jackson() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public ClassyNode loadFromJSONFile(File file, Class<?> classyNodeType) {
        try (FileReader fileReader = new FileReader(file)) {
            return (ClassyNode) objectMapper.readValue(fileReader, classyNodeType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveToJSONFile(ClassyNode node) {
        try (FileWriter writer = new FileWriter(node.getJSONFilePath())) {
            objectMapper.writeValue(writer, node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
