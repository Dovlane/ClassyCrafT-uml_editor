package raf.dsw.classycraft.app.model.compositePattern;

import java.util.ArrayList;

public abstract class ClassyNodeComposite extends ClassyNode{
    private ArrayList<ClassyNode> children;
    @Override
    public ClassyNode getParent() {
        return super.getParent();
    }

    @Override
    public void setParent(ClassyNode parent) {
        super.setParent(parent);
    }

    public ClassyNodeComposite(ClassyNode parent) {
        super(parent);
        children = new ArrayList<>();
    }

    public void addChild(ClassyNode child){
        if (children.contains(child))
            return;
        children.add(child);
        child.setParent(this);
    }
}
