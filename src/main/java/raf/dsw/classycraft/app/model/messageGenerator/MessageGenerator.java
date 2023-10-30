package raf.dsw.classycraft.app.model.messageGenerator;

import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.awt.event.ActionEvent;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;

public class MessageGenerator implements IMessageGenerator, IPublisher {
    private ArrayList<IListener> listeners;
    private Message newMessage;
    public MessageGenerator() {
        this.listeners = new ArrayList<>();
    }
    @Override
    public void generateMessage(Object notification, SystemEventType systemEventType) {
        if (systemEventType == SystemEventType.NODE_CANNOT_HAVE_A_SAME_NAME){
            newMessage = new Message( (ActionEvent)notification + " cannot have a same name!", MessageType.ERROR, Timestamp.from(Instant.now()));
        }
        else if (systemEventType == SystemEventType.NODE_CANNOT_HAVE_A_CHILD){
            newMessage = new Message( notification.toString() + " cannot have a child!", MessageType.ERROR, Timestamp.from(Instant.now()));
        }
        else if (systemEventType == SystemEventType.NAME_CANNOT_BE_EMPTY){
            newMessage = new Message( notification.toString() + " cannot have an empty name!", MessageType.ERROR, Timestamp.from(Instant.now()));
        }
        else{
            newMessage = new Message(notification.toString() + " cannot be deleted!", MessageType.ERROR, Timestamp.from(Instant.now()));
        }
    }

    @Override
    public void addListener(IListener listener) {
        if (listeners.contains(listener))
            return;
        listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifySuscribers(Object notification) throws ParseException {
        for (IListener listener : listeners)
            listener.update(newMessage);
    }
}
