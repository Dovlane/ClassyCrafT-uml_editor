package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

public abstract class Logger implements ILogger, IListener {

    @Override
    public String formatMessage(Message msg) {
        return msg.toString();
    }
}
