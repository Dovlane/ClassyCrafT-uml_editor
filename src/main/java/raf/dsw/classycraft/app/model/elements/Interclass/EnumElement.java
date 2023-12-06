package raf.dsw.classycraft.app.model.elements.Interclass;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class EnumElement extends Interclass {
    private List<EnumLiteral> enumLiterals;
    public EnumElement(String name, Diagram parent) {
        super(name, parent);
        enumLiterals = new ArrayList<>();
    }

    public EnumElement(String name, Diagram parent, Point point, AccessModifiers visibility) {
        super(name, parent, point, visibility);
        enumLiterals = new ArrayList<>();
    }

    public List<EnumLiteral> getEnumLiterals() {
        return enumLiterals;
    }

    public void setEnumLiterals(List<EnumLiteral> enumLiterals) {
        this.enumLiterals = enumLiterals;
    }


    @Override
    public String toString() {
        return getName();
    }
}
