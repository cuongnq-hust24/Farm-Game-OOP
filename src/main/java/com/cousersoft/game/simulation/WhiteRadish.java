package com.cousersoft.game.simulation;

import com.cousersoft.game.graphics.Sprite;

public class WhiteRadish extends Crop {
    public WhiteRadish() {
        super(6, 22);
    }

    @Override
    public Sprite getSprite() {
        if (stage == GrowthStage.DEAD) return Sprite.sDead1;
        if (stage == GrowthStage.SEED) return Sprite.sSeedGlobal;
        return switch (stage) {
            case STAGE1 -> Sprite.sRadish1;
            case STAGE2 -> Sprite.sRadish2;
            case STAGE3 -> Sprite.sRadish3;
            case MATURE -> Sprite.sRadish4;
            default -> Sprite.sSeedGlobal;
        };
    }
}
