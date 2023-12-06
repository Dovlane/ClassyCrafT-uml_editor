package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements IListener {

    private Diagram diagram;
    private List<ElementPainter> painters;
    private List<ElementPainter> selectionModel;
    private LassoPainter lassoPainter;
    private boolean selectionFinished;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
        painters = new ArrayList<>();
        selectionModel = new ArrayList<>();
        diagram.addListener(this);
    }

    @Override
    public void update(Object notification) {

        if (notification instanceof DiagramElement) {
            // TODO: remove element painter or a group of selected painters
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // Display all ElementPainters
        for (ElementPainter painter: painters) {
            painter.draw(graphics2D);
        }

        // Stand out all selected ElementPainters
        if (selectionFinished) {
            for (ElementPainter painter: selectionModel) {
                painter.drawSelectionBox(graphics2D);
            }
        }

        // Display lasso if it is necessary
        if (lassoPainter != null) {
            lassoPainter.draw(graphics2D);
        }

        // Debug Info
        System.out.println("DiagramView paintComponent is being performed");
    }

    public void addPainter(ElementPainter painter) {
        if (!painters.contains(painter)) {
            painters.add(painter);
        }
    }

    public void updateSelectionModel(Point location) {
        for (ElementPainter painter: painters) {
            if (painter.elementAt(location)) {
                addSelectedPainter(painter);
            }
        }
    }

    public void addSelectedPainter(ElementPainter painter) {
        if (!selectionModel.contains(painter)) {
            selectionModel.add(painter);
        }
    }

    public void clearSelectionModel() {
        selectionModel.clear();
        setSelectionFinished(false);
        setLasso(null);
    }


    // Getters and setters
    public void setLasso(LassoPainter lassoPainter) {
        this.lassoPainter = lassoPainter;
        repaint();
    }

    public void setSelectionFinished(boolean selectionFinished) {
        this.selectionFinished = selectionFinished;
    }

    public Diagram getDiagram() {
        return diagram;
    }

}
