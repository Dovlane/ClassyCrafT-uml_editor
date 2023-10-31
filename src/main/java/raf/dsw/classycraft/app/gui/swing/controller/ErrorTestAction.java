package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;

import java.awt.event.ActionEvent;

public class ErrorTestAction extends AbstractClassyAction{
    private MessageGenerator msgGenerator;
    public ErrorTestAction(MessageGenerator msgGenerator) {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/error_test.png"));
        putValue(NAME, "ErrorTest");
        putValue(SHORT_DESCRIPTION, "ErrorTest");
        this.msgGenerator = msgGenerator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        msgGenerator.generateMessage("name cannot be empty", MessageType.ERROR);
    }

    @Override
    public String toString() {
        return "ErrorTestAction";
    }
}
