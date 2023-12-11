package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainters;

import raf.dsw.classycraft.app.model.elements.Connection.Composition;

import java.awt.*;

public class CompositionPainter extends ConnectionPainter {
    public CompositionPainter(Composition diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Stroke lastStroke = graphics2D.getStroke();
        Color lastColor = graphics2D.getColor();

        graphics2D.setStroke(strokeFullLine);
        getConnection().getBestPairOfStartAndEndPoint();

        if (!getConnection().isReflexiveConnection()) {
            graphics2D.drawLine(getXFrom(), getYFrom(), getXTo(), getYTo());
            drawAlignedRhomboid(graphics2D, getXFrom(), getYFrom(), getXTo(), getYTo(), new Color(0, 0, 0));
        } else {
            drawReflexiveConnection(graphics2D, this);
        }

        graphics2D.setStroke(lastStroke);
        graphics2D.setColor(lastColor);
    }
}
