package raf.dsw.classycraft.app.model.observerPattern;

import java.text.ParseException;

public interface IListener {
    void update(Object notification) throws ParseException;
}
