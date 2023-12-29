package raf.dsw.classycraft.app.model.commandPattern.concreteCommand;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.commandPattern.AbstractCommand;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Connection.Connection;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;

import java.awt.*;

public class MoveCommand extends AbstractCommand {
    private Diagram diagram;
    private Point startLocation;
    private Interclass movedInterclass;
    private Point endLocation;

    public MoveCommand(Diagram diagram, Interclass movedInterclass, Point startLocation, Point endLocation) {
        this.diagram = diagram;
        this.movedInterclass = movedInterclass;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    @Override
    public void doCommand() {
        updateMovedInterclass();
        movedInterclass.setLocation(endLocation);

        System.out.println("MoveCommand doCommand");
    }

    @Override
    public void undoCommand() {
        updateMovedInterclass();
        movedInterclass.setLocation(startLocation);

        System.out.println("MoveCommand undoCommand");
    }

    private void updateMovedInterclass() {
        movedInterclass.setParent(diagram);
        ClassyTreeItem treeItemMovedInterclass =
                MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(movedInterclass);
        if (movedInterclass.equals(treeItemMovedInterclass.getClassyNode())) {
            movedInterclass = (Interclass) treeItemMovedInterclass.getClassyNode();
            for (ClassyNode diagramElementClassyNode : diagram.getChildren()) {
                if (diagramElementClassyNode instanceof Connection) {
                    Connection connection = (Connection) diagramElementClassyNode;
                    if (connection.containsInterclass(movedInterclass)) {
                        if (connection.getFrom().equals(movedInterclass))
                            connection.setFrom(movedInterclass);
                        else if (connection.getTo().equals(movedInterclass))
                            connection.setTo(movedInterclass);
                    }
                }
            }
        }
    }
}
