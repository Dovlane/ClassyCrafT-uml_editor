package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class ClassPainter extends InterclassPainter {
    public ClassPainter(ClassElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {


        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        for (Attribute attribute : getClassElement().getClassAttributes()) {
            int size = 10;
            String attributeString = attribute.toString();
            graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
            if (attribute.getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                graphics2D.setFont(new Font(graphics2D.getFont().getName(), graphics2D.getFont().getStyle(), size).deriveFont(fontAttributes));
            }

            LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, attributeString);
            float messageHeight = (float) graphics2D.getFont().getStringBounds(attributeString, graphics2D.getFontRenderContext()).getHeight();

            y += messageHeight;
            graphics2D.drawString(attributeString, (int) getX(), y);
        }

        y += padding;
        graphics2D.drawLine((int) getX(), (int) y, (int) (getX() + getBoxWidth()), (int) y);

        for (Method method : getClassElement().getClassMethods()) {
            int size = 10;
            String methodString = method.toString();
            graphics2D.setFont(new Font("Arial", Font.PLAIN, size));
            if (method.getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                graphics2D.setFont(new Font(graphics2D.getFont().getName(), graphics2D.getFont().getStyle(), size).deriveFont(fontAttributes));
            }
            LineMetrics metrics = setFontSizeAndFlagAndGetMetrics(graphics2D, methodString);
            float messageHeight = (float) graphics2D.getFont().getStringBounds(methodString, graphics2D.getFontRenderContext()).getHeight();

            y += messageHeight;
            graphics2D.drawString(methodString, (int) getX(), y);
        }
    }
    private ClassElement getClassElement() { return (ClassElement)diagramElement; }

}
