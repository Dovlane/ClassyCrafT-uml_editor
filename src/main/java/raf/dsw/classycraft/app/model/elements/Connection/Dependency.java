package raf.dsw.classycraft.app.model.elements.Connection;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public class Dependency extends Connection {
    private DependencyEnum dependencyEnum;
    public Dependency(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
        this.dependencyEnum = DependencyEnum.INSTANTIATE;
    }

    public DependencyEnum getDependencyEnum() {
        return dependencyEnum;
    }

    public void setDependencyEnum(DependencyEnum dependencyEnum) {
        this.dependencyEnum = dependencyEnum;
    }
}
