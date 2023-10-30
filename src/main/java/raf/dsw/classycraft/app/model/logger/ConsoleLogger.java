package raf.dsw.classycraft.app.model.logger;

import raf.dsw.classycraft.app.model.messageGenerator.Message;
import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;

import java.text.ParseException;


public class ConsoleLogger extends Logger{
    public ConsoleLogger(MessageGenerator msg) {
        super(msg);
    }

    @Override
    public void update(Object notification) throws ParseException {
        String messageText = formatMessage((Message) notification);
        System.out.println(messageText);
    }
}
