package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LassoPainter extends ElementPainter {

    private Point lassoUpperLeft;
    private Point lassoBottomRight;

    public LassoPainter(Point first, Point second) {
        super(null);
        setCorners(first, second);
    }

    public boolean intersectsWith(ElementPainter elementPainter) {
        if (elementPainter instanceof InterclassPainter) {
            InterclassPainter interclassPainter = (InterclassPainter) elementPainter;
            Point painterUpperLeft = interclassPainter.getUpperLeft();
            Point painterBottomRight = interclassPainter.getBottomRight();

            // Check if one rectangle is next to the other
            if (lassoBottomRight.x < painterUpperLeft.x || painterBottomRight.x < lassoUpperLeft.x) {
                return false;
            }

            // Check if one rectangle is above the other
            if (lassoBottomRight.y < painterUpperLeft.y || painterBottomRight.y < lassoUpperLeft.y) {
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
        int boxWidth = lassoBottomRight.x - lassoUpperLeft.x;
        int boxHeight = lassoBottomRight.y - lassoUpperLeft.y;
        graphics2D.setColor(new Color(0, 0, 1f));
        graphics2D.drawRect(lassoUpperLeft.x, lassoUpperLeft.y, boxWidth, boxHeight);
        graphics2D.setColor(new Color(0f, 0f, 1f, 0.4f));
        graphics2D.fillRect(lassoUpperLeft.x, lassoUpperLeft.y, boxWidth, boxHeight);
    }

    @Override
    public void draw(Graphics2D graphics2D, AffineTransform transform) {
        int boxWidth = lassoBottomRight.x - lassoUpperLeft.x;
        int boxHeight = lassoBottomRight.y - lassoUpperLeft.y;
        graphics2D.setColor(new Color(0, 0, 1f));
        graphics2D.drawRect(
                lassoUpperLeft.x,
                lassoUpperLeft.y,
                boxWidth, boxHeight);
        graphics2D.setColor(new Color(0f, 0f, 1f, 0.4f));
        graphics2D.fillRect(
                lassoUpperLeft.x,
                lassoUpperLeft.y,
                boxWidth, boxHeight);
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    private void setCorners(Point first, Point second) {
        lassoUpperLeft = new Point(
                Math.min(first.x, second.x),
                Math.min(first.y, second.y)
        );
        lassoBottomRight = new Point(
                Math.max(first.x, second.x),
                Math.max(first.y, second.y)
        );
    }
    private int getWidth() {
        return lassoBottomRight.x - lassoUpperLeft.x;
    }

    private int getHeight() {
        return lassoBottomRight.y - lassoUpperLeft.y;
    }

    private Rectangle getLassoRectangle() {
        return new Rectangle(lassoUpperLeft.x, lassoUpperLeft.y, getWidth(), getHeight());
    }

    public static boolean connectionIntersectsLasso(ConnectionPainter connectionPainter, Rectangle  r) {

        Point from = connectionPainter.getCurrentPointFrom();
        Point to = connectionPainter.getCurrentPointTo();
        int x0 = (int)from.getX();
        int y0 = (int)from.getY();
        int x1 = (int)to.getX();
        int y1 = (int)to.getY();
        // use the Rectangle2D.intersectsLine() method.
        boolean intersection = r.intersectsLine(x0, y0, x1, y1);
        if (intersection) {
            return true;
        }
        
        return false;
    }
}

