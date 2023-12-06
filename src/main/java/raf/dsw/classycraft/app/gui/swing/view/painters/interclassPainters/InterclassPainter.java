package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;
import java.util.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;

public class InterclassPainter extends ElementPainter {

    protected static Font interclassNameFont = new Font("Arial", Font.BOLD, 12);
    protected static Font abstractInterclassNameFont = new Font("Arial", Font.BOLD|Font.ITALIC, 12);
    private static Font interclassContentFont = new Font("Arial", Font.PLAIN, 10);
    protected static Font getInterclassContentFont() {
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, -1);
        interclassContentFont = new Font(interclassContentFont.getName(), interclassContentFont.getStyle(), interclassContentFont.getSize()).deriveFont(fontAttributes);
        return interclassContentFont;
    }
    protected static Font getStaticInterclassContentFont() {
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        interclassContentFont = new Font(interclassContentFont.getName(), interclassContentFont.getStyle(), interclassContentFont.getSize()).deriveFont(fontAttributes);
        return interclassContentFont;
    }
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

    @Override
    public void drawSelectionBox(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(255, 0, 0));
        graphics2D.drawRect((int) getX(), (int) getY(), (int) getBoxWidth(), (int) getBoxHeight());
    }

    protected void drawInterclassName(Graphics2D graphics2D, float x, float y){
        float boxWidth = (float)getBoxWidth();
        String interclassName = ((Interclass)diagramElement).getName();
        graphics2D.setFont(interclassNameFont);
        LineMetrics metrics = getLineMetrics(graphics2D, interclassName);
        float messageWidth = (float)graphics2D.getFont().getStringBounds(interclassName, graphics2D.getFontRenderContext()).getWidth();
        float messageHeight = (float)graphics2D.getFont().getStringBounds(interclassName, graphics2D.getFontRenderContext()).getHeight();

        float descent = metrics.getDescent( );
        float x_text = x + (boxWidth - messageWidth) / 2;
        float y_text = y + messageHeight;
        graphics2D.drawString(interclassName, x_text, y_text);
        yBelowInterclassName = y + messageHeight + descent;
        drawInterclassLine(graphics2D, yBelowInterclassName);
    }

    private float interclassNameStringHeight(Graphics2D graphics2D) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassNameFont);
        float methodStringHeight = (float) graphics2D.getFont().getStringBounds("foo", graphics2D.getFontRenderContext()).getHeight();
        graphics2D.setFont(lastFont);
        return methodStringHeight;
    }

    private float interclassNameHeight(Graphics2D graphics2D) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassNameFont);
        LineMetrics metrics = getLineMetrics(graphics2D, "foo");
        graphics2D.setFont(lastFont);
        float descent = metrics.getDescent();
        return interclassNameStringHeight(graphics2D) + descent;
    }

    private float interclassNameWidth(Graphics2D graphics2D) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassNameFont);
        float interclassNameWidth = (float) graphics2D.getFont().getStringBounds(getInterclassElement().getName(), graphics2D.getFontRenderContext()).getWidth();
        graphics2D.setFont(lastFont);
        return interclassNameWidth;
    }

    protected void adjustBoxHeight(Graphics2D graphics2D, int numOfInterclassElements)  {
        float y = lineBelowInterclassNameGetY(graphics2D);
        float interclassContentHeight = getInterclassContentStringHeight(graphics2D);
        float max_y = y  + numOfInterclassElements * interclassContentHeight;

        if (max_y >= getY() + getBoxHeight()) {
            setBoxHeight(max_y + padding - getY());
        }
    }

    protected void adjustBoxWidth(Graphics2D graphics2D, String stringMaxContentLength) {
        float maxTextWidth = Math.max(getInterclassContentStringWidth(graphics2D, stringMaxContentLength), interclassNameWidth(graphics2D));
        if (maxTextWidth >= getBoxWidth()) {
            setBoxWidth(maxTextWidth + padding * 2);
        }
    }

    protected float getInterclassContentStringHeight(Graphics2D graphics2D) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassContentFont);
        float interclassContentStringHeight = (float) graphics2D.getFont().getStringBounds("foo", graphics2D.getFontRenderContext()).getHeight();
        graphics2D.setFont(lastFont);
        return interclassContentStringHeight;
    }

    protected float getInterclassContentStringWidth(Graphics2D graphics2D, String interclassContentString) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassContentFont);
        float interclassContentStringWidth = (float) graphics2D.getFont().getStringBounds(interclassContentString, graphics2D.getFontRenderContext()).getWidth();
        graphics2D.setFont(lastFont);
        return interclassContentStringWidth;
    }

    protected float getInterclassHeaderStringHeight(Graphics2D graphics2D) {
        Font lastFont = graphics2D.getFont();
        graphics2D.setFont(interclassContentFont);
        float textHeight = (float)graphics2D.getFont().getStringBounds("foo", graphics2D.getFontRenderContext()).getHeight();
        graphics2D.setFont(lastFont);
        return textHeight;
    }

    private LineMetrics getLineMetrics(Graphics2D graphics2D, String text) {
        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineMetrics metrics = graphics2D.getFont().getLineMetrics(text, frc);
        return metrics;
    }


    private void drawInterclassLine(Graphics2D graphics2D, double y) {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawLine((int)getX(), (int)y, (int) (getX() + getBoxWidth()), (int)yBelowInterclassName);
    }

    @Override
    public boolean elementAt(Point location) {
        Point upperLeft = ((Interclass) diagramElement).getLocation();
        boolean insideX = ((upperLeft.x <= location.x) &&
                            (location.x <= upperLeft.x + getBoxWidth()));
        boolean insideY = ((upperLeft.y <= location.y) &&
                            (location.y <= upperLeft.y + getBoxHeight()));
        return (insideX && insideY);
    }

    protected double getX() {
        return ((Interclass) diagramElement).getLocation().getX();
    }

    protected double getY() {
        return ((Interclass) diagramElement).getLocation().getY();
    }

    private Interclass getInterclassElement() { return (Interclass) diagramElement; }

    protected void setBoxWidth(double newBoxWidth) { ((Interclass) diagramElement).setBoxWidth((int)newBoxWidth); }

    protected void setBoxHeight(double newBoxWidth) { ((Interclass) diagramElement).setBoxHeight((int)newBoxWidth); }

    protected  double getBoxWidth() { return ((Interclass) diagramElement).getBoxWidth(); }

    protected double getBoxHeight() { return ((Interclass) diagramElement).getBoxHeight(); }

    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) { return (float)(getY() + interclassNameHeight(graphics2D));}

}
