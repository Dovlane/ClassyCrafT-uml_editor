package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.Attribute;
import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;

import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;


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

        y += padding;
        graphics2D.drawLine((int) getX(), (int) y, (int) (getX() + getBoxWidth()), (int) y);

        for (Method method : getClassElement().getClassMethods()) {
            int size = 10;
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


    private ClassElement getClassElement() { return (ClassElement)diagramElement; }

}
