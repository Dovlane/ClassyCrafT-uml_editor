package raf.dsw.classycraft.app.model.logger;

import raf.dsw.classycraft.app.model.messageGenerator.Message;
import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

public class FileLogger extends Logger{
    private FileWriter fileWriter;
    private PrintWriter printWriter;

    public FileLogger(MessageGenerator msg) {
        super(msg);
        fileWriter = null;
        printWriter = null;
        try {
            fileWriter = new FileWriter("src/main/resources/log.txt");
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            if (printWriter != null)
//                printWriter.close();
//            if (fileWriter != null)
//            {
//                try{
//                    fileWriter.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

    }

    @Override
    public void update(Object notification) throws ParseException {
        String messageText = formatMessage((Message) notification);
        printWriter.println(messageText);
        printWriter.flush();
        //printWriter.close();
    }
}
