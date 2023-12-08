package raf.dsw.classycraft.app.model.elements.abstractFactoryElements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public class ClassyCraftManufacturer extends AbstractClassyCraftManufacturer{
    @Override
    public Interclass createInterclass(InfoForCreatingInterclass infoForCreatingInterclass) {
        String name = infoForCreatingInterclass.getName();
        Diagram parent = infoForCreatingInterclass.getParent();
        Point point = infoForCreatingInterclass.getPoint();
        AccessModifiers visibility = infoForCreatingInterclass.getVisibility();
        NonAccessModifiers nonAccessModifiers = infoForCreatingInterclass.getNonAccessModifier();
        ElementInterclassType elementInterclassType = infoForCreatingInterclass.getElementInterclassType();
        if (elementInterclassType == ElementInterclassType.CLASS)
            return new ClassElement(name, parent, point, visibility, nonAccessModifiers);
        else if (elementInterclassType == ElementInterclassType.INTERFACE)
            return new InterfaceElement(name, parent, point, visibility, nonAccessModifiers);
        else if (elementInterclassType == ElementInterclassType.ENUM)
            return new EnumElement(name, parent, point, visibility, nonAccessModifiers);
        return null;
    }

    @Override
    public Connection createConnection(InfoForCreatingConnection infoForCreatingConnection) {
        String name = infoForCreatingConnection.getName();
        Diagram parent = infoForCreatingConnection.getParent();
        Interclass from = infoForCreatingConnection.getFrom();
        Interclass to = infoForCreatingConnection.getTo();
        ElementConnectionType elementConnectionType = infoForCreatingConnection.getElementConnectionType();
        if (elementConnectionType == ElementConnectionType.AGGREGATION)
            return new Aggregation(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.COMPOSITION)
            return new Composition(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.DEPENDENDCY)
            return new Dependency(name, parent, from, to);
        else if (elementConnectionType == ElementConnectionType.GENERALISATION)
            return new Generalization(name, parent, from, to);
        return null;
    }
}
