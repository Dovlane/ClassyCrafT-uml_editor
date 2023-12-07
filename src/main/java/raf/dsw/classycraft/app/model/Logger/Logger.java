package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

public abstract class Logger implements ILogger, IListener {

    protected MessageGenerator messageGenerator;

    public Logger(MessageGenerator messageGenerator) {
        setMessageGenerator(messageGenerator);
        messageGenerator.addListener(this);
    }

    @Override
    public String formatMessage(Message msg) {
        return msg.toString();
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }
}
