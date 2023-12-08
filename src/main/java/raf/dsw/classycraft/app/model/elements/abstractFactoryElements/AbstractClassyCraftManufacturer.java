package raf.dsw.classycraft.app.model.elements.abstractFactoryElements;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public abstract class AbstractClassyCraftManufacturer {
    public abstract Interclass createInterclass(InfoForCreatingInterclass infoForCreatingInterclass);
    public abstract Connection createConnection(InfoForCreatingConnection infoForCreatingConnection);
}
