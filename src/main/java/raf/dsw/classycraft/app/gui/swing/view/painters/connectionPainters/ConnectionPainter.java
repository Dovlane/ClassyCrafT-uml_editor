package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ConnectionPainter extends ElementPainter {

    public ConnectionPainter(Connection diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {

    }

    @Override
    public void draw(Graphics2D graphics2D, AffineTransform transform) {

    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    @Override
    public Point getUpperLeft() {
        // TODO: Find boundary for connection
        return null;
    }

    @Override
    public Point getBottomRight() {
        // TODO: Find boundary for connection
        return null;
    }
}
