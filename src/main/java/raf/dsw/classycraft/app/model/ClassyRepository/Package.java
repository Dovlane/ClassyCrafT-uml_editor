package raf.dsw.classycraft.app.model.ClassyRepository;

import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNodeComposite;

public class Package extends ClassyNodeComposite {

    private int nmbOfCreatedPackages;
    private int nmbOfCreatedDiagrams;

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

    public void increasePackageCounter() {
        nmbOfCreatedPackages += 1;
    }

    public void increaseDiagramCounter() {
        nmbOfCreatedDiagrams += 1;
    }

    public int getNmbOfCreatedPackages() {
        return nmbOfCreatedPackages;
    }

    public void setNmbOfCreatedPackages(int nmbOfCreatedPackages) {
        this.nmbOfCreatedPackages = nmbOfCreatedPackages;
    }

    public int getNmbOfCreatedDiagrams() {
        return nmbOfCreatedDiagrams;
    }

    public void setNmbOfCreatedDiagrams(int nmbOfCreatedDiagrams) {
        this.nmbOfCreatedDiagrams = nmbOfCreatedDiagrams;
    }

}
