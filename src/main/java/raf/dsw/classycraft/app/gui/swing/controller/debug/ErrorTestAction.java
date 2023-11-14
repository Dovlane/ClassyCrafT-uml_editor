package raf.dsw.classycraft.app.gui.swing.controller.debug;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;

import java.awt.event.ActionEvent;

public class ErrorTestAction extends AbstractClassyAction {

    public ErrorTestAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/ErrorTest.png"));
        putValue(NAME, "ErrorTest");
        putValue(SHORT_DESCRIPTION, "ErrorTest");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getMessageGenerator().generateMessage("name cannot be empty", MessageType.ERROR);
    }

    @Override
    public String toString() {
        return "ErrorTestAction";
    }
}
