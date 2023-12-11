package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters;

import raf.dsw.classycraft.app.model.elements.ClassContent.ClassContent;
import raf.dsw.classycraft.app.model.elements.ClassContent.Method;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.NonAccessModifiers;

import java.awt.*;

public class InterfacePainter extends InterclassPainter {
    
    public InterfacePainter(InterfaceElement diagramElement) {
        super(diagramElement);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        adjustBoxHeight(graphics2D, getInterfaceElement().getInterfaceMethods().size());
        adjustBoxWidth(graphics2D, getMaxContentStringLength());
        super.draw(graphics2D);

        float y = lineBelowInterclassNameGetY(graphics2D);
        float methodStringHeight = getInterclassContentStringHeight(graphics2D);
        for (ClassContent method : getInterfaceElement().getInterfaceMethods()) {
            String methodString = method.toString();
            graphics2D.setFont(getInterclassContentFont());
            if (((Method)method).getNonAccessModifiers() == NonAccessModifiers.STATIC) {
                graphics2D.setFont(getStaticInterclassContentFont());
            }

            y += methodStringHeight;
            graphics2D.drawString(methodString, (int) getX(), y);
        }
    }

    @Override
    protected void drawInterclassName(Graphics2D graphics2D, float x, float y) {
        graphics2D.setFont(getInterclassContentFont());
        String interfaceString = "<<Interface>>";
        float textWidth = (float)graphics2D.getFont().getStringBounds(interfaceString, graphics2D.getFontRenderContext()).getWidth();
        float textHeight = (float)graphics2D.getFont().getStringBounds(interfaceString, graphics2D.getFontRenderContext()).getHeight();

        float x_text = x + (float) (getBoxWidth() - textWidth) / 2;
        float y_text = y + textHeight;
        graphics2D.drawString(interfaceString, x_text, y_text);

        y += textHeight;
        super.drawInterclassName(graphics2D, x, y);
    }

    private String getMaxContentStringLength() {
        String maxStringLength = "";
        for (Method method : getInterfaceElement().getInterfaceMethods()) {
            if (maxStringLength.length() < method.toString().length())
                maxStringLength = method.toString();
        }
        return maxStringLength;
    }

    @Override
    protected float lineBelowInterclassNameGetY(Graphics2D graphics2D) {
        return super.lineBelowInterclassNameGetY(graphics2D) + getInterclassHeaderStringHeight(graphics2D);
    }

    private InterfaceElement getInterfaceElement() { return (InterfaceElement) diagramElement; }
}
