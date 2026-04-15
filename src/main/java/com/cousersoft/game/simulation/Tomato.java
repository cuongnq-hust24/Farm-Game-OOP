package com.cousersoft.game.simulation;

public class Tomato extends Crop {

    public Tomato() {
        super(2, 25); // Water required: 2, Base value: 25
    }

    @Override
    public void grow() {
        if (stage == GrowthStage.DEAD) return;
        
        daysPlanted++;
        if (daysPlanted >= 3 && stage == GrowthStage.SEED) {
            stage = GrowthStage.SEEDLING;
        } else if (daysPlanted >= 6 && stage == GrowthStage.SEEDLING) {
            stage = GrowthStage.MATURE;
        }
        
        checkSurvival();
    }

    @Override
    public boolean consumeResources(FarmCell cell, Weather weather) {
        if (stage == GrowthStage.DEAD) return false;

        // Tomato is sensitive: extra water consumption during Heat Waves
        int actualWaterReq = waterRequired;
        if (weather instanceof HeatWave) {
            actualWaterReq += 1;
        }

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
