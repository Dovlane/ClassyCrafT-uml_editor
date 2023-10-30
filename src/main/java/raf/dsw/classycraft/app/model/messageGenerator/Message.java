package raf.dsw.classycraft.app.model.messageGenerator;

import java.sql.Timestamp;

public class Message {
    private String description;
    private MessageType type;
    private Timestamp timestamp;

    public Message(String description, MessageType type, Timestamp timestamp) {
        this.description = description;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public MessageType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
