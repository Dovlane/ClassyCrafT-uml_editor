package raf.dsw.classycraft.app.model.logger;

import raf.dsw.classycraft.app.model.messageGenerator.Message;
import raf.dsw.classycraft.app.model.messageGenerator.MessageGenerator;
import raf.dsw.classycraft.app.model.messageGenerator.MessageType;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public abstract class Logger implements ILogger, IListener {
    private MessageGenerator msg;

    public Logger(MessageGenerator msg) {
        this.msg = msg;
        msg.addListener(this);
    }

    @Override
    public String formatMessage(Message msg) throws ParseException {

//        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy. HH:mm");
//        sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        //String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").toString();
        String dateString = convertTimeStampToDateString(msg.getTimestamp());
        return String.format("[%s][%s] %s", msg.getType().toString(), dateString, msg.getDescription());
    }

    private String convertTimeStampToDateString(Timestamp timestamp){
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String dateString = String.format("%02d.%02d.%d. %02d:%02d", day, month, year, hour, minute);
        return dateString;
    }
}
