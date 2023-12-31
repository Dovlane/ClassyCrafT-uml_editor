package raf.dsw.classycraft.app.model.Jackson;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.io.File;

public interface IJackson {

    ClassyNode loadFromJSONFile(File file, Class<?> classyNodeType);
    void saveToJSONFile(ClassyNode node);

}
