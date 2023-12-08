package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;

import java.awt.*;

public class RemoveElementState implements State {

    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside RemoveElementState");

        // Find the DiagramElement which was clicked
        boolean inSelectionModel = false;
        DiagramElement diagramElement = null;
        for (ElementPainter elementPainter: diagramView.getPainters()) {
            if (elementPainter.elementAt(location)) {
                diagramElement = elementPainter.getDiagramElement();
                inSelectionModel = diagramView.getSelectionModel().contains(elementPainter);
            }
        }

        // Remove it and potentially other DiagramElements if they are all selected together
        if (diagramElement != null) {

            // Remove the clicked one from painters
            ClassyTreeItem treeItemDiagramElement =
                    MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(diagramElement);
            MainFrame.getInstance().getClassyTree().removeItem(treeItemDiagramElement);

            // Remove all painters from SelectionModel if necessary
            while (inSelectionModel && diagramView.getSelectionModel().size() > 0) {
                diagramElement = diagramView.getSelectionModel().get(0).getDiagramElement();
                treeItemDiagramElement =
                        MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(diagramElement);
                MainFrame.getInstance().getClassyTree().removeItem(treeItemDiagramElement);
            }

            // Debug
            ApplicationFramework.getInstance().getClassyRepository().printTree();
        }
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside RemoveElementState");

    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside RemoveElementState");

    }

    @Override
    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("mouseWheelMoved inside of RemoveElementState");

    }

}