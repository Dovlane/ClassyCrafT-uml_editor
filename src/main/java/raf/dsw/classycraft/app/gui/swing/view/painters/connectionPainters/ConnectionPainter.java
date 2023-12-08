package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ConnectionPainter extends ElementPainter {
    private Point currentPointFrom;
    private Point currentPointTo;
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

        Stroke previousStroke = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine(xFrom, yFrom, xTo, yTo);
        graphics2D.setStroke(previousStroke);
    }

    @Override
    public void draw(Graphics2D graphics2D, AffineTransform transform) {

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

    private Point[] getBestPairOfStartAndEndPoint() {
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
