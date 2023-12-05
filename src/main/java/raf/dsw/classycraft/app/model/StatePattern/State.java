package raf.dsw.classycraft.app.model.StatePattern;

public interface State {

    // Methods for AddInterclassState
    void addClassElement();
    void addInterfaceElement();
    void addEnumElement();

    // Methods for AddConnectionState
    void addAggregation();
    void addComposition();
    void addDependency();
    void addGeneralization();

    // Methods for AddClassContentState
    void addMethod();
    void addAttribute();

    // Methods for RemoveElementState
    void removeElement();

    // Methods for SelectElementState
    void selectElement();

}
