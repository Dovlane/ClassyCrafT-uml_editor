package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.ClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainters.InterfacePainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.ClassElement;
import raf.dsw.classycraft.app.model.elements.Interclass.EnumElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.elements.Interclass.InterfaceElement;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements IListener {

    private final Diagram diagram;
    private final List<ElementPainter> painters;
    private final List<ElementPainter> selectionModel;
    private LassoPainter lassoPainter;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
        painters = new ArrayList<>();
        selectionModel = new ArrayList<>();
        diagram.addListener(this);
    }

    @Override
    public void update(Object notification) {

        // Cast notification
        Notification diagramNotification = (Notification) notification;

        // DiagramElement has been passed as a notification node
        if (diagramNotification.getNode() instanceof DiagramElement) {
            DiagramElement aDiagramElement = (DiagramElement) diagramNotification.getNode();
            if (diagramNotification.getType() == NotificationType.ADD) {
                addPainter(aDiagramElement);
                System.out.println("Added a new Painter in DiagramView.");
            }
            else if (diagramNotification.getType() == NotificationType.REMOVE) {
                removePainter(aDiagramElement);
                System.out.println("Removed Painter from DiagramView.");
            }
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
        for (ElementPainter painter: selectionModel) {
            painter.drawSelectionBox(graphics2D);
        }

        // Display lasso if it is necessary
        if (lassoPainter != null) {
            lassoPainter.draw(graphics2D);
        }

        // Debug Info
        System.out.println("DiagramView paintComponent is being performed");
    }

    public void addPainter(DiagramElement diagramElement) {

        // Factory for ElementPainters based on Diagram Element
        ElementPainter elementPainter = null;
        if (diagramElement instanceof Interclass) {
            if (diagramElement instanceof ClassElement) {
                elementPainter = new ClassPainter((ClassElement) diagramElement);
            }
            else if (diagramElement instanceof InterfaceElement) {
                elementPainter = new InterfacePainter((InterfaceElement) diagramElement);
            }
            else {
                elementPainter = new EnumPainter((EnumElement) diagramElement);
            }
        }
        else if (diagramElement instanceof Connection) {
            // TODO: add factory for connections
        }

        // Check for Factory quality
        if (elementPainter == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Painter for object " + diagramElement + " does not exist.", MessageType.ERROR);
            return;
        }

        // Add a newly created painter in list of all painters for this DiagramView
        painters.add(elementPainter);
        elementPainter.getDiagramElement().addListener(this);
    }

    public void removePainter(DiagramElement diagramElement) {
        for (int i = 0; i < painters.size(); i++) {
            if (painters.get(i).getDiagramElement().equals(diagramElement)) {
                ElementPainter painter = painters.get(i);
                painters.remove(painter);
                selectionModel.remove(painter);
                painter.getDiagramElement().removeListener(this);
            }
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
        setLasso(null);
    }


    // Getters and setters
    public void setLasso(LassoPainter lassoPainter) {
        this.lassoPainter = lassoPainter;
        repaint();
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public List<ElementPainter> getPainters() {
        return painters;
    }

    public List<ElementPainter> getSelectionModel() {
        return selectionModel;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DiagramView) {
            return getDiagram().equals(((DiagramView) object).getDiagram());
        }
        return false;
    }

}
