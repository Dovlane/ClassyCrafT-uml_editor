package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class ElementPainter {

    protected DiagramElement diagramElement;

    public ElementPainter(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }

    public abstract void draw(Graphics2D graphics2D);
    public abstract void draw(Graphics2D graphics2D, AffineTransform transform);

    public abstract void drawSelectionBox(Graphics2D graphics2D);

    public abstract boolean elementAt(Point location);

    public abstract Point getUpperLeft();

    public abstract Point getBottomRight();

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
