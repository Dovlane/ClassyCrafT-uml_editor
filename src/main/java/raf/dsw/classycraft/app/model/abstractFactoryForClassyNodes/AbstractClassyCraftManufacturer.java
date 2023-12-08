package raf.dsw.classycraft.app.model.abstractFactoryForClassyNodes;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

public abstract class AbstractClassyCraftManufacturer {
    public abstract Interclass createInterclass(InfoForCreatingInterclass infoForCreatingInterclass);
    public abstract Connection createConnection(InfoForCreatingConnection infoForCreatingConnection);
    public abstract ClassyNode createClassyNode(InfoForCreatingClassyNodeCompositeNodes infoForCreatingClassyNodeCompositeNodes);
}
