package raf.dsw.classycraft.app.model.elements.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.Jackson.GeneralizationDeserializer;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

@Getter
@Setter
@JsonDeserialize(using = GeneralizationDeserializer.class)
public class Generalization extends Connection {

    public Generalization(String name, Diagram parent) {
        super(name, parent);
    }

    public Generalization(String name, Diagram parent, Interclass from, Interclass to) {
        super(name, parent, from, to);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return  "gen-" + from.getName() + "-" + to.getName();
    }

}
