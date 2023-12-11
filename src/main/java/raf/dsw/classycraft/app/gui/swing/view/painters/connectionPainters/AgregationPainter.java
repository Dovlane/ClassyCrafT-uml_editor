package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.model.elements.Connection.Aggregation;

import java.awt.*;

public class AgregationPainter extends ConnectionPainter {
    public AgregationPainter(Aggregation diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Stroke lastStroke = graphics2D.getStroke();

        getConnection().getBestPairOfStartAndEndPoint();
        graphics2D.setStroke(strokeFullLine);

        if (!getConnection().isReflexiveConnection()) {
            graphics2D.drawLine(getXFrom(), getYFrom(), getXTo(), getYTo());
            drawAlignedRhomboid(graphics2D, getXFrom(), getYFrom(), getXTo(), getYTo(), graphics2D.getBackground());
        } else {
            drawReflexiveConnection(graphics2D, this);
        }

        graphics2D.setStroke(lastStroke);
    }

}


