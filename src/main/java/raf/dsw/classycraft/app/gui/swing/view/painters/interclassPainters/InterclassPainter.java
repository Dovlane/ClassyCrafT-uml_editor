package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class InterclassPainter extends ElementPainter {
    protected static int padding = 5;
    protected float yBelowInterclassName;
    public InterclassPainter(Interclass diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        drawBox(graphics2D);
        drawInterclassName(graphics2D, (float) getX(), (float) getY());
    }
    private void drawBox(Graphics2D graphics2D){
        double x = getX();
        double y = getY();
        double boxWidth = getBoxWidth();
        double boxHeight = getBoxHeight();
        graphics2D.setColor(new Color(120, 221, 242));
        graphics2D.fillRect((int)x, (int)y, (int)boxWidth, (int)boxHeight);
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawRect((int)x, (int)y, (int)boxWidth, (int)boxHeight);
    }
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y){
        float boxWidth = (float)getBoxWidth();
        String interclassName = ((Interclass)diagramElement).getName();
        int size = 12;
        graphics2D.setFont(new Font("Arial", Font.BOLD, size));
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, interclassName);
        float messageWidth = (float)graphics2D.getFont().getStringBounds(interclassName, graphics2D.getFontRenderContext()).getWidth();
        float messageHeight = (float)graphics2D.getFont().getStringBounds(interclassName, graphics2D.getFontRenderContext()).getHeight();

        //float ascent = metrics.getAscent( );
        float descent = metrics.getDescent( );
        float x_text = x + (boxWidth - messageWidth) / 2;
        float y_text = y + messageHeight;
        graphics2D.drawString(interclassName, x_text, y_text);
        yBelowInterclassName = y + messageHeight + descent;
        drawInterclassLine(graphics2D, yBelowInterclassName);
//        graphics2D.setColor(new Color(0, 0, 0));
//        graphics2D.drawLine((int)x_line_below_text, (int)y_line_below_text, (int)x_line_below_text + boxWidth, (int)y_line_below_text);
    }

    private float interclassNameStringHeight(Graphics2D graphics2D) {
        int size = 10;
        String foo = "foo";
        String methodString = foo.toString();
        graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
        float methodStringHeight = (float) graphics2D.getFont().getStringBounds(methodString, graphics2D.getFontRenderContext()).getHeight();

        return methodStringHeight;
    }

    private float interclassNameHeight(Graphics2D graphics2D) {
        String foo = "foo";
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, foo);
        float descent = metrics.getDescent();
        return interclassNameStringHeight(graphics2D) + descent;
    }


    protected LineMetrics setFontSizeAndFlagAndGetMetrics(Graphics2D graphics2D, String text) {
        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineMetrics metrics = graphics2D.getFont().getLineMetrics(text, frc);
        return metrics;
    }


    protected void drawInterclassLine(Graphics2D graphics2D, double y) {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawLine((int)getX(), (int)y, (int) (getX() + getBoxWidth()), (int)yBelowInterclassName);
    }

    @Override
    public boolean elementAt(int eX, int eY) {
        return false;
    }

    protected double getX() {
        return ((Interclass) diagramElement).getPoint().getX();
    }

    protected double getY() {
        return ((Interclass) diagramElement).getPoint().getY();
    }

    protected void setBoxWidth(double newBoxWidth) { ((Interclass) diagramElement).setBoxWidth((int)newBoxWidth); }

    protected void setBoxHeight(double newBoxWidth) { ((Interclass) diagramElement).setBoxHeight((int)newBoxWidth); }

    protected  double getBoxWidth() { return ((Interclass) diagramElement).getBoxWidth(); }

    protected double getBoxHeight() { return ((Interclass) diagramElement).getBoxHeight(); }

    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) { return (float)(getY() + interclassNameHeight(graphics2D));}
}
