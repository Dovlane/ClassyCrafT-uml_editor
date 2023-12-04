package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.controller.debug.PaintTestAction;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterclassPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Modifiers.AccessModifiers;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;

public class DiagramView extends JPanel implements IListener {

    private Diagram diagram;
    private boolean painterTestActionShouldBePerformed = false;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
        diagram.addListener(this);
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof PaintTestAction) {
            painterTestActionShouldBePerformed = true;
        }
        repaint();
    }

    public Diagram getDiagram() {
        return diagram;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (painterTestActionShouldBePerformed) {
            Graphics2D graphics2D = (Graphics2D) g;
            InterclassPainter interclassPainter = new InterclassPainter(new ClassElement("test class", diagram.getParent(), new Point(100, 100), AccessModifiers.PRIVATE, "test class name"));
            interclassPainter.draw(graphics2D, 100, 100);
            System.out.println("DiagramView paintComponent is being performed");
            painterTestActionShouldBePerformed = false;
        }
    }
}
