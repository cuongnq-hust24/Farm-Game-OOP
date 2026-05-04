package com.cousersoft.game.input;

import java.util.HashSet;
import java.util.Set;

/**
 * Converts continuous key/mouse states into one-shot actions by managing debounce flags.
 * Replaces the scattered boolean *Triggered flags throughout the game logic.
 */
public class InputManager {
    
    private final Set<String> triggeredActions = new HashSet<>();

    /**
     * Checks if an action is newly triggered. 
     * Returns true ONLY on the first frame the input is active.
     * 
     * @param isActive The current raw state of the key/mouse (e.g., keyboard.enter)
     * @param actionId A unique string identifier for the action (e.g., "enter")
     * @return true if the action was just pressed
     */
    public boolean isJustPressed(boolean isActive, String actionId) {
        if (isActive) {
            if (!triggeredActions.contains(actionId)) {
                triggeredActions.add(actionId);
                return true;
            }
            return false;
        } else {
            triggeredActions.remove(actionId);
            return false;
        }
    }
}
