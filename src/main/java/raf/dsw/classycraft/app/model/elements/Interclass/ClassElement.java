package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClassElement extends Interclass {

    private List<ClassContent> classContent;

    public ClassElement(String name, ClassyNode parent) {
        super(name, parent);
        classContent = new ArrayList<>();
    }

    public ClassElement(String name, ClassyNode parent, Point point, AccessModifiers visibility, String className) {
        super(name, parent, point, visibility, className);
        classContent = new ArrayList<>();
    }


    // Getters and Setters
    public List<ClassContent> getClassContent() {
        return classContent;
    }

    public void setClassContent(List<ClassContent> classContent) {
        this.classContent = classContent;
    }

}
