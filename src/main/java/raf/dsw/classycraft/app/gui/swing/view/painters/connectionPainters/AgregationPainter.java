package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class AgregationPainter extends ConnectionPainter {
    public AgregationPainter(Aggregation diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Stroke lastStroke = graphics2D.getStroke();
        Color lastColor = graphics2D.getColor();

        float[] dashPattern = {5, 5};
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{9}, 0);

        Point[] pointsFromTo = getBestPairOfStartAndEndPoint();
        currentPointFrom = pointsFromTo[0];
        currentPointTo = pointsFromTo[1];
        int xFrom = (int)currentPointFrom.getX();
        int yFrom = (int)currentPointFrom.getY();
        int xTo = (int)currentPointTo.getX();
        int yTo = (int)currentPointTo.getY();
        graphics2D.setStroke(dashed);
        int width = 20;
        int height = 10;
        double angle = calculateLineAngle(xFrom, yFrom, xTo, yTo);

        graphics2D.drawLine(xFrom, yFrom, xTo, yTo);

        graphics2D.setStroke(new BasicStroke(2));
        drawAlignedRhomboid(graphics2D, xTo, yTo, 20, 10, angle);

        graphics2D.setStroke(lastStroke);
        graphics2D.setColor(lastColor);
    }


}


