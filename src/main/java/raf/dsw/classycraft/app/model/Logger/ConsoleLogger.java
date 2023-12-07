package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;

public class ConsoleLogger extends Logger {

    public ConsoleLogger(MessageGenerator messageGenerator) {
        super(messageGenerator);
    }

    @Override
    public void update(Object notification) {
        String messageText = formatMessage((Message) notification);
        System.out.println(messageText);
    }
}
