package com.cousersoft.game;

import com.cousersoft.game.graphics.Screen;
import com.cousersoft.game.graphics.text.BitmapFont;
import com.cousersoft.game.input.Keyboard;
import com.cousersoft.game.input.Mouse;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.simulation.CropData;
import com.cousersoft.game.simulation.FarmGrid;

import static com.cousersoft.game.GameConstants.STARTING_BALANCE;
import static com.cousersoft.game.GameConstants.STARTING_DAY;
import static com.cousersoft.game.GameConstants.DEFAULT_SCALE;

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

    // Animation/Weather State
    public int tickCounter = 0;

    // Shop & Crop State
    public int shopPage = 0;
    public int seedIndex = 0;
    public CropData[] cropCatalog;

    // UI state
    public boolean showQuitConfirm = false;

    // Temporary arrays until Step 8
    public String[] seedNames = { "Rice", "Cabbage", "Corn", "Carrot", "Radish", "Tomato", "Pumpkin", "Eggplant", "Chili", "Pepper" };
    public int[] seedCosts = { 5, 8, 10, 6, 7, 12, 25, 15, 10, 12 };
    public com.cousersoft.game.graphics.Sprite[] matureSprites = {
            com.cousersoft.game.graphics.Sprite.sRice4, com.cousersoft.game.graphics.Sprite.sCabbage4, com.cousersoft.game.graphics.Sprite.sCorn4, 
            com.cousersoft.game.graphics.Sprite.sCarrot4, com.cousersoft.game.graphics.Sprite.sRadish4, com.cousersoft.game.graphics.Sprite.sTomato4, 
            com.cousersoft.game.graphics.Sprite.sPumpkin4, com.cousersoft.game.graphics.Sprite.sEggplant4, com.cousersoft.game.graphics.Sprite.sChili4, 
            com.cousersoft.game.graphics.Sprite.sPepper4
    };

    public void reset() {
        day = STARTING_DAY;
        balance = STARTING_BALANCE;
        selectedX = -1;
        selectedY = -1;
        selectedTool = Tool.NONE;
        message = "Welcome to Smart Farm!";
        grid = new FarmGrid(com.cousersoft.game.GameConstants.GRID_ROWS, com.cousersoft.game.GameConstants.GRID_COLS);
        showQuitConfirm = false;
    }
}
