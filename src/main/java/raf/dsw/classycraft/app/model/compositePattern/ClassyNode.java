package raf.dsw.classycraft.app.model.compositePattern;

public abstract class ClassyNode {
    private ClassyNode parent;

    public ClassyNode getParent() {
        return parent;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }
    public ClassyNode(ClassyNode parent) {
        this.parent = parent;
    }
}
