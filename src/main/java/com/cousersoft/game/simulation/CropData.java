package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

/**
 * Centralized data class holding all crop statistics.
 * Used by the Shop UI to display information and by the game logic for planting.
 */
public class CropData {
    public final String name;
    public final int cost;
    public final int growthDays;
    public final int dailyWater;
    public final int harvestValue;
    public final Sprite matureSprite;

    public CropData(String name, int cost, int growthDays, int dailyWater, int harvestValue, Sprite matureSprite) {
        this.name = name;
        this.cost = cost;
        this.growthDays = growthDays;
        this.dailyWater = dailyWater;
        this.harvestValue = harvestValue;
        this.matureSprite = matureSprite;
    }

    /**
     * Returns the full catalog of all 10 crops in the game.
     */
    public static CropData[] getAllCrops() {
        return new CropData[] {
            new CropData("Rice",       5,  5,  10, 15,  Sprite.sRice4),
            new CropData("Cabbage",    8,  7,  10, 25,  Sprite.sCabbage4),
            new CropData("Corn",      10,  8,  10, 35,  Sprite.sCorn4),
            new CropData("Carrot",     6,  6,  10, 20,  Sprite.sCarrot4),
            new CropData("Radish",     7,  6,  10, 22,  Sprite.sRadish4),
            new CropData("Tomato",    12,  8,  10, 40,  Sprite.sTomato4),
            new CropData("Pumpkin",   25, 12,  10, 100, Sprite.sPumpkin4),
            new CropData("Eggplant",  15, 10,  10, 60,  Sprite.sEggplant4),
            new CropData("Chili",     10,  9,  10, 45,  Sprite.sChili4),
            new CropData("Pepper",    12,  9,  10, 45,  Sprite.sPepper4),
        };
    }
}
