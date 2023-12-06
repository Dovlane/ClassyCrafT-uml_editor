package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;

import java.awt.*;

public abstract class DiagramElement extends ClassyNodeLeaf {

    public DiagramElement(String name, Diagram parent) {
        super(name, parent);
    }
}
