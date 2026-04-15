package com.cousersoft.game.simulation;

public class Sunny implements Weather {
    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() - 5);
    }

    @Override
    public String getName() {
        return "Sunny";
    }
}
