package com.cousersoft.game.model.crop;

import com.cousersoft.game.graphics.Sprite;

public class Pepper extends Crop {
    public Pepper() { super(9, 45); }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead2;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sPepper1;
            case STAGE2 -> Sprite.sPepper2;
            case STAGE3 -> Sprite.sPepper3;
            case MATURE -> Sprite.sPepper4;
            default     -> Sprite.sSeedGlobal;
        };
    }
}
