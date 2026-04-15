package com.cousersoft.game.simulation;

public interface Weather {
    void apply(FarmCell cell);
    String getName();
}
