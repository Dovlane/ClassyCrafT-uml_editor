package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedPaneMouseAdapter extends MouseAdapter {

    private MouseState mouseState;
    private DiagramView diagramViewSelected;
    private Point startLocation;

    public TabbedPaneMouseAdapter() {
        this.mouseState = MouseState.MOUSE_INACTIVE;
    }

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
            startLocation = getLocationOfMouseOnDiagramView(e);
            ApplicationFramework.getInstance().getClassyRepository().getPackageView()
                    .mousePressed(diagramViewSelected, startLocation);
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

        Point location = getOptimalLocation(tabbedPane, e);
        if (location != null) {

            if (mouseState == MouseState.MOUSE_DRAGGED) {
                System.out.println("Mouse is not dragging anymore");
            }

            if (mouseState != MouseState.MOUSE_INACTIVE) {
                ApplicationFramework.getInstance().getClassyRepository().getPackageView()
                        .mouseReleased(diagramViewSelected, location);
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
        JTabbedPane tabbedPane = (JTabbedPane) e.getComponent();

        if (mouseState == MouseState.MOUSE_INACTIVE) {
            return;
        }

        if (mouseState == MouseState.MOUSE_PRESSED) {
            System.out.println("Mouse has started dragging");
            mouseState = MouseState.MOUSE_DRAGGED;
        }
        else if (mouseState == MouseState.MOUSE_DRAGGED) {
            System.out.println("Mouse is still dragging");
        }

        Point currentLocation = getOptimalLocation(tabbedPane, e);
        ApplicationFramework.getInstance().getClassyRepository().getPackageView().
                mouseDragged(diagramViewSelected, startLocation, currentLocation);
    }


    private Point getLocationOfMouseOnDiagramView(MouseEvent e) {
        int xCoorOfMouseOnDiagramView = e.getX() - diagramViewSelected.getX();
        int yCoorOfMouseOnDiagramView = e.getY() - diagramViewSelected.getY();
        Point locationOfMouseOnDiagramView = new Point(xCoorOfMouseOnDiagramView, yCoorOfMouseOnDiagramView);
        return locationOfMouseOnDiagramView;
    }

    private boolean mouseEventInsideTab(JTabbedPane tabbedPane, MouseEvent e) {
        if (tabbedPane.getTabCount() > 0) {
            Point location = getLocationOfMouseOnDiagramView(e);
            double width = diagramViewSelected.getWidth();
            double height = diagramViewSelected.getHeight();
            return (0 < location.x && location.x < width && 0 < location.y && location.y < height);
        }
        return false;
    }

    private Point getOptimalLocation(JTabbedPane tabbedPane, MouseEvent e) {
        if (tabbedPane.getTabCount() > 0) {
            Point location = getLocationOfMouseOnDiagramView(e);
            double width = diagramViewSelected.getWidth();
            double height = diagramViewSelected.getHeight();
            location.x = (int) Math.min(Math.max(1, location.x), width - 1);
            location.y = (int) Math.min(Math.max(1, location.y), height - 1);
            return location;
        }
        return null;
    }

    private boolean tabbedPaneIsEmpty(JTabbedPane tabbedPane){
        return tabbedPane.getTabCount() == 0;
    }

}
