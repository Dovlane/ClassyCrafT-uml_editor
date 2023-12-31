package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassElementContent extends InterclassElementContent {
    private List<ClassContent> classContentList;
    public ClassElementContent(ClassElement classElement) {
        super(classElement);
        this.classContentList = new ArrayList<>();
        for (ClassContent classContent : classElement.getClassContent()) {
            if (classContent instanceof Method)
                classContentList.add(new Method((Method) classContent));
            else if (classContent instanceof Attribute)
                classContentList.add(new Attribute((Attribute) classContent));
        }
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        ClassElement classElement = (ClassElement)diagramElement;
        classElement.setClassContent(classContentList);
        super.setDiagramElementContent(diagramElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassElementContent that = (ClassElementContent) o;
        //return Objects.equals(classContentList, that.classContentList);
        return listEqualsIgnoreOrder(classContentList, that.classContentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classContentList);
    }
}
