package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.messageGenerator.SystemEventType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;

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
        msgGenerator.generateMessage(this, SystemEventType.NAME_CANNOT_BE_EMPTY);
        try {
            msgGenerator.notifySuscribers(e);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "ErrorTestAction";
    }
}
