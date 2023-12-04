package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class InterclassPainter extends ElementPainter {
    protected int x, y;
    protected int boxWidth, boxHeight;
    public InterclassPainter(Interclass diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D, int eX, int eY) {
        this.boxWidth = 300;
        this.boxHeight = 100;
        drawBox(graphics2D, eX, eY, boxWidth, boxHeight);
        String interclassName = "Interclass name";
        drawInterclassName(graphics2D, interclassName, eX, eY, boxWidth);
    }
    private void drawBox(Graphics2D graphics2D, int x, int y, int boxWidth, int boxHeight){
        graphics2D.setColor(new Color(120, 221, 242));
        graphics2D.fillRect(x, y, boxWidth, boxHeight);
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawRect(x, y, boxWidth, boxHeight);
    }
    private void drawInterclassName(Graphics2D graphics2D, String interclassName, int x, int y, int boxWidth){
        int size = 12;
        Font font = new Font("Arial", Font.BOLD, size);
        graphics2D.setFont(font);
        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineMetrics metrics = font.getLineMetrics(interclassName, frc);
        float messageWidth =
                (float)font.getStringBounds(interclassName, frc).getWidth();
        float messageHeight =
                (float)font.getStringBounds(interclassName, frc).getHeight();
//        float ascent = metrics.getAscent( );
        float descent = metrics.getDescent( );
        float x_text = x + (boxWidth - messageWidth) / 2;
        float y_text = y + messageHeight;
        graphics2D.drawString(interclassName, x_text, y_text);

        float y_line_below_text = y + messageHeight + descent;
        float x_line_below_text = x;
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawLine((int)x_line_below_text, (int)y_line_below_text, (int)x_line_below_text + boxWidth, (int)y_line_below_text);
    }

    @Override
    public boolean elementAt(int eX, int eY) {
        return false;
    }
}
