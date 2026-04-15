package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

public class Chili extends Crop {
    public Chili() {
        super(8, 38);
    }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead1;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sChili1;
            case STAGE2 -> Sprite.sChili2;
            case STAGE3 -> Sprite.sChili3;
            case MATURE -> Sprite.sChili4;
            default -> Sprite.sSeedGlobal;
        };
    }
}
