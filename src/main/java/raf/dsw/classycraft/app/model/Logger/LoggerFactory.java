package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;

public class LoggerFactory {

    public static Logger createLogger (LoggerType loggerType){
        MessageGenerator messageGenerator = MainFrame.getInstance().getMessageGenerator();
        if (loggerType == LoggerType.CONSOLE_LOGGER)
            return new ConsoleLogger(messageGenerator);
        else if (loggerType == LoggerType.FILE_LOGGER)
            return new FileLogger(messageGenerator);
        else
            return null;
    }
}
