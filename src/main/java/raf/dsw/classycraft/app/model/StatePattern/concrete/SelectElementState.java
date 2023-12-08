package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.LassoPainter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.State;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.LassoElement;

import java.awt.*;

public class SelectElementState implements State {

    private LassoElement lassoElement;
    @Override
    public void mousePressed(Point location, DiagramView diagramView) {
        System.out.println("mousePressed inside SelectElementState");
        Diagram currentDiagram = diagramView.getDiagram();
        lassoElement = new LassoElement("lasso", currentDiagram, location, location);

        ClassyTreeItem classyTreeDiagram =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(currentDiagram);
        if (classyTreeDiagram == null) {
            MainFrame.getInstance().getMessageGenerator().generateMessage(
                    "Diagram cannot be found in ClassyTree.", MessageType.ERROR);
            return;
        }
        MainFrame.getInstance().getClassyTree().attachChild(classyTreeDiagram, lassoElement);
    }

    @Override
    public void mouseReleased(Point location, DiagramView diagramView) {
        System.out.println("mouseReleased inside SelectElementState");
        ClassyTreeItem treeItemLassoElement =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(lassoElement);
        MainFrame.getInstance().getClassyTree().removeItem(treeItemLassoElement);
    }

    @Override
    public void mouseDragged(Point startLocation, Point currentLocation, DiagramView diagramView) {
        System.out.println("mouseDragged inside SelectElementState");
        lassoElement.setSecondPoint(currentLocation);
    }

}