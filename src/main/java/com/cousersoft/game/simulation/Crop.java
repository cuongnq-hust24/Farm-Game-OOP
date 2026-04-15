package com.cousersoft.game.simulation;

public abstract class Crop {
    protected GrowthStage stage;
    protected int daysPlanted;
    protected int waterRequired;
    protected int baseValue;
    protected int consecutiveDaysWithoutWater;

    public Crop(int waterRequired, int baseValue) {
        this.stage = GrowthStage.SEED;
        this.daysPlanted = 0;
        this.waterRequired = waterRequired;
        this.baseValue = baseValue;
        this.consecutiveDaysWithoutWater = 0;
    }

    public abstract void grow();

    /**
     * Consumes resources from the cell.
     * @param cell The farm cell this crop is planted in.
     * @param weather The current weather event.
     * @return true if resources were successfully consumed, false otherwise.
     */
    public abstract boolean consumeResources(FarmCell cell, Weather weather);

    public int harvest() {
        if (stage == GrowthStage.MATURE) {
            return baseValue;
        }
        return 0;
    }

    public GrowthStage getStage() {
        return stage;
    }

    protected void checkSurvival() {
        if (consecutiveDaysWithoutWater >= 2) {
            stage = GrowthStage.DEAD;
        }
    }
}
