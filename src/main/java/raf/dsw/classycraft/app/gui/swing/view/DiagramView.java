package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.controller.debug.PaintTestAction;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterfacePainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements IListener {

    private Diagram diagram;
    private List<ElementPainter> painters;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
        painters = new ArrayList<>();
        diagram.addListener(this);
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof PaintTestAction) {
            ClassElement classElement = new ClassElement("class name", this.getDiagram(), new Point(100, 100), AccessModifiers.PUBLIC);
            classElement.getClassContent().add(new Attribute(AccessModifiers.PUBLIC, NonAccessModifiers.STATIC, "attribute_1", "int"));
            classElement.getClassContent().add(new Attribute(AccessModifiers.PRIVATE, NonAccessModifiers.STATIC, "attribute_2attribute_2attribute_2attribute_2", "int"));
            classElement.getClassContent().add(new Attribute(AccessModifiers.PRIVATE, NonAccessModifiers.SYNCHRONIZED, "attribute_3", "int"));

            List<Method> methods = new ArrayList<>();
            methods.add(new Method("method_1", AccessModifiers.PRIVATE, NonAccessModifiers.SYNCHRONIZED, "int"));
            methods.add(new Method("method_1", AccessModifiers.PUBLIC, NonAccessModifiers.SYNCHRONIZED, "void"));
            methods.add(new Method("method_2method_1method_1method_1", AccessModifiers.PUBLIC, NonAccessModifiers.STATIC, "String"));

            classElement.getClassContent().add(methods.get(0));
            classElement.getClassContent().add(methods.get(1));
            classElement.getClassContent().add(methods.get(2));

            InterfaceElement interfaceElement = new InterfaceElement("interface name", this.getDiagram(), new Point(400, 100), AccessModifiers.PUBLIC);
            interfaceElement.getInterfaceMethods().add(methods.get(0));
            interfaceElement.getInterfaceMethods().add(methods.get(1));
            interfaceElement.getInterfaceMethods().add(methods.get(2));
            interfaceElement.getInterfaceMethods().add(methods.get(0));
            interfaceElement.getInterfaceMethods().add(methods.get(1));
            interfaceElement.getInterfaceMethods().add(methods.get(2));
            interfaceElement.getInterfaceMethods().add(methods.get(0));
            interfaceElement.getInterfaceMethods().add(methods.get(1));
            interfaceElement.getInterfaceMethods().add(methods.get(2));

            EnumElement enumElement = new EnumElement("enum name", this.getDiagram(), new Point(700, 100), AccessModifiers.PUBLIC);
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1Enum 1Enum 1Enum 1Enum1Enum 1Enum 1Enum 1Enum "));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 3"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 3"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 3"));
            enumElement.getEnumLiterals().add(new EnumLiteral("Enum 1"));

            painters.add(new ClassPainter(classElement));
            painters.add(new InterfacePainter(interfaceElement));
            painters.add(new EnumPainter(enumElement));

        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (ElementPainter painter: painters) {
            painter.draw(graphics2D);
        }
        System.out.println("DiagramView paintComponent is being performed");
    }

    public Diagram getDiagram() {
        return diagram;
    }

}
