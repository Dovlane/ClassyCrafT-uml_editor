package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;

public interface ILogger {
    String formatMessage(Message msg);
}
