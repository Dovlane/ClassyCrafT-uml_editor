package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;
import raf.dsw.classycraft.app.model.observerPattern.IListener;
import raf.dsw.classycraft.app.model.observerPattern.IPublisher;

import java.util.ArrayList;
import java.util.List;

public class Package extends ClassyNodeComposite implements IPublisher {

    List<IListener> listeners = new ArrayList<>();
    private int nmbOfCreatedPackages;
    private int nmbOfCreatedDiagrams;

    public Package(String name, ClassyNode parent) {
        super(name, parent);
        nmbOfCreatedPackages = 0;
        nmbOfCreatedDiagrams = 0;
        addListener(ApplicationFramework.getInstance().getClassyRepository().getPackageView());
    }

    @Override
    public void addChild(ClassyNode child) {
        if (child instanceof Package || child instanceof Diagram) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
            }
        }
    }

    @Override
    public void addListener(IListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifyAllSubscribers(Object notification) {
        for (IListener listener : listeners)
            listener.update(notification);
    }

    public void increasePackageCounter() {
        nmbOfCreatedPackages += 1;
    }

    public void increaseDiagramCounter() {
        nmbOfCreatedDiagrams += 1;
    }

    public int getNmbOfCreatedPackages() {
        return nmbOfCreatedPackages;
    }

    public int getNmbOfCreatedDiagrams() {
        return nmbOfCreatedDiagrams;
    }

}
