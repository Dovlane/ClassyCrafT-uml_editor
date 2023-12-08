package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.elements.LassoElement;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LassoPainter extends ElementPainter {

    public LassoPainter(LassoElement lassoElement) {
        super(lassoElement);
    }

    public boolean intersectsWith(ElementPainter elementPainter) {
        if (elementPainter instanceof InterclassPainter) {
            InterclassPainter interclassPainter = (InterclassPainter) elementPainter;
            Point painterUpperLeft = interclassPainter.getUpperLeft();
            Point painterBottomRight = interclassPainter.getBottomRight();

            // Check if one rectangle is next to the other
            if (getLassoBottomRight().x < painterUpperLeft.x || painterBottomRight.x < getLassoUpperLeft().x) {
                return false;
            }

            // Check if one rectangle is above the other
            if (getLassoBottomRight().y < painterUpperLeft.y || painterBottomRight.y < getLassoUpperLeft().y) {
                return false;
            }
        }
        else if (elementPainter instanceof  ConnectionPainter) {
            ConnectionPainter connectionPainter = (ConnectionPainter) elementPainter;
            Rectangle lassoRectangle = getLassoRectangle();
            if (!connectionIntersectsLasso(connectionPainter, lassoRectangle))
                return false;
        }
        // Rectangles overlap
        return true;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int boxWidth = getWidth();
        int boxHeight = getHeight();
        Color lastColor = graphics2D.getColor();
        graphics2D.setColor(new Color(0, 0, 1f));
        graphics2D.drawRect(getLassoUpperLeft().x, getLassoUpperLeft().y, boxWidth, boxHeight);
        graphics2D.setColor(new Color(0f, 0f, 1f, 0.4f));
        graphics2D.fillRect(getLassoUpperLeft().x, getLassoUpperLeft().y, boxWidth, boxHeight);
        graphics2D.setColor(lastColor);
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    private LassoElement getLassoElement() {return (LassoElement) diagramElement;}

    private Point getLassoUpperLeft() { return getLassoElement().getLassoUpperLeft(); }

    private Point getLassoBottomRight() { return getLassoElement().getLassoBottomRight(); }

    private int getWidth() {
        return getLassoBottomRight().x - getLassoUpperLeft().x;
    }

    private int getHeight() {
        return getLassoBottomRight().y - getLassoUpperLeft().y;
    }

    private Rectangle getLassoRectangle() {
        return new Rectangle(getLassoUpperLeft().x, getLassoUpperLeft().y, getWidth(), getHeight());
    }

    public static boolean connectionIntersectsLasso(ConnectionPainter connectionPainter, Rectangle  r) {

        Point from = connectionPainter.getCurrentPointFrom();
        Point to = connectionPainter.getCurrentPointTo();
        int x0 = (int)from.getX();
        int y0 = (int)from.getY();
        int x1 = (int)to.getX();
        int y1 = (int)to.getY();

        boolean intersection = r.intersectsLine(x0, y0, x1, y1);
        if (intersection) {
            return true;
        }
        
        return false;
    }
}

