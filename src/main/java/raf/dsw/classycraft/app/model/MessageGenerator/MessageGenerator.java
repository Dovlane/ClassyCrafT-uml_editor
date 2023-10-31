package raf.dsw.classycraft.app.model.MessageGenerator;

import raf.dsw.classycraft.app.model.Logger.*;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;

public class MessageGenerator implements IMessageGenerator, IPublisher {
    private ArrayList<IListener> listeners;

    public MessageGenerator() {
        listeners = new ArrayList<>();
        addListener(LoggerFactory.createLogger(LoggerType.CONSOLE_LOGGER));
        addListener(LoggerFactory.createLogger(LoggerType.FILE_LOGGER));
    }

    @Override
    public void generateMessage(String description, MessageType messageType) {
        Message newMessage = new Message(description, messageType);
        notifyAllSubscribers(newMessage);
    }

    @Override
    public void addListener(IListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener : listeners)
            listener.update(notification);
    }
}
