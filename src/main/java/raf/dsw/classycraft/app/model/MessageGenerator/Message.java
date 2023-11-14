package raf.dsw.classycraft.app.model.MessageGenerator;

import java.sql.Timestamp;
import java.time.Instant;

public class Message {
    private String description;
    private MessageType type;
    private Timestamp timestamp;

    public Message(String description, MessageType type) {
        this.description = description;
        this.type = type;
        this.timestamp = Timestamp.from(Instant.now());
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s",
                type.toString(), timestamp, description);
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
