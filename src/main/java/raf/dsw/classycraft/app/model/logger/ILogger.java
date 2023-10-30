package raf.dsw.classycraft.app.model.logger;

import raf.dsw.classycraft.app.model.messageGenerator.Message;

import java.text.ParseException;

public interface ILogger {
    String formatMessage(Message msg) throws ParseException;
}
