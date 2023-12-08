package raf.dsw.classycraft.app.gui.swing.view.painters;

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
        Point painterUpperLeft = elementPainter.getUpperLeft();
        Point painterBottomRight = elementPainter.getBottomRight();

        // Check if one rectangle is next to the other
        if (lassoBottomRight.x < painterUpperLeft.x || painterBottomRight.x < lassoUpperLeft.x) {
            return false;
        }

        // Check if one rectangle is above the other
        if (lassoBottomRight.y < painterUpperLeft.y || painterBottomRight.y < lassoUpperLeft.y) {
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

    @Override
    public Point getUpperLeft() {
        return lassoUpperLeft;
    }

    @Override
    public Point getBottomRight() {
        return lassoBottomRight;
    }

}
