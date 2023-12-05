package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedPaneMouseAdapter extends MouseAdapter {

    private MouseState mouseState = MouseState.MOUSE_INACTIVE;

    private DiagramView diagramViewSelected = null;

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JTabbedPane tabbedPane = (JTabbedPane) e.getComponent();

        if (tabbedPaneIsEmpty(tabbedPane))
            return;

        diagramViewSelected = (DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        if (mouseEventInsideTab(tabbedPane, e)) {
            System.out.println("Mouse is pressed in tab");
            mouseState = MouseState.MOUSE_PRESSED;
            Point location = getLocationOfMouseOnDiagramView(e);
            ApplicationFramework.getInstance().getClassyRepository().getPackageView().mousePressed(diagramViewSelected, location);
        }
        else {
            System.out.println("Mouse is pressed outside of tab");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        JTabbedPane tabbedPane = (JTabbedPane) e.getComponent();

        if (tabbedPaneIsEmpty(tabbedPane))
            return;

        double eX = e.getX();
        double eY = e.getY();
        if (mouseEventInsideTab(tabbedPane, e)) {
            if (mouseState == MouseState.MOUSE_DRAGGED){
                System.out.println("Mouse is not dragging anymore");
            }

            if (mouseState != MouseState.MOUSE_INACTIVE) {
                Point location = getLocationOfMouseOnDiagramView(e);
                ApplicationFramework.getInstance().getClassyRepository().getPackageView().mouseReleased(diagramViewSelected, location);
            }

            System.out.println("Mouse is released in tab");
        }
        else {
            System.out.println("Mouse is released outside of tab");
        }
        mouseState = MouseState.MOUSE_INACTIVE;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (mouseState == MouseState.MOUSE_INACTIVE){
            return;
        }

        if (mouseState == MouseState.MOUSE_PRESSED) {
            System.out.println("Mouse has started dragging");
            mouseState = MouseState.MOUSE_DRAGGED;
        }
        else if (mouseState == MouseState.MOUSE_DRAGGED) {
            System.out.println("Mouse is still dragging");
        }
        Point location = getLocationOfMouseOnDiagramView(e);
        ApplicationFramework.getInstance().getClassyRepository().getPackageView().mouseDragged(diagramViewSelected, location);
    }


    private Point getLocationOfMouseOnDiagramView(MouseEvent e) {
        int xCoorOfMouseOnDiagramView = e.getX();
        int yCoorOfMouseOnDiagramView = e.getY() - diagramViewSelected.getY();
        Point locationOfMouseOnDiagramView = new Point(xCoorOfMouseOnDiagramView, yCoorOfMouseOnDiagramView);
        return locationOfMouseOnDiagramView;
    }

    private boolean mouseEventInsideTab(JTabbedPane tabbedPane, MouseEvent e) {
        if (tabbedPane.getTabCount() > 0){
            double x = diagramViewSelected.getLocation().getX();
            double y = diagramViewSelected.getLocation().getY();
            Point location = getLocationOfMouseOnDiagramView(e);
            double eX = location.getX();
            double eY = location.getY() + y;
            double width = diagramViewSelected.getWidth();
            double height = diagramViewSelected.getHeight();
            boolean isInsideTab = x < eX && y < eY && eX < x + width && eY < y + height;

            return isInsideTab;
        }
        return false;
    }

    private boolean tabbedPaneIsEmpty(JTabbedPane tabbedPane){
        return tabbedPane.getTabCount() == 0;
    }


}
