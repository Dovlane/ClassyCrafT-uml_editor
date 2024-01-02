package raf.dsw.classycraft.app.model.elements.Interclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.Jackson.EnumElementDeserializer;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
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
    public void setEnumLiterals(List<EnumLiteral> enumLiterals) {
        this.enumLiterals = enumLiterals;
        Notification notification = new Notification(this, NotificationType.SET);
        notifyAllSubscribers(notification);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "Enum-" + getPlainName();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String firstLine = String.format("%s%s %s %s { \n" , modifiersStringHashMap.get(visibility), nonAccessModifiers.toString().toLowerCase(), "enum", getPlainName());
        stringBuilder.append(firstLine);

        stringBuilder.append("\t");
        int n = enumLiterals.size();
        if (n > 0) {
            for (int i = 0; i < n - 1; i++) {
                EnumLiteral enumLiteral = enumLiterals.get(i);
                stringBuilder.append(enumLiteral + ", ");
            }
            stringBuilder.append(enumLiterals.get(n - 1));
        }
        stringBuilder.append("\n");

        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }

}
