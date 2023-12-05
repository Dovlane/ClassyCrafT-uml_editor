package raf.dsw.classycraft.app.model.StatePattern.concrete;

import raf.dsw.classycraft.app.model.StatePattern.State;

public class AddInterclassState implements State {

    // Methods for AddInterclassState
    @Override
    public void addClassElement() {
        System.out.println("Added a new class on a Diagram!");
    }

    @Override
    public void addInterfaceElement() {

    }

    @Override
    public void addEnumElement() {

    }

    // Methods for other states
    @Override
    public void addAggregation() { }

    @Override
    public void addComposition() { }

    @Override
    public void addDependency() { }

    @Override
    public void addGeneralization() { }

    @Override
    public void addMethod() { }

    @Override
    public void addAttribute() { }

    @Override
    public void removeElement() { }

    @Override
    public void selectElement() { }

}
