package raf.dsw.classycraft.app.model.Logger;

import raf.dsw.classycraft.app.model.MessageGenerator.Message;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageGenerator;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileLogger extends Logger {
    private FileWriter fileWriter;
    private PrintWriter printWriter;

    public FileLogger(MessageGenerator messageGenerator) {
        super(messageGenerator);

        fileWriter = null;
        printWriter = null;
        try {
            fileWriter = new FileWriter("src/main/resources/log.txt");
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object notification) {
        String messageText = formatMessage((Message) notification);
        printWriter.println(messageText);
        printWriter.flush();
    }

    public void close() {
        System.out.println("close");
        if (printWriter != null)
                printWriter.close();
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
