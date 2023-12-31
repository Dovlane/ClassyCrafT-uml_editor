package raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent;

import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.ConnectionContent.*;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent.ClassElementContent;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent.EnumElementContent;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent.InterclassElementContent;
import raf.dsw.classycraft.app.model.commandPattern.concreteCommand.diagramElementContent.interclassElementContent.InterfaceElementContent;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

public class DiagramElementContentFactory {
    public DiagramElementContent createDiagramElementContent(DiagramElement diagramElement) {
        DiagramElementContent diagramElementContent = null;
        if (diagramElement instanceof Interclass) {
            if (diagramElement instanceof ClassElement) {
                diagramElementContent = new ClassElementContent((ClassElement) diagramElement);
            }
            else if (diagramElement instanceof InterfaceElement) {
                diagramElementContent = new InterfaceElementContent((InterfaceElement) diagramElement);
            }
            else if (diagramElement instanceof EnumElement) {
                diagramElementContent = new EnumElementContent((EnumElement) diagramElement);
            }
        }
        else if (diagramElement instanceof Connection) {
            if (diagramElement instanceof Composition) {
                diagramElementContent = new CompositionContent((Composition) diagramElement);
            }
            else if (diagramElement instanceof Aggregation) {
                diagramElementContent = new AggregationContent((Aggregation) diagramElement);
            }
            else if (diagramElement instanceof Dependency) {
                diagramElementContent = new DependencyContent((Dependency) diagramElement);
            }
            else if (diagramElement instanceof Generalization) {
                diagramElementContent = new GeneralizationContent((Generalization) diagramElement);
            }
        }

        return diagramElementContent;
    }
}
