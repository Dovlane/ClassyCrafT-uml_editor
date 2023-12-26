package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class Generalization extends Connection {

    public Generalization(String name, Diagram parent) {
        super(name, parent);
    }

    public Generalization(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
    }
}