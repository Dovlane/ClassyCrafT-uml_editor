package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;

public class DiagramView extends JPanel implements IListener {

    private Diagram diagram;

    public DiagramView(Diagram diagram){
        this.diagram = diagram;
    }

    @Override
    public void update(Object notification) {
        ;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

}
