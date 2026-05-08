package com.cousersoft.game.model;

import com.cousersoft.game.graphics.Sprite;

public class Rice extends Crop {
    public Rice() { super(5, 15); }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead2;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sRice1;
            case STAGE2 -> Sprite.sRice2;
            case STAGE3 -> Sprite.sRice3;
            case MATURE -> Sprite.sRice4;
            default     -> Sprite.sSeedGlobal;
        };
    }
}
