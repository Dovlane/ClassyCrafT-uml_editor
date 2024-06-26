package raf.dsw.classycraft.app.model.StatePattern;

import raf.dsw.classycraft.app.model.StatePattern.concrete.*;

public class StateManager {

    private State currentState;
    private final AddInterclassState addInterclassState;
    private final AddConnectionState addConnectionState;
    private final AddClassContentState addClassContentState;
    private final MoveState moveState;
    private final ZoomState zoomState;
    private final RemoveElementState removeElementState;
    private final SelectElementState selectElementState;
    private final DuplicateElementState duplicateElementState;

    public StateManager() {

        // Init states
        addInterclassState = new AddInterclassState();
        addConnectionState = new AddConnectionState();
        addClassContentState = new AddClassContentState();
        moveState = new MoveState();
        zoomState = new ZoomState();
        removeElementState = new RemoveElementState();
        selectElementState = new SelectElementState();
        duplicateElementState = new DuplicateElementState();

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

    public void setZoomState() {
        currentState = zoomState;
    }

    public void setRemoveElementState() {
        currentState = removeElementState;
    }

    public void setSelectElementState() {
        currentState = selectElementState;
    }

    public void setDuplicateElementState() {
        currentState = duplicateElementState;
    }


    // Getters
    public State getCurrentState() {
        return currentState;
    }

}
