package raf.dsw.classycraft.app.gui.swing.controller.debug;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;

import java.awt.event.ActionEvent;

public class PrintTreeAction extends AbstractClassyAction {

    public PrintTreeAction() {
        // putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        // putValue(SMALL_ICON, loadIcon("/images/error_test.png"));
        putValue(NAME, "Print Tree");
        putValue(SHORT_DESCRIPTION, "Print Tree of ClassyNode");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationFramework.getInstance().getClassyRepository().printTree();
    }

}
