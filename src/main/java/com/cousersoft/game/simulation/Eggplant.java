package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

public class Eggplant extends Crop {
    public Eggplant() {
        super(10, 55);
    }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead1;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sEggplant1;
            case STAGE2 -> Sprite.sEggplant2;
            case STAGE3 -> Sprite.sEggplant3;
            case MATURE -> Sprite.sEggplant4;
            default -> Sprite.sSeedGlobal;
        };
    }
}
