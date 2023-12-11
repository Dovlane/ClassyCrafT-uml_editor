package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class ConnectionPainter extends ElementPainter {

    private static int arrowSize = 14;
    protected static Stroke strokeFullLine = new BasicStroke(2);
    protected static Stroke strokeDashLine = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

    protected static void drawArrowhead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int n = 6;
        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / n));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / n));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / n));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / n));

        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x2, y2, x4, y4);
    }

    protected static void drawTriangleArrowhead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int n = 6;
        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / n));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / n));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / n));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / n));
        Path2D arrowTriangle = new Path2D.Double();
        arrowTriangle.moveTo(x2, y2);
        arrowTriangle.lineTo(x3, y3);
        arrowTriangle.lineTo(x4, y4);
        arrowTriangle.lineTo(x2, y2);

        g2d.setColor(new Color(255, 255, 255));
        g2d.fill(arrowTriangle);
        g2d.setStroke(strokeFullLine);
        g2d.setColor(new Color(0, 0, 0));
        g2d.draw(arrowTriangle);
    }

    protected static void drawAlignedRhomboid(Graphics2D g2d, int xFrom, int yFrom, int xTo, int yTo, Color fillColor) {
        int width = 20;
        int height = 10;
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        double rhomboid_length = height;
        double angle = calculateLineAngle(xFrom, yFrom, xTo, yTo);
        double dx = Math.cos(angle) * rhomboid_length;
        double dy = Math.sin(angle) * rhomboid_length;
        xTo -= dx;
        yTo -= dy;
        Path2D rhomboid = new Path2D.Double();
        rhomboid.moveTo(xTo - halfWidth, yTo);
        rhomboid.lineTo(xTo, yTo - halfHeight);
        rhomboid.lineTo(xTo + halfWidth, yTo);
        rhomboid.lineTo(xTo, yTo + halfHeight);
        rhomboid.closePath();

        AffineTransform rotation = AffineTransform.getRotateInstance(angle, xTo, yTo);
        AffineTransform combined = new AffineTransform();
        combined.concatenate(rotation);

        Shape rotatedRhomboid = combined.createTransformedShape(rhomboid);
        Color lastColor = g2d.getColor();

        g2d.setColor(fillColor);
        g2d.fill(rotatedRhomboid);
        g2d.setPaint(new Color(0, 0, 0));
        g2d.setStroke(strokeFullLine);
        g2d.draw(rotatedRhomboid);
        g2d.setColor(lastColor);
    }

    protected void drawReflexiveConnection(Graphics2D graphics2D, ConnectionPainter connectionPainter) {
        int x = connectionPainter.getCurrentPointFrom().x;
        int y = connectionPainter.getCurrentPointFrom().y;
        int width = connectionPainter.getFromWidth();
        int half_width = width / 2;
        int height = connectionPainter.getFromHeight();
        int half_height = height / 2;
        int offset_around = 25;

        // Draw the main line and add points to list of points of that line. We need that list of points for checking intersection when using Lasso in SelectState
        ArrayList<Point> pointListOfReflexiveLine = new ArrayList<>();

        Path2D reflexiveLineShape = new Path2D.Double();
        reflexiveLineShape.moveTo(x, y + half_height);
        pointListOfReflexiveLine.add(new Point(x, y + half_height));
        reflexiveLineShape.lineTo(x - offset_around, y + half_height);
        pointListOfReflexiveLine.add(new Point(x - offset_around, y + half_height));
        reflexiveLineShape.lineTo(x - offset_around, y - offset_around);
        pointListOfReflexiveLine.add(new Point(x - offset_around, y - offset_around));

        // Here is logic how to avoid issue when there are more reflexive connections
        int horisontalOffset = 20;
        Point endingPoint = new Point(x + half_width, y - offset_around);
        if (connectionPainter instanceof AgregationPainter)
            endingPoint.setLocation(new Point(endingPoint.x - horisontalOffset / 2 * 3, endingPoint.y));
        else if (connectionPainter instanceof CompositionPainter)
            endingPoint.setLocation(new Point(endingPoint.x, endingPoint.y));
        else if (connectionPainter instanceof DependencyPainter)
            endingPoint.setLocation(new Point(endingPoint.x + horisontalOffset / 2 * 3, endingPoint.y));

        reflexiveLineShape.lineTo(endingPoint.x, endingPoint.y);
        pointListOfReflexiveLine.add(endingPoint);
        reflexiveLineShape.lineTo(endingPoint.x, y);
        pointListOfReflexiveLine.add(new Point(endingPoint.x, y));

        graphics2D.draw(reflexiveLineShape);

        connectionPainter.setPointListOfReflexiveLine(pointListOfReflexiveLine);

        if (connectionPainter instanceof AgregationPainter)
            drawAlignedRhomboid(graphics2D, endingPoint.x, endingPoint.y, endingPoint.x, y, new Color(255, 255, 255));
        else if (connectionPainter instanceof DependencyPainter)
            drawArrowhead(graphics2D, endingPoint.x, endingPoint.y, endingPoint.x, y);
        else if (connectionPainter instanceof CompositionPainter)
            drawAlignedRhomboid(graphics2D, endingPoint.x, endingPoint.y, endingPoint.x, y, new Color(0, 0, 0));
    }

    private static void drawSelectionBoxForReflexiveLine(Graphics2D graphics2D, ArrayList<Point> pointListOfReflexiveLine) {
        Point startingPoint = pointListOfReflexiveLine.get(0);
        Point lastPoint = pointListOfReflexiveLine.get(pointListOfReflexiveLine.size() - 1);

        Path2D selectionBoxForReflexiveLineShape = new Path2D.Double();
        selectionBoxForReflexiveLineShape.moveTo(startingPoint.x, startingPoint.y);

        for (int i = 1; i < pointListOfReflexiveLine.size(); i++) {
            Point currentPoint = pointListOfReflexiveLine.get(i);
            selectionBoxForReflexiveLineShape.lineTo(currentPoint.x, currentPoint.y);
        }

        selectionBoxForReflexiveLineShape.lineTo(startingPoint.x, lastPoint. y);
        selectionBoxForReflexiveLineShape.closePath();

        Color lastColor = graphics2D.getColor();
        graphics2D.setStroke(strokeFullLine);
        graphics2D.setColor(new Color(255, 0, 0));
        graphics2D.draw(selectionBoxForReflexiveLineShape);
    }

    private static double calculateLineAngle(int startX, int startY, int endX, int endY) {
        return Math.atan2(endY - startY, endX - startX);
    }

    private ArrayList<Point> pointListOfReflexiveLine = null;

    public ConnectionPainter(Connection diagramElement) {
        super(diagramElement);
    }
    @Override
    public void draw(Graphics2D graphics2D) {
        getConnection().getBestPairOfStartAndEndPoint();
        graphics2D.drawLine(getXFrom(), getYFrom(), getXTo(), getYTo());
    }

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(255, 0, 0));
        if (!getConnection().isReflexiveConnection()) {
            graphics2D.drawRect(getUpperLeftX(), getUpperLeftY(), getBoxWidth(), getBoxHeight());
        } else {
            drawSelectionBoxForReflexiveLine(graphics2D, pointListOfReflexiveLine);
        }
    }

    @Override
    public boolean elementAt(Point location) {
        int x_clicked = location.x;
        int y_clicked = location.y;
        int half_dim = 3;
        Rectangle rectangle = new Rectangle(x_clicked - half_dim, y_clicked - half_dim, half_dim * 2, half_dim * 2);

        if (!getConnection().isReflexiveConnection()) {
            return rectangle.intersectsLine(getXFrom(), getYFrom(), getXTo(), getYTo());
        } else {
            boolean intersects = false;
            for (int i = 0; i < pointListOfReflexiveLine.size() - 1; i++) {
                Point firstPoint = pointListOfReflexiveLine.get(i);
                Point secondPoint = pointListOfReflexiveLine.get(i + 1);
                if (rectangle.intersectsLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y)) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        }
    }

    public ArrayList<Point> getPointListOfReflexiveLine() {
        if (getConnection().isReflexiveConnection())
            return pointListOfReflexiveLine;
        return null;
    }

    public void setPointListOfReflexiveLine(ArrayList pointListOfReflexiveLine) {
        this.pointListOfReflexiveLine = pointListOfReflexiveLine;
    }

    protected Connection getConnection() {
        Connection connection = (Connection) diagramElement;
        return connection;
    }

    protected int getXTo() {
        return getConnection().getCurrentPointTo().x;
    }

    protected int getXFrom() {
        return getConnection().getCurrentPointFrom().x;
    }

    protected int getYTo() {
        return getConnection().getCurrentPointTo().y;
    }

    protected int getYFrom() {
        return getConnection().getCurrentPointFrom().y;
    }

    private int getUpperLeftX() {
        return Math.min(getXFrom(), getXTo());
    }

    private int getUpperLeftY() {
        return Math.min(getYFrom(), getYTo());
    }

    protected int getBoxWidth() {
        return Math.abs(getXTo() - getXFrom());
    }

    private int getBoxHeight() {
        return Math.abs(getYTo() - getYFrom());
    }

    public Point getCurrentPointFrom() {
        return getConnection().getCurrentPointFrom();
    }

    public Point getCurrentPointTo() {
        return getConnection().getCurrentPointTo();
    }

    protected int getFromWidth() { return getConnection().getFrom().getBoxWidth(); }

    protected int getFromHeight() { return getConnection().getFrom().getBoxHeight(); }

}
