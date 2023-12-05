package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class InterfacePainter extends InterclassPainter {
    public InterfacePainter(InterfaceElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        adjustingBoxHeight(graphics2D);

        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        float methodStringHeight = methodsStringHeight(graphics2D);
        for (Method method : getInterfaceElement().getInterfaceMethods()) {
            int size = 10;
            String methodString = method.toString();
            graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
            if (method.getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                graphics2D.setFont(new Font(graphics2D.getFont().getName(), graphics2D.getFont().getStyle(), size).deriveFont(fontAttributes));
            }
            LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, methodString);

            y += methodStringHeight;
            graphics2D.drawString(methodString, (int) getX(), y);
        }
    }

    @Override
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y) {
        int size = 10;
        graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
        String interfaceString = "<<Interface>>";
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, interfaceString);
        float textWidth = (float)graphics2D.getFont().getStringBounds(interfaceString, graphics2D.getFontRenderContext()).getWidth();
        float textHeight = (float)graphics2D.getFont().getStringBounds(interfaceString, graphics2D.getFontRenderContext()).getHeight();

        float ascent = metrics.getAscent( );
        float descent = metrics.getDescent( );
        float x_text = x + (float) (getBoxWidth() - textWidth) / 2;
        float y_text = y + textHeight;
        graphics2D.drawString(interfaceString, x_text, y_text);

        y += textHeight;
        super.drawInterclassName(graphics2D, x, y);
    }

    private void adjustingBoxHeight(Graphics2D graphics2D) {
        float y = lineBelowInterclassNameGetY(graphics2D);
        float methodStringHeight = methodsStringHeight(graphics2D);
        float max_y = y + getInterfaceElement().getInterfaceMethods().size() * methodStringHeight;

        if (max_y >= getY() + getBoxHeight()) {
            setBoxHeight(max_y + padding - getY());
        }
    }

    @Override
    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) {
        return super.lineBelowInterclassNameGetY(graphics2D) + interfaceHeaderStringHeight(graphics2D);
    }

    private float methodsStringHeight(Graphics2D graphics2D) {
        int size = 10;
        String foo = "foo";
        String methodString = foo.toString();
        Font font = new Font("Arial", Font.PLAIN, size);
        LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, methodString);
        float methodStringHeight = (float) font.getStringBounds(methodString, graphics2D.getFontRenderContext()).getHeight();

        return methodStringHeight;
    }

    private float interfaceHeaderStringHeight(Graphics2D graphics2D) {
        int size = 10;
        Font font = new Font("Arial", Font.PLAIN, size);
        String interfaceString = "<<Interface>>";
        float textHeight = (float)font.getStringBounds("foo", graphics2D.getFontRenderContext()).getHeight();
        return textHeight;
    }
    private InterfaceElement getInterfaceElement() { return (InterfaceElement) diagramElement; }
}
