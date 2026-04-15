package com.cousersoft.game.simulation;

public class Rainy implements Weather {
    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() + 20);
    }

    @Override
    public String getName() {
        return "Rainy";
    }
}
