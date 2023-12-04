package raf.dsw.classycraft.app.model.ClassyRepository;

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
    private static Package defaultPackage;
    private static Package displayedPackage;

    public Package(String name, ClassyNode parent) {
        super(name, parent);
        nmbOfCreatedPackages = 0;
        nmbOfCreatedDiagrams = 0;
    }

    @Override
    public void addChild(ClassyNode child) {
        if (child instanceof Package || child instanceof Diagram) {
            if (!getChildren().contains(child)) {
                getChildren().add(child);
            }
        }
    }

    public Project findParentProject() {
        ClassyNode tmp = this;
        while ((tmp != null) && !(tmp instanceof Project)) {
            tmp = tmp.getParent();
        }
        return (Project) tmp;
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

        // There will be only 1 listener in the array of
        // listeners, because there is only one PackageView.

        // Problem with for-each loop is because every time
        // the update happens the listener should be removed
        // from the listeners array list.
        // It raises ConcurrentModificationException.

        // Solution is to only use the first element of the
        // list which is at the same time the only listener.

        listeners.get(0).update(notification);
        System.out.println("The Number of listeners of this package is " + listeners.size());
    }

    public static Package getDefaultPackage() {
        if (defaultPackage == null)
            defaultPackage = new Package("Default Package", null);
        return defaultPackage;
    }

    public static Package getDisplayedPackage() {
        if (displayedPackage == null)
            displayedPackage = getDefaultPackage();
        return displayedPackage;
    }

    public static void setDisplayedPackage(Package displayedPackage) {
        Package.displayedPackage = displayedPackage;
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
