package com.cousersoft.game;

import com.cousersoft.game.graphics.Screen;
import com.cousersoft.game.graphics.text.BitmapFont;
import com.cousersoft.game.input.Keyboard;
import com.cousersoft.game.input.Mouse;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.model.CropData;
import com.cousersoft.game.model.FarmGrid;

import static com.cousersoft.game.GameConstants.*;

/**
 * Lightweight data object holding references to all shared subsystems and mutable game state.
 * This replaces the practice of passing the Game instance everywhere.
 */
public class GameContext {
    public Screen screen;
    public StateHandler handler;
    public Mouse mouse;
    public Keyboard keyboard;
    public BitmapFont guiFont;
    public FarmGrid grid;

    public int scale = DEFAULT_SCALE;
    public boolean scaleChanged = false;

    // Mutable Game State
    public int day = STARTING_DAY;
    public int balance = STARTING_BALANCE;
    public int selectedX = -1;
    public int selectedY = -1;
    public Tool selectedTool = Tool.NONE;
    public String message = "Welcome to Smart Farm!";

    // Animation / Weather State
    public int tickCounter = 0;

    // Shop & Crop State
    public int shopPage = 0;
    public int seedIndex = 0;
    public CropData[] cropCatalog;

    /** Called by GameLauncher when the window needs to be resized (scale changed). */
    public Runnable scaleChangedCallback;

    // UI state
    public boolean showQuitConfirm = false;

    public void reset() {
        day = STARTING_DAY;
        balance = STARTING_BALANCE;
        selectedX = -1;
        selectedY = -1;
        selectedTool = Tool.NONE;
        message = "Welcome to Smart Farm!";
        grid = new FarmGrid(GRID_ROWS, GRID_COLS);
        showQuitConfirm = false;
    }
}
