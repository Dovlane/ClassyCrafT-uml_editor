package raf.dsw.classycraft.app.model.StatePattern;

import raf.dsw.classycraft.app.model.StatePattern.concrete.*;

public class StateManager {

    private State currentState;
    private AddInterclassState addInterclassState;
    private AddConnectionState addConnectionState;
    private AddClassContentState addClassContentState;
    private RemoveElementState removeElementState;
    private SelectElementState selectElementState;

    public StateManager() {

        // Init states
        addInterclassState = new AddInterclassState();
        addConnectionState = new AddConnectionState();
        removeElementState = new RemoveElementState();
        addClassContentState = new AddClassContentState();
        selectElementState = new SelectElementState();

        // Set default state for currentState
        setAddInterclassState();
    }


    // State setters
    public void setAddInterclassState() {
        currentState = addInterclassState;
    }

    public void setAddConnectionState() {
        currentState = addConnectionState;
    }

    public void setAddClassContentState() {
        currentState = addClassContentState;
    }

    public void setRemoveElementState() {
        currentState = removeElementState;
    }

    public void setSelectElementState() {
        currentState = selectElementState;
    }


    // Getters
    public State getCurrentState() {
        return currentState;
    }

}
