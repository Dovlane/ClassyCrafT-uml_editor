package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.ClassElementDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = ClassElementDeserializer.class)
public class ClassElement extends Interclass {

    private List<ClassContent> classContent;

    public ClassElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        classContent = new ArrayList<>();
    }

    // Create a Deep Copy Constructor
    public ClassElement(ClassElement classElement) {
        super(classElement);
        classContent = new ArrayList<>();
        for (ClassContent aClassContent: classElement.getClassContent()) {
            if (aClassContent instanceof Method) {
                addClassContent(new Method((Method) aClassContent));
            }
            else if (aClassContent instanceof Attribute) {
                addClassContent(new Attribute((Attribute) aClassContent));
            }
        }
    }

    public void addClassContent(ClassContent classContent) {
        getClassContent().add(classContent);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    @JsonIgnore
    public List<Attribute> getClassAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (ClassContent classContent : getClassContent()) {
            if (classContent instanceof Attribute) {
                attributes.add((Attribute) classContent);
            }
        }
        return attributes;
    }

    @JsonIgnore
    public List<Method> getClassMethods() {
        ArrayList<Method> methods = new ArrayList<>();
        for (ClassContent classContent : getClassContent()) {
            if (classContent instanceof Method) {
                methods.add((Method) classContent);
            }
        }
        return methods;
    }

}
