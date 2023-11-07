package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

public abstract class AbstractClassyAction extends AbstractAction{


    public Icon loadIcon(String fileName){

        URL imageURL = getClass().getResource(fileName);
        Icon icon = null;

        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
        }
        else {
            System.err.println("Resource not found: " + fileName);
        }
        return icon;
    }


}
