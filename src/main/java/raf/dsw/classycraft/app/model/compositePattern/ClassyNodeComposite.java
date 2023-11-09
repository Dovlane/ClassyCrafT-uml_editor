package raf.dsw.classycraft.app.model.compositePattern;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassyNodeComposite extends ClassyNode {

    private List<ClassyNode> children;

    public ClassyNodeComposite(String name, ClassyNode parent) {
        super(name, parent);
        children = new ArrayList<>();
    }

    public abstract void addChild(ClassyNode child);

    public void remove(ClassyNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(aChild)) {
            throw new IllegalArgumentException("argument is not a child");
        }

        remove(getIndex(aChild));
    }

    public void remove(int childIndex) {
        ClassyNode child = (ClassyNode) getChildAt(childIndex);
        children.remove(childIndex);
        child.setParent(null);
    }

    public boolean isNodeChild(ClassyNode aNode) {
        if (aNode == null)
            return false;

        if (getChildCount() == 0) {
            return false;
        }

        return (aNode.getParent() == this);
    }

    public int getChildCount() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }

    public ClassyNode getChildAt(int index) {
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        return children.get(index);
    }

    public int getIndex(ClassyNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(aChild)) {
            return -1;
        }

        return children.indexOf(aChild);
    }

    public void printNode(int cnt) {
        String leadingWhiteCharacters = new String(new char[cnt*2]).replace("\0", " ");
        System.out.println(leadingWhiteCharacters + getName());
        for (ClassyNode child: getChildren()) {
            if (child instanceof ClassyNodeLeaf) {
                System.out.println(leadingWhiteCharacters + "  " + child.getName());
            } else if (child instanceof ClassyNodeComposite) {
                ((ClassyNodeComposite) child).printNode(cnt+1);
            }
        }
    }

    public ClassyNode getChildByName(String name) {
        for (ClassyNode child: getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }

    public List<ClassyNode> getChildren() {
        return children;
    }

}
