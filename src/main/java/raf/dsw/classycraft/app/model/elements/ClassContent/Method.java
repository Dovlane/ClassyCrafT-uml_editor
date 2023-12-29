package raf.dsw.classycraft.app.model.elements.ClassContent;

import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.util.Objects;

public class Method extends ClassContent {

    private AccessModifiers accessModifiers;
    private NonAccessModifiers nonAccessModifiers;
    private String name;
    private String type;

    public Method( String name, AccessModifiers accessModifiers, NonAccessModifiers nonAccessModifiers, String type) {
        super();
        this.accessModifiers = accessModifiers;
        this.nonAccessModifiers = nonAccessModifiers;
        this.name = name;
        this.type = type;
    }

    // Create a Deep Copy Constructor
    public Method(Method method) {
        this(method.name, method.accessModifiers, method.nonAccessModifiers, method.type);
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (accessModifiers) {
            case PRIVATE -> stringBuilder.append('-');
            case PUBLIC -> stringBuilder.append('+');
            case PROTECTED -> stringBuilder.append('#');
            case DEFAULT -> stringBuilder.append('~');
        }
        stringBuilder.append(name + "() : " + getType());
        return stringBuilder.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return accessModifiers == method.accessModifiers && nonAccessModifiers == method.nonAccessModifiers && name.equals(method.name) && type.equals(method.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessModifiers, nonAccessModifiers, name, type);
    }
}
