package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.model.elements.Connection.Generalization;

import java.awt.*;

public class GeneralisationPainter extends ConnectionPainter {
    public GeneralisationPainter(Generalization diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Stroke lastStroke = graphics2D.getStroke();

        getConnection().getBestPairOfStartAndEndPoint();
        graphics2D.setStroke(strokeFullLine);

        if (!getConnection().isReflexiveConnection()) {
            graphics2D.drawLine(getXFrom(), getYFrom(), getXTo(), getYTo());
            drawTriangleArrowhead(graphics2D, getXFrom(), getYFrom(), getXTo(), getYTo());
        } else {
            drawReflexiveConnection(graphics2D, this);
        }

        graphics2D.setStroke(lastStroke);
    }
}
