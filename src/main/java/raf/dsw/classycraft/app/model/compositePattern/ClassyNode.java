package raf.dsw.classycraft.app.model.compositePattern;

public abstract class ClassyNode {

    private String name;
    private ClassyNode parent;

    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public void removeFromParent() {
        ClassyNodeComposite parent = (ClassyNodeComposite) getParent();
        if (parent != null) {
            parent.remove(this);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClassyNode) {
            return getName().equals(((ClassyNode) object).getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassyNode getParent() {
        return parent;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

}
