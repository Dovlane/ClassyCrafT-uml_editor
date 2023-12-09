package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;

import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;
import java.awt.font.LineMetrics;


public class ClassPainter extends InterclassPainter {

    public ClassPainter(ClassElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        adjustBoxHeight(graphics2D, getClassElement().getClassContent().size());
        adjustBoxWidth(graphics2D, getMaxContentStringLength());
        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        for (Attribute attribute : getClassElement().getClassAttributes()) {
            String attributeString = attribute.toString();
            graphics2D.setFont(getInterclassContentFont());
            if (attribute.getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                graphics2D.setFont(getStaticInterclassContentFont());
            }

            float messageHeight = (float) graphics2D.getFont().getStringBounds(attributeString, graphics2D.getFontRenderContext()).getHeight();

            y += messageHeight;
            graphics2D.drawString(attributeString, (int) getX(), y);
        }

        y += 5;
        graphics2D.drawLine((int) getX(), (int) y, (int) (getX() + getBoxWidth()), (int) y);

        for (Method method : getClassElement().getClassMethods()) {
            String methodString = method.toString();
            graphics2D.setFont(getInterclassContentFont());
            if (method.getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                graphics2D.setFont(getStaticInterclassContentFont());
            }
            float messageHeight = (float) graphics2D.getFont().getStringBounds(methodString, graphics2D.getFontRenderContext()).getHeight();

            y += messageHeight;
            graphics2D.drawString(methodString, (int) getX(), y);
        }
    }


    private String getMaxContentStringLength() {
        String maxStringLength = "";
        for (ClassContent classContent : getClassElement().getClassContent()) {
            if (maxStringLength.length() < classContent.toString().length())
                maxStringLength = classContent.toString();
        }
        return maxStringLength;
    }

    @Override
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y) {
        float boxWidth = (float)getBoxWidth();
        String interclassName = ((Interclass)diagramElement).getName();
        if (getClassElement().getNonAccessModifiers() == NonAccessModifiers.ABSTRACT) {
            graphics2D.setFont(abstractInterclassNameFont);
        } else {
            graphics2D.setFont(interclassNameFont);
        }
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

    private ClassElement getClassElement() { return (ClassElement) diagramElement; }

}
