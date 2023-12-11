package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.EnumLiteral;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;

import java.awt.*;

public class EnumPainter extends InterclassPainter {
    public EnumPainter(EnumElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        adjustBoxHeight(graphics2D, getEnumElement().getEnumLiterals().size());
        adjustBoxWidth(graphics2D, getMaxContentStringLength());
        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        graphics2D.setFont(getInterclassContentFont());

        for (EnumLiteral enumLiteral : getEnumElement().getEnumLiterals()) {
            String enumLiteralStringString = enumLiteral.toString();
            float messageHeight = (float) graphics2D.getFont().getStringBounds(enumLiteralStringString, graphics2D.getFontRenderContext()).getHeight();
            y += messageHeight;
            graphics2D.drawString(enumLiteralStringString, (int) getX(), y);
        }
    }

    @Override
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y) {
        graphics2D.setFont(getInterclassContentFont());
        String enumString = "<<enumeration>>";

        float textWidth = (float)graphics2D.getFont().getStringBounds(enumString, graphics2D.getFontRenderContext()).getWidth();
        float textHeight = (float)graphics2D.getFont().getStringBounds(enumString, graphics2D.getFontRenderContext()).getHeight();

        float x_text = x + (float) (getBoxWidth() - textWidth) / 2;
        float y_text = y + textHeight;
        graphics2D.drawString(enumString, x_text, y_text);

        y += textHeight;
        super.drawInterclassName(graphics2D, x, y);
    }

    private String getMaxContentStringLength() {
        String maxStringLength = "";
        for (EnumLiteral enumLiteral : getEnumElement().getEnumLiterals()) {
            if (maxStringLength.length() < enumLiteral.toString().length())
                maxStringLength = enumLiteral.toString();
        }
        return maxStringLength;
    }
    @Override
    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) {
        return super.lineBelowInterclassNameGetY(graphics2D) + getInterclassHeaderStringHeight(graphics2D);
    }
    private EnumElement getEnumElement() {
        return (EnumElement) diagramElement;
    }
}
