package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class Aggregation extends Connection {

    public Aggregation(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Aggregation(String name, ClassyNode parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
    }
}
