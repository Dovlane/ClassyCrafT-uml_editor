package raf.dsw.classycraft.app.model.elements.ClassContent;

import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

public class Method extends ClassContent {

    private AccessModifiers accessModifiers;
    private NonAccessModifiers nonAccessModifiers;
    private String name;

    public Method(AccessModifiers accessModifiers, NonAccessModifiers nonAccessModifiers, String name) {
        super();
        this.accessModifiers = accessModifiers;
        this.nonAccessModifiers = nonAccessModifiers;
        this.name = name;
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

}
