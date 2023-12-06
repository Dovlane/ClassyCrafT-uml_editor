package raf.dsw.classycraft.app.gui.swing.view.painters;

import java.awt.*;

public class LassoPainter extends ElementPainter {

    private Point upperLeft;
    private Point bottomRight;

    public LassoPainter(Point first, Point second) {
        super(null);
        setCorners(first, second);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int boxWidth = bottomRight.x - upperLeft.x;
        int boxHeight = bottomRight.y - upperLeft.y;
        graphics2D.setColor(new Color(0, 0, 1f));
        graphics2D.drawRect(upperLeft.x, upperLeft.y, boxWidth, boxHeight);
        graphics2D.setColor(new Color(0f, 0f, 1f, 0.4f));
        graphics2D.fillRect(upperLeft.x, upperLeft.y, boxWidth, boxHeight);
    }

    @Override
    public boolean elementAt(Point location) {
        return false;
    }

    private void setCorners(Point first, Point second) {
        upperLeft = new Point(
                Math.min(first.x, second.x),
                Math.min(first.y, second.y)
        );
        bottomRight = new Point(
                Math.max(first.x, second.x),
                Math.max(first.y, second.y)
        );
    }
}
