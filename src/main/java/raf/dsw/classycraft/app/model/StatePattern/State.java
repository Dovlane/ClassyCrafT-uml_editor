package raf.dsw.classycraft.app.model.StatePattern;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;

import java.awt.*;

public interface State {

    void mousePressed(Point location, DiagramView diagramView);
    void mouseReleased(Point location, DiagramView diagramView);
    void mouseDragged(Point start, Point location, DiagramView diagramView);

}
