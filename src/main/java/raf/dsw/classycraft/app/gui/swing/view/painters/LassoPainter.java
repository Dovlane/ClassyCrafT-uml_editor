package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LassoPainter extends ElementPainter {

    private Point lassoUpperLeft;
    private Point lassoBottomRight;

    public LassoPainter(Point firstPoint, Point secondPoint) {
        super(null);
        setCorners(firstPoint, secondPoint);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int boxWidth = getWidth();
        int boxHeight = getHeight();
        Color lastColor = graphics2D.getColor();
        graphics2D.setColor(new Color(0, 0, 1f));
        graphics2D.drawRect(lassoUpperLeft.x, lassoUpperLeft.y, boxWidth, boxHeight);
        graphics2D.setColor(new Color(0f, 0f, 1f, 0.4f));
        graphics2D.fillRect(lassoUpperLeft.x, lassoUpperLeft.y, boxWidth, boxHeight);
        graphics2D.setColor(lastColor);
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {

    }

    @Override
    public boolean elementAt(Point location) {
        return false;
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

            // If connection is not reflexive
            if (connectionPainter.getPointListOfReflexiveLine() == null) {
                Point from = connectionPainter.getCurrentPointFrom();
                Point to = connectionPainter.getCurrentPointTo();

                int x0 = (int) from.getX();
                int y0 = (int) from.getY();
                int x1 = (int) to.getX();
                int y1 = (int) to.getY();

                return lassoRectangle.intersectsLine(x0, y0, x1, y1);
            } else {
                ArrayList<Point> listOfReflexiveLine = connectionPainter.getPointListOfReflexiveLine();
                boolean intersects = false;
                for (int i = 0; i < listOfReflexiveLine.size() - 1; i++) {
                    Point firstPoint = listOfReflexiveLine.get(i);
                    Point secondPoint = listOfReflexiveLine.get(i + 1);
                    if (lassoRectangle.intersectsLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y)) {
                        intersects = true;
                        break;
                    }
                }
                return intersects;
            }
        }

        return true;
    }

    private void setCorners(Point firstPoint, Point secondPoint) {
        lassoUpperLeft = new Point(
                Math.min(firstPoint.x, secondPoint.x),
                Math.min(firstPoint.y, secondPoint.y)
        );
        lassoBottomRight = new Point(
                Math.max(firstPoint.x, secondPoint.x),
                Math.max(firstPoint.y, secondPoint.y)
        );
    }

    private Rectangle getLassoRectangle() {
        return new Rectangle(lassoUpperLeft.x, lassoUpperLeft.y, getWidth(), getHeight());
    }

    private int getWidth() {
        return lassoBottomRight.x - lassoUpperLeft.x;
    }

    private int getHeight() {
        return lassoBottomRight.y - lassoUpperLeft.y;
    }

}

