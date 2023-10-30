package raf.dsw.classycraft.app.model.logger;

import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;

public class LoggerFactory {
    public static Logger createLogger (String loggerType, MessageGenerator msgGenerator){
        if (loggerType.toUpperCase().equals("CONSOLELOGGER"))
            return new ConsoleLogger(msgGenerator);
        else if (loggerType.toUpperCase().equals("FILELOGGER"))
            return new FileLogger(msgGenerator);
        else
            return null;
    }
}
