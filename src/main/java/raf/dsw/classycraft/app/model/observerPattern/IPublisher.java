package raf.dsw.classycraft.app.model.observerPattern;

public interface IPublisher {

    void addListener(IListener listener);

    void removeListener(IListener listener);

    void notifyAllSubscribers(Object notification);
}
