package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;

public class ConsoleLogger extends Logger {

    public ConsoleLogger() {
        super();
    }

    @Override
    public void update(Object notification) {
        String messageText = formatMessage((Message) notification);
        System.out.println(messageText);
    }
}
