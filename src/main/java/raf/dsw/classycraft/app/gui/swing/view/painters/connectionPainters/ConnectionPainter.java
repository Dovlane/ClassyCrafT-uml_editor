package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class ConnectionPainter extends ElementPainter {

    protected static void drawArrowhead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }

    protected static void drawAlignedRhomboid(Graphics2D g2d, int x, int y, int width, int height, double angle) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        Path2D rhomboid = new Path2D.Double();
        double rhomboid_length = height;
        double dx = Math.cos(angle) * rhomboid_length;
        double dy = Math.sin(angle) * rhomboid_length;
        x -= dx;
        y -= dy;
        rhomboid.moveTo(x - halfWidth, y);
        rhomboid.lineTo(x, y - halfHeight);
        rhomboid.lineTo(x + halfWidth, y);
        rhomboid.lineTo(x, y + halfHeight);
        rhomboid.closePath();

        AffineTransform rotation = AffineTransform.getRotateInstance(angle, x, y);
        AffineTransform combined = new AffineTransform();
        combined.concatenate(rotation);

        Shape rotatedRhomboid = combined.createTransformedShape(rhomboid);
        Color lastColor = g2d.getColor();
        g2d.setColor(g2d.getBackground());

        g2d.fill(rotatedRhomboid);
        g2d.setPaint(new Color(0, 0, 0));
        g2d.draw(rotatedRhomboid);
        g2d.setColor(lastColor);
    }

    protected static double calculateLineAngle(int startX, int startY, int endX, int endY) {
        return Math.atan2(endY - startY, endX - startX);
    }

    protected Point currentPointFrom;
    protected Point currentPointTo;
    public ConnectionPainter(Connection diagramElement) {
        super(diagramElement);
    }
    @Override
    public void draw(Graphics2D graphics2D) {
        Point[] pointsFromTo = getBestPairOfStartAndEndPoint();
        currentPointFrom = pointsFromTo[0];
        currentPointTo = pointsFromTo[1];
        int xFrom = (int)currentPointFrom.getX();
        int yFrom = (int)currentPointFrom.getY();
        int xTo = (int)currentPointTo.getX();
        int yTo = (int)currentPointTo.getY();

        graphics2D.drawLine(xFrom, yFrom, xTo, yTo);
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(255, 0, 0));

        graphics2D.drawRect(getX(), getY(), getBoxWidth(), getBoxHeight());
    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    private Connection getConnection() {
        Connection connection = (Connection) diagramElement;
        return connection;
    }

    private Point[] getConnectionAttachingPointsOfFrom() {
        return getConnection().getFrom().getConnectionAttachingPoints();
    }

    private Point[] getConnectionAttachingPointsOfTo() {
        return getConnection().getTo().getConnectionAttachingPoints();
    }

    protected Point[] getBestPairOfStartAndEndPoint() {
        Point bestFromPoint = getConnectionAttachingPointsOfFrom()[0];
        Point bestToPoint = getConnectionAttachingPointsOfTo()[0];
        double minDistance = distanceBetween(bestFromPoint, bestToPoint);

        for (int i = 0; i < getConnectionAttachingPointsOfFrom().length; i++) {
            for (int j = 1; j < getConnectionAttachingPointsOfTo().length; j++) {
                Point fromPoint = getConnectionAttachingPointsOfFrom()[i];
                Point toPoint = getConnectionAttachingPointsOfTo()[j];
                double distance = distanceBetween(fromPoint, toPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestFromPoint = fromPoint;
                    bestToPoint = toPoint;
                }
            }
        }

        return new Point[] {bestFromPoint, bestToPoint};
    }

    private static double distanceBetween(Point fromPoint, Point toPoint) {
        return Point.distance(fromPoint.getX(), fromPoint.getY(), toPoint.getX(), toPoint.getY());
    }

    private int getX() {
        return Math.min((int)currentPointFrom.getX(), (int)currentPointTo.getX());
    }

    private int getY() {
        return Math.min((int)currentPointFrom.getY(), (int)currentPointTo.getY());
    }

    private int getBoxWidth() {
        return Math.abs((int)(currentPointFrom.getX() - currentPointTo.getX()));
    }

    private int getBoxHeight() {
        return Math.abs((int)(currentPointFrom.getY() - currentPointTo.getY()));
    }

    public Point getCurrentPointFrom() {
        return currentPointFrom;
    }

    public Point getCurrentPointTo() {
        return currentPointTo;
    }
}
