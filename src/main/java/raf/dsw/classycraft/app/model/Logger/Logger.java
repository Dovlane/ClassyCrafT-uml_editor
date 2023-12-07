package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

public abstract class Logger implements ILogger, IListener {

    public Logger() {
        MainFrame.getInstance().getMessageGenerator().addListener(this);
    }

    @Override
    public String formatMessage(Message msg) {
        return msg.toString();
    }
}
