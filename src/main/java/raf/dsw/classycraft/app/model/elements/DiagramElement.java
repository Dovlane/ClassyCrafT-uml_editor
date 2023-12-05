package raf.dsw.classycraft.app.model.elements;

import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import java.awt.*;

public abstract class DiagramElement extends ClassyNode {

    public DiagramElement(String name, Diagram parent) {
        super(name, parent);
    }
}
