package raf.dsw.classycraft.app.gui.swing.controller.debug;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;

import java.awt.event.ActionEvent;

public class PaintTestAction extends AbstractClassyAction {
    public PaintTestAction() {
        putValue(NAME, "Paint Test");
        putValue(SHORT_DESCRIPTION, "Paint test for ElementPainters");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DiagramView displayedDiagramView = ApplicationFramework.getInstance().getClassyRepository().getPackageView().getLastSelectedTab();
        if (displayedDiagramView != null){
            displayedDiagramView.update(this);
        }
    }
}
