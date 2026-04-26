package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

public class Pumpkin extends Crop {
    public Pumpkin() {
        super(12, 100);
    }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead1;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sPumpkin1;
            case STAGE2 -> Sprite.sPumpkin2;
            case STAGE3 -> Sprite.sPumpkin3;
            case MATURE -> Sprite.sPumpkin4;
            default -> Sprite.sSeedGlobal;
        };
    }
}
