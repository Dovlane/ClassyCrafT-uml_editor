package raf.dsw.classycraft.app.model.elements.ClassContent;

import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

public class Attribute extends ClassContent {

    private AccessModifiers accessModifiers;
    private NonAccessModifiers nonAccessModifiers;
    private String name;
    private String type;

    public Attribute(AccessModifiers accessModifiers, NonAccessModifiers nonAccessModifiers, String name, String type) {
        super();
        this.accessModifiers = accessModifiers;
        this.nonAccessModifiers = nonAccessModifiers;
        this.name = name;
        this.type = type;
    }

    // Create a Deep Copy Constructor
    public Attribute(Attribute attribute) {
        this(attribute.accessModifiers, attribute.nonAccessModifiers, attribute.name, attribute.type);
    }

    // Getters and Setters
    public AccessModifiers getAccessModifiers() {
        return accessModifiers;
    }

    public void setAccessModifiers(AccessModifiers accessModifiers) {
        this.accessModifiers = accessModifiers;
    }

    public NonAccessModifiers getNonAccessModifiers() {
        return nonAccessModifiers;
    }

    public void setNonAccessModifiers(NonAccessModifiers nonAccessModifiers) {
        this.nonAccessModifiers = nonAccessModifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (accessModifiers) {
            case PRIVATE -> stringBuilder.append('-');
            case PUBLIC -> stringBuilder.append('+');
            case PROTECTED -> stringBuilder.append('#');
            case DEFAULT -> stringBuilder.append('~');
        }
        stringBuilder.append(type + " " + name);
        return stringBuilder.toString();
    }
}
