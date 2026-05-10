package com.cousersoft.game.model.crop;

import com.cousersoft.game.graphics.Sprite;

public class Cabbage extends Crop {
    public Cabbage() { super(6, 25); }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead1;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sCabbage1;
            case STAGE2 -> Sprite.sCabbage2;
            case STAGE3 -> Sprite.sCabbage3;
            case MATURE -> Sprite.sCabbage4;
            default     -> Sprite.sSeedGlobal;
        };
    }
}
