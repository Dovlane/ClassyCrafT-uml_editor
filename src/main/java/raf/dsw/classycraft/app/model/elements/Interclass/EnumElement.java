package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.EnumElementDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@JsonDeserialize(using = EnumElementDeserializer.class)
public class EnumElement extends Interclass {

    private List<EnumLiteral> enumLiterals;

    public EnumElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        enumLiterals = new ArrayList<>();
    }

    // Create a Deep Copy Constructor
    public EnumElement(EnumElement enumElement) {
        super(enumElement);
        enumLiterals = new ArrayList<>();
        for (EnumLiteral aEnumLiteral: enumElement.getEnumLiterals()) {
            addEnumLiteral(new EnumLiteral(aEnumLiteral));
        }
    }

    public void addEnumLiteral(EnumLiteral enumLiteral) {
        getEnumLiterals().add(enumLiteral);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
        changeOccurred();
    }

    @Override
    public String toString() {
        return getName();
    }

}
