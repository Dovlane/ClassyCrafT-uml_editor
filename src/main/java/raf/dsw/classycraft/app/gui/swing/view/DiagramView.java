package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements IListener {

    private Diagram diagram;
    private List<ElementPainter> painters;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
        painters = new ArrayList<>();
        diagram.addListener(this);
    }

    @Override
    public void update(Object notification) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (ElementPainter painter: painters) {
            painter.draw(graphics2D);
        }
        System.out.println("DiagramView paintComponent is being performed");
    }

    public Diagram getDiagram() {
        return diagram;
    }

}
