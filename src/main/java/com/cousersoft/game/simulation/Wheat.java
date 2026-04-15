package com.cousersoft.game.simulation;

public class Wheat extends Crop {

    public Wheat() {
        super(1, 10); // Water required: 1, Base value: 10
    }

    @Override
    public void grow() {
        if (stage == GrowthStage.DEAD) return;
        
        daysPlanted++;
        if (daysPlanted >= 2 && stage == GrowthStage.SEED) {
            stage = GrowthStage.SEEDLING;
        } else if (daysPlanted >= 4 && stage == GrowthStage.SEEDLING) {
            stage = GrowthStage.MATURE;
        }
        
        checkSurvival();
    }

    @Override
    public boolean consumeResources(FarmCell cell, Weather weather) {
        if (stage == GrowthStage.DEAD) return false;

        // Wheat is resistant: no extra water consumption during Heat Waves
        int actualWaterReq = waterRequired;
        
        if (cell.getMoistureLevel() >= actualWaterReq) {
            cell.setMoistureLevel(cell.getMoistureLevel() - actualWaterReq);
            consecutiveDaysWithoutWater = 0;
            return true;
        } else {
            consecutiveDaysWithoutWater++;
            return false;
        }
    }
}
