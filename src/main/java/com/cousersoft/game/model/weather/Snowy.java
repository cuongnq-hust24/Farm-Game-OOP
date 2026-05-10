package com.cousersoft.game.model.weather;

import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.model.FarmCell;

public class Snowy extends Sunny {

    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() + 10);
    }

    @Override
    public String getName() {
        return "Snowy";
    }

    @Override
    public Sprite getGrassSprite(int baseSeed) {
        int variant = baseSeed % 3;
        return switch (variant) {
            case 0  -> Sprite.nGrassWinter3;
            case 1  -> Sprite.nGrassWinter4;
            default -> Sprite.nGrassWinter5;
        };
    }

    @Override
    public Sprite getFarmlandSprite(boolean u, boolean d, boolean l, boolean r, int moisture) {
        // Snow farmland ignores moisture variant — only one visual style
        if (!u && !l) return Sprite.fSnowTopLeft;
        if (!u && !r) return Sprite.fSnowTopRight;
        if (!d && !l) return Sprite.fSnowBotLeft;
        if (!d && !r) return Sprite.fSnowBotRight;
        if (!u)       return Sprite.fSnowTop;
        if (!d)       return Sprite.fSnowBot;
        if (!l)       return Sprite.fSnowLeft;
        if (!r)       return Sprite.fSnowRight;
        return Sprite.fSnowCenter;
    }
}
