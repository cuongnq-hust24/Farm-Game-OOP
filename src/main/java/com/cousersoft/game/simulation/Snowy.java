package com.cousersoft.game.simulation;

public class Snowy implements Weather {
    @Override
    public void apply(FarmCell cell) {
        // Snow has the same moisture mechanics as Rain (+20 moisture)
        cell.setMoistureLevel(cell.getMoistureLevel() + 20);
    }

    @Override
    public String getName() {
        return "Snowy";
    }
}
