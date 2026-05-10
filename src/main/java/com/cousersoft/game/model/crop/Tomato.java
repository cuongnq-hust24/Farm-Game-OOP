package com.cousersoft.game.model.crop;

import com.cousersoft.game.graphics.Sprite;

public class Tomato extends Crop {
    public Tomato() { super(8, 40); }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead2;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sTomato1;
            case STAGE2 -> Sprite.sTomato2;
            case STAGE3 -> Sprite.sTomato3;
            case MATURE -> Sprite.sTomato4;
            default     -> Sprite.sSeedGlobal;
        };
    }
}
