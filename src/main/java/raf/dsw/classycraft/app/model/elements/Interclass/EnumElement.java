package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class EnumElement extends Interclass {
    private List<EnumLiteral> enumLiterals;
    public EnumElement(String name, Diagram parent) {
        super(name, parent);
        enumLiterals = new ArrayList<>();
    }

    public EnumElement(String name, Diagram parent, Point point, AccessModifiers visibility, NonAccessModifiers nonAccessModifiers) {
        super(name, parent, point, visibility, nonAccessModifiers);
        enumLiterals = new ArrayList<>();
    }

    public List<EnumLiteral> getEnumLiterals() {
        return enumLiterals;
    }

    public void setEnumLiterals(List<EnumLiteral> enumLiterals) {
        this.enumLiterals = enumLiterals;
    }

    public void addEnumLiteral(EnumLiteral enumLiteral) {
        getEnumLiterals().add(enumLiteral);
        Notification notification =
                new Notification(null, NotificationType.ADD);
        notifyAllSubscribers(notification);
    }

    @Override
    public String toString() {
        return getName();
    }
}
