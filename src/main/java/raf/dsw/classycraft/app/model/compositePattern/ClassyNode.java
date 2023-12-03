package raf.dsw.classycraft.app.model.compositePattern;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;

public abstract class ClassyNode {

    private String name;
    private ClassyNode parent;

    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public void removeSubtree() {
        if (this instanceof ClassyNodeComposite) {
            while (((ClassyNodeComposite) this).getChildCount() > 0) {
                ((ClassyNodeComposite) this).getChildren().get(0).removeSubtree();
            }
        }
        removeFromParent();
    }

    public void removeFromParent() {
        ClassyNodeComposite parent = (ClassyNodeComposite) getParent();
        if (parent != null) {
            parent.removeAt(this);
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

    public boolean setName(String name) {
        ClassyNodeComposite parent = (ClassyNodeComposite) this.parent;
        if (!name.isEmpty() && parent.getChildByName(name) == null) {
            this.name = name;
            return true;
        } else if (name.isEmpty()) {
            String errorMessage = "New name cannot be an empty string.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        } else {
            System.out.println(name);
            String errorMessage = "The path of the file is ambiguous.";
            MainFrame.getInstance().getMessageGenerator().generateMessage(errorMessage, MessageType.ERROR);
        }
        return false;
    }

    public ClassyNode getParent() {
        return parent;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

}
