package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedPaneMouseAdapter extends MouseAdapter {

    MouseState mouseState = MouseState.MOUSE_INACTIVE;

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JTabbedPane tabbedPane = (JTabbedPane) e.getComponent();
        double eX = e.getX();
        double eY = e.getY();
        if (tabbedPaneIsEmpty(tabbedPane))
            return;
        if (mouseEventInsideTab(tabbedPane, eX, eY)){
            System.out.println("Mouse is pressed in tab");
            mouseState = MouseState.MOUSE_PRESSED;
        }
        else{
            System.out.println("Mouse is pressed outside of tab");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        JTabbedPane tabbedPane = (JTabbedPane) e.getComponent();
        double eX = e.getX();
        double eY = e.getY();
        if (tabbedPaneIsEmpty(tabbedPane))
            return;
        if (mouseEventInsideTab(tabbedPane, eX, eY)){
            if (mouseState == MouseState.MOUSE_DRAGGED){
                System.out.println("Mouse is not dragging anymore");
            }
            mouseState = MouseState.MOUSE_INACTIVE;
            System.out.println("Mouse is released in tab");
        }
        else {
            System.out.println("Mouse is released outside of tab");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (mouseState == MouseState.MOUSE_INACTIVE){
            return;
        }
        if (mouseState == MouseState.MOUSE_PRESSED){
            System.out.println("Mouse has started dragging");
            mouseState = MouseState.MOUSE_DRAGGED;
        }
        else if (mouseState == MouseState.MOUSE_DRAGGED) {
            System.out.println("Mouse is still dragging");
        }
    }

    private boolean mouseEventInsideTab(JTabbedPane tabbedPane, double eX, double eY) {
        if (tabbedPane.getTabCount() > 0){
            DiagramView selectedDiagramView = (DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
            double x = selectedDiagramView.getLocation().getX();
            double y = selectedDiagramView.getLocation().getY();
            double width = selectedDiagramView.getWidth();
            double height = selectedDiagramView.getHeight();

            return x < eX && y < eY && x < eX + width && y < eY + height;
        }
        return false;
    }

    private boolean tabbedPaneIsEmpty(JTabbedPane tabbedPane){
        return tabbedPane.getTabCount() == 0;
    }

}
