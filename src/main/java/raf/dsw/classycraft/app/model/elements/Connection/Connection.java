package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.DiagramElement;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public abstract class Connection extends DiagramElement {

    protected Interclass from;
    protected Interclass to;

    public Connection(String name, Diagram parent) {
        super(name, parent);
    }

    public Connection(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent);
        this.from = from;
        this.to = to;
    }


    // Getters and Setters
    public Interclass getFrom() {
        return from;
    }

    public void setFrom(Interclass from) {
        this.from = from;
    }

    public Interclass getTo() {
        return to;
    }

    public void setTo(Interclass to) {
        this.to = to;
    }

}
