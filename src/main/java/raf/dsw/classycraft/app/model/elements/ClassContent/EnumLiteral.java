package raf.dsw.classycraft.app.model.elements.ClassContent;

public class EnumLiteral {

    private String name;

    public EnumLiteral(String name) {
        this.name = name;
    }

    // Create a Deep Copy Constructor
    public EnumLiteral(EnumLiteral enumLiteral) {
        this(enumLiteral.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
