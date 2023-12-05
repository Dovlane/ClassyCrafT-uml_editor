package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class EnumPainter extends InterclassPainter {
    public EnumPainter(EnumElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        adjustingBoxHeight(graphics2D);

        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        for (EnumLiteral enumLiteral : getEnumElement().getEnumLiterals()) {
            int size = 10;
            String enumLiteralStringString = enumLiteral.toString();
            graphics2D.setFont(new Font("Arial", Font.PLAIN, size));

            LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, enumLiteralStringString);
            float messageHeight = (float) graphics2D.getFont().getStringBounds(enumLiteralStringString, graphics2D.getFontRenderContext()).getHeight();

            y += messageHeight;
            graphics2D.drawString(enumLiteralStringString, (int) getX(), y);
        }
    }

    @Override
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y) {
        int size = 10;
        graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
        String enumString = "<<enumeration>>";
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, "<<enumeration>>");
        float textWidth = (float)graphics2D.getFont().getStringBounds(enumString, graphics2D.getFontRenderContext()).getWidth();
        float textHeight = (float)graphics2D.getFont().getStringBounds(enumString, graphics2D.getFontRenderContext()).getHeight();

        float ascent = metrics.getAscent( );
        float descent = metrics.getDescent( );
        float x_text = x + (float) (getBoxWidth() - textWidth) / 2;
        float y_text = y + textHeight;
        graphics2D.drawString(enumString, x_text, y_text);

        y += textHeight;
        super.drawInterclassName(graphics2D, x, y);
    }

    private float enumHeaderStringHeight(Graphics2D graphics2D) {
        int size = 10;
        Font font = new Font("Arial", Font.PLAIN, size);
        float textHeight = (float)font.getStringBounds("foo", graphics2D.getFontRenderContext()).getHeight();
        return textHeight;
    }

    private float enumLiteralStringHeight(Graphics2D graphics2D) {
        int size = 10;
        String foo = "foo";
        String methodString = foo.toString();
        Font font = new Font("Arial", Font.PLAIN, size);
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, methodString);
        float methodStringHeight = (float) font.getStringBounds(methodString, graphics2D.getFontRenderContext()).getHeight();

        return methodStringHeight;
    }

    private void adjustingBoxHeight(Graphics2D graphics2D) {
        float y = lineBelowInterclassNameGetY(graphics2D);
        float methodStringHeight = enumLiteralStringHeight(graphics2D);

        float max_y = y + getEnumElement().getEnumLiterals().size() * methodStringHeight;
        if (max_y >= getY() + getBoxHeight()) {
            setBoxHeight(max_y + padding - getY());
        }
    }
    @Override
    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) {
        return super.lineBelowInterclassNameGetY(graphics2D) + enumHeaderStringHeight(graphics2D);
    }
    private EnumElement getEnumElement() {
        return (EnumElement) diagramElement;
    }
}
