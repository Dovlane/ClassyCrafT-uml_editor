package raf.dsw.classycraft.app.model.Logger;

public class LoggerFactory {

    public static Logger createLogger (LoggerType loggerType){
        if (loggerType == LoggerType.CONSOLE_LOGGER)
            return new ConsoleLogger();
        else if (loggerType == LoggerType.FILE_LOGGER)
            return new FileLogger();
        else
            return null;
    }
}
