package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;

public abstract class ElementPainter {
    protected Shape shape;
    protected DiagramElement diagramElement;
    protected Color color;
    protected int stroke;

    public abstract void draw(Graphics2D graphics2D);

    public abstract boolean elementAt(Point location);

    public ElementPainter(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }
}
