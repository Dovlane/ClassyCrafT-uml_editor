package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.awt.geom.Rectangle2D;

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
    public static int linesIntersect(double x0, double y0, double x1,
                                     double y1, double x2, double y2, double x3, double y3) {

        int k03_01, k01_02, k20_23, k23_21;
        int pos, neg, nul;

        k03_01 = SIGNTEST((x3 - x0) * (y1 - y0) - (y3 - y0) * (x1 - x0));
        k01_02 = SIGNTEST((x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0));
        k20_23 = SIGNTEST((x0 - x2) * (y3 - y2) - (y0 - y2) * (x3 - x2));
        k23_21 = SIGNTEST((x3 - x2) * (y1 - y2) - (y3 - y2) * (x1 - x2));

        pos = neg = nul = 0;

        if (k03_01 < 0) {
            neg++;
        } else if (k03_01 > 0) {
            pos++;
        } else {
            nul++;
        }

        if (k01_02 < 0) {
            neg++;
        } else if (k01_02 > 0) {
            pos++;
        } else {
            nul++;
        }

        if (k20_23 < 0) {
            neg++;
        } else if (k20_23 > 0) {
            pos++;
        } else {
            nul++;
        }

        if (k23_21 < 0) {
            neg++;
        } else if (k23_21 > 0) {
            pos++;
        } else {
            nul++;
        }

        if (nul == 0) {
            if (neg == 4 || pos == 4) {
                return 1;
            } // legal intersection
            else {
                return 0;
            } // no intersection
        } else {
            if (neg == 3 || pos == 3) {
                return 2;
            } // point on line
            else if (neg == 2 || pos == 2) {
                return 3;
            } // point on point
            else {
                return 4;
            } // line on line
        }
    }

    private static int SIGNTEST(double a) {
        return ((a) > 0.) ? 1 : ((a) < 0.) ? -1 : 0;
    }
}

