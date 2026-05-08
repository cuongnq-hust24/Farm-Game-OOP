package com.cousersoft.game;

/**
 * P3 FIX: enterState() and exitState() stubs removed.
 * The class now only exposes the clean public API it actually implements.
 */
public class StateHandler {

    private GameState currentState = GameState.MENU;
    private GameState lastState;

    public GameState getState() {
        return currentState;
    }

    public void setState(GameState state) {
        lastState = currentState;
        currentState = state;
    }

    /** Returns the state active before the last transition. */
    public GameState getLastState() {
        return lastState;
    }
}
