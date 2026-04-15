package com.cousersoft.game.simulation;

public class HeatWave implements Weather {
    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() - 15);
    }

    @Override
    public String getName() {
        return "Heat Wave";
    }
}
