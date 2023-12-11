package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.FactoryForPainters;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.LinePainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Notification;
import raf.dsw.classycraft.app.model.ClassyRepository.NotificationType;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.elements.Connection.*;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DiagramView extends JPanel implements IListener {

    private final Diagram diagram;
    private final List<ElementPainter> painters;
    private final List<ElementPainter> selectionModel;
    private LassoPainter lassoPainter;
    private LinePainter linePainter;
    private double zoomFactor = 1.0;
    private AffineTransform transform = new AffineTransform();
    private FactoryForPainters factoryForPainters = new FactoryForPainters();

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

        // Apply zoom transformation
        graphics2D.transform(transform);

        // Display all ElementPainters
        for (ElementPainter painter: painters) {
            painter.draw(graphics2D);
        }

        // Stand out all selected ElementPainters
        for (ElementPainter painter: selectionModel) {
            painter.drawSelectionBox(graphics2D);
        }

        // Draw Lasso
        if (lassoPainter != null) {
            lassoPainter.draw(graphics2D);
        }

        // Draw temporary line for connections
        if (linePainter != null) {
            linePainter.draw(graphics2D);
        }

        // Debug Info
        System.out.println("DiagramView paintComponent is being performed");
    }

    public void addPainter(DiagramElement diagramElement) {

        // Factory for ElementPainters based on Diagram Element
        ElementPainter elementPainter = null;
        if (diagramElement instanceof Interclass) {
            elementPainter = factoryForPainters.createInterclassPainter((Interclass) diagramElement);
        }
        else if (diagramElement instanceof Connection) {
            elementPainter = factoryForPainters.createConnectionPainter((Connection) diagramElement);
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

    public void updateSelectionModel(LassoPainter lassoPainter) {

        // Create upper-left and bottom-right corner
        setLassoPainter(lassoPainter);

        // Fill the selection model with new painters
        if (lassoPainter != null) {
            selectionModel.clear();
            for (ElementPainter painter : painters) {
                if (lassoPainter.intersectsWith(painter)) {
                    selectionModel.add(painter);
                }
            }
        }

        repaint();
    }

    public void updateLinePainter (LinePainter linePainter) {
        setLinePainter(linePainter);
        repaint();
    }

    public void zoom(int wheelRotation, Point location) {
        zoomFactor = (wheelRotation >= 0) ? (wheelRotation > 0) ? 0.95 : 1 : 1.05;
        transform.transform(location, location);
        zoomWithFactor(zoomFactor, location);
    }

    public void zoomWithFactor(double zoomFactor, Point location) {
        AffineTransform previousTransform = new AffineTransform(transform);
        transform.setToIdentity();
        transform.translate(location.x, location.y);
        transform.scale(zoomFactor, zoomFactor);
        transform.translate(-location.x, -location.y);
        transform.concatenate(previousTransform);
        repaint();
    }

    public void move(Point previousLocation, Point currentLocation) {
        AffineTransform previousTransform = new AffineTransform(transform);
        transform.setToIdentity();
        int dx = currentLocation.x - previousLocation.x;
        int dy = currentLocation.y - previousLocation.y;
        transform.translate(dx, dy);
        transform.concatenate(previousTransform);
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

    public void setLassoPainter(LassoPainter lassoPainter) {
        this.lassoPainter = lassoPainter;
    }

    public void setLinePainter(LinePainter linePainter) {
        this.linePainter = linePainter;
    }
    public DiagramElement getElementAt(Point location) {
        ElementPainter elementPainter = getPainterAt(location);
        if (elementPainter != null) {
            return elementPainter.getDiagramElement();
        }
        return null;
    }

    public ElementPainter getPainterAt(Point location) {
        for (ElementPainter elementPainter : getPainters()) {
            if (elementPainter.elementAt(location)) {
                return elementPainter;
            }
        }
        return null;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DiagramView) {
            return getDiagram().equals(((DiagramView) object).getDiagram());
        }
        return false;
    }

}
