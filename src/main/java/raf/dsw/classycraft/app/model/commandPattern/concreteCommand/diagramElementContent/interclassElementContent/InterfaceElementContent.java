package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent;

import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InterfaceElementContent extends InterclassElementContent {
    private final List<Method> methodList;
    public InterfaceElementContent(InterfaceElement interfaceElement) {
        super(interfaceElement);
        this.methodList = new ArrayList<>();
        for (Method method : interfaceElement.getInterfaceMethods()) {
            methodList.add(new Method(method));
        }
    }

    @Override
    public void setDiagramElementContent(DiagramElement diagramElement) {
        InterfaceElement interfaceElement = (InterfaceElement) diagramElement;
        interfaceElement.setInterfaceMethods(methodList);
        super.setDiagramElementContent(diagramElement) ;
    }

    @Override
    public boolean thereIsNothingToUpdate(DiagramElement diagramElement) {
        InterfaceElement interfaceElement = (InterfaceElement) diagramElement;
        return super.thereIsNothingToUpdate(diagramElement) && listEqualsIgnoreOrder(interfaceElement.getInterfaceMethods(), methodList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InterfaceElementContent that = (InterfaceElementContent) o;
        //return Objects.equals(methodList, that.methodList);
        return listEqualsIgnoreOrder(that.methodList, methodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), methodList);
    }
}
