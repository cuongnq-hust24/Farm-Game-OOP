package com.cousersoft.game.model;

import com.cousersoft.game.graphics.Sprite;
import java.util.function.Supplier;

/**
 * Centralized data class holding all crop statistics.
 * Used by the Shop UI to display information and by the game logic for planting.
 *
 * P1 FIX: Added {@code createCrop()} factory method backed by a {@link Supplier},
 * replacing the hard-coded {@code switch(seedIndex)} in GameUpdater (Controller).
 */
public class CropData {
    public final String name;
    public final int cost;
    public final int growthDays;
    public final int dailyWater;
    public final int harvestValue;
    public final Sprite matureSprite;
    private final Supplier<Crop> factory;

    public CropData(String name, int cost, int growthDays, int dailyWater,
                    int harvestValue, Sprite matureSprite, Supplier<Crop> factory) {
        this.name = name;
        this.cost = cost;
        this.growthDays = growthDays;
        this.dailyWater = dailyWater;
        this.harvestValue = harvestValue;
        this.matureSprite = matureSprite;
        this.factory = factory;
    }

    /**
     * Creates and returns a fresh instance of this crop type.
     * Eliminates the need for a hard-coded switch in the Controller.
     */
    public Crop createCrop() {
        return factory.get();
    }

    /**
     * Returns the full catalog of all 10 crops in the game.
     */
    public static CropData[] getAllCrops() {
        return new CropData[] {
            new CropData("Rice",       5,  5,  10, 15,  Sprite.sRice4,     Rice::new),
            new CropData("Cabbage",    8,  7,  10, 25,  Sprite.sCabbage4,  Cabbage::new),
            new CropData("Corn",      10,  8,  10, 35,  Sprite.sCorn4,     Corn::new),
            new CropData("Carrot",     6,  6,  10, 20,  Sprite.sCarrot4,   Carrot::new),
            new CropData("Radish",     7,  6,  10, 22,  Sprite.sRadish4,   WhiteRadish::new),
            new CropData("Tomato",    12,  8,  10, 40,  Sprite.sTomato4,   Tomato::new),
            new CropData("Pumpkin",   25, 12,  10, 100, Sprite.sPumpkin4,  Pumpkin::new),
            new CropData("Eggplant",  15, 10,  10, 60,  Sprite.sEggplant4, Eggplant::new),
            new CropData("Chili",     10,  9,  10, 45,  Sprite.sChili4,    Chili::new),
            new CropData("Pepper",    12,  9,  10, 45,  Sprite.sPepper4,   Pepper::new),
        };
    }
}
