package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.*;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterfacePainter;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;

public class FactoryForPainters {
    public InterclassPainter createInterclassPainter(Interclass interclassElement) {
        if (interclassElement instanceof ClassElement) {
            return new ClassPainter((ClassElement) interclassElement);
        }
        else if (interclassElement instanceof InterfaceElement) {
            return new InterfacePainter((InterfaceElement) interclassElement);
        }
        else if (interclassElement instanceof EnumElement){
            return new EnumPainter((EnumElement) interclassElement);
        }
        return null;
    }
    public ConnectionPainter createConnectionPainter(Connection connectionElement) {
        if (connectionElement instanceof Dependency)
            return new DependencyPainter((Dependency) connectionElement);
        else if (connectionElement instanceof Generalization)
            return new GeneralisationPainter((Generalization) connectionElement);
        else if (connectionElement instanceof Composition)
            return new CompositionPainter((Composition) connectionElement);
        else if (connectionElement instanceof Aggregation)
            return new AgregationPainter((Aggregation) connectionElement);
        return null;
    }
}
