package com.cousersoft.game;

public class StateHandler {
	
	private GameState currentState = GameState.MENU;
	private GameState lastState;
	
	
	private void enterState(GameState state) {
	}
	
	private void exitState(GameState lastState) {
	}
	
	public GameState getState() {
		return currentState;
	}
	
	public void setState(GameState state) {
		lastState = currentState;
		currentState = state;
	}
}
