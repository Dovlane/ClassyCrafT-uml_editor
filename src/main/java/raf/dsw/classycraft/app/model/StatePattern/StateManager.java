package raf.dsw.classycraft.app.model.StatePattern;

import raf.dsw.classycraft.app.model.StatePattern.concrete.*;

public class StateManager {

    private State currentState;
    private final AddInterclassState addInterclassState;
    private final AddConnectionState addConnectionState;
    private final AddClassContentState addClassContentState;
    private final MoveState moveState;
    private final RemoveElementState removeElementState;
    private final SelectElementState selectElementState;

    public StateManager() {

        // Init states
        addInterclassState = new AddInterclassState();
        addConnectionState = new AddConnectionState();
        addClassContentState = new AddClassContentState();
        moveState = new MoveState();
        removeElementState = new RemoveElementState();
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

    public void setMoveState() {
        currentState = moveState;
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
