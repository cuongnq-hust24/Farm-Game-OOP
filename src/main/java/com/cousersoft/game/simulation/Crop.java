package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

public abstract class Crop {
    protected GrowthStage stage;
    protected int daysPlanted;
    protected int growthTime;
    protected int baseValue;
    protected int consecutiveDaysWithoutWater;

    public Crop(int growthTime, int baseValue) {
        this.stage = GrowthStage.SEED;
        this.daysPlanted = 0;
        this.growthTime = growthTime;
        this.baseValue = baseValue;
        this.consecutiveDaysWithoutWater = 0;
    }

    public void grow() {
        if (stage == GrowthStage.DEAD || stage == GrowthStage.MATURE) return;
        
        daysPlanted++;
        
        double progress = (double) daysPlanted / growthTime;
        if (progress >= 1.0) stage = GrowthStage.MATURE;
        else if (progress >= 0.75) stage = GrowthStage.STAGE3;
        else if (progress >= 0.50) stage = GrowthStage.STAGE2;
        else if (progress >= 0.25) stage = GrowthStage.STAGE1;
        
        checkSurvival();
    }

    public boolean consumeResources(FarmCell cell, Weather weather) {
        if (stage == GrowthStage.DEAD) return false;

        // Base requirement: 10 units of moisture per day
        int waterReq = 10;
        if (weather instanceof HeatWave) waterReq = 20;

        if (cell.getMoistureLevel() >= waterReq) {
            cell.setMoistureLevel(cell.getMoistureLevel() - waterReq);
            consecutiveDaysWithoutWater = 0;
            return true;
        } else {
            consecutiveDaysWithoutWater++;
            return false;
        }
    }

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

    public abstract Sprite getSprite();
}
