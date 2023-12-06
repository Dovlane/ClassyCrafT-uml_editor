package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;

public abstract class ElementPainter {

    protected Shape shape;
    protected DiagramElement diagramElement;
    protected Color color;
    protected int stroke;

    public ElementPainter(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }

    public abstract void draw(Graphics2D graphics2D);

    public abstract void drawSelectionBox(Graphics2D graphics2D);

    public abstract boolean elementAt(Point location);

    public DiagramElement getDiagramElement() {
        return diagramElement;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ElementPainter) {
            return getDiagramElement().equals(((ElementPainter) object).getDiagramElement());
        }
        return false;
    }

}
