package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.Jackson.DependencyDeserializer;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

@Getter
@Setter
@JsonDeserialize(using = DependencyDeserializer.class)
        public class Dependency extends Connection {

    private DependencyEnum dependencyEnum;

    public Dependency(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
        this.dependencyEnum = DependencyEnum.INSTANTIATE;
    }

    public void setDependencyEnum(DependencyEnum dependencyEnum) {
        if (this.dependencyEnum == dependencyEnum) {
            return;
        }
        this.dependencyEnum = dependencyEnum;
        changeOccurred();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return  "dep-" + from.getName() + "-" + to.getName();
    }

}
