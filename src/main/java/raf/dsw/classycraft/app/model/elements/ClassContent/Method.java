package raf.dsw.classycraft.app.model.elements.ClassContent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.Jackson.MethodDeserializer;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

@Getter
@Setter
@JsonDeserialize(using = MethodDeserializer.class)
public class Method extends ClassContent {

    private AccessModifiers accessModifiers;
    private NonAccessModifiers nonAccessModifiers;
    private String name;
    private String returnType;

    public Method(String name, AccessModifiers accessModifiers, NonAccessModifiers nonAccessModifiers, String returnType) {
        super();
        this.accessModifiers = accessModifiers;
        this.nonAccessModifiers = nonAccessModifiers;
        this.name = name;
        this.returnType = returnType;
    }

    // Create a Deep Copy Constructor
    public Method(Method method) {
        this(method.name, method.accessModifiers, method.nonAccessModifiers, method.returnType);
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
        stringBuilder.append(name).append("() : ").append(getReturnType());
        return stringBuilder.toString();
    }

}
