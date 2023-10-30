package raf.dsw.classycraft.app.model.observerPattern;

import java.text.ParseException;

public interface IPublisher {
    void addListener(IListener listener);
    void removeListener(IListener listener);
    void notifySuscribers(Object notification) throws ParseException;
}
