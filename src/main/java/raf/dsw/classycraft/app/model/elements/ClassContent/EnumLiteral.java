package raf.dsw.classycraft.app.model.elements.ClassContent;

public class EnumLiteral {
    private String name;

    public EnumLiteral(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
