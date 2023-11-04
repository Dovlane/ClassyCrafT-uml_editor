package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeLeaf;

public class Diagram extends ClassyNodeLeaf {
    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
    }
}
