package com.cousersoft.game.model.weather;

import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.model.FarmCell;

public class HeatWave extends Sunny {

    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() - 15);
    }

    @Override
    public String getName() {
        return "HeatWave";
    }

    @Override
    public Sprite getGrassSprite(int baseSeed) {
        int variant = baseSeed % 3;
        return switch (variant) {
            case 0  -> Sprite.nGrassDry1;
            case 1  -> Sprite.nGrassDry2;
            default -> Sprite.nGrassDry3;
        };
    }

    @Override
    public Sprite getFarmlandSprite(boolean u, boolean d, boolean l, boolean r, int moisture) {
        int variant = getMoistureVariant(moisture);
        if (!u && !l) return pick(variant, Sprite.fHeatTopLeft,   Sprite.fHeatTopLeftWet1,   Sprite.fHeatTopLeftWet2);
        if (!u && !r) return pick(variant, Sprite.fHeatTopRight,  Sprite.fHeatTopRightWet1,  Sprite.fHeatTopRightWet2);
        if (!d && !l) return pick(variant, Sprite.fHeatBotLeft,   Sprite.fHeatBotLeftWet1,   Sprite.fHeatBotLeftWet2);
        if (!d && !r) return pick(variant, Sprite.fHeatBotRight,  Sprite.fHeatBotRightWet1,  Sprite.fHeatBotRightWet2);
        if (!u)       return pick(variant, Sprite.fHeatTop,       Sprite.fHeatTopWet1,       Sprite.fHeatTopWet2);
        if (!d)       return pick(variant, Sprite.fHeatBot,       Sprite.fHeatBotWet1,       Sprite.fHeatBotWet2);
        if (!l)       return pick(variant, Sprite.fHeatLeft,      Sprite.fHeatLeftWet1,      Sprite.fHeatLeftWet2);
        if (!r)       return pick(variant, Sprite.fHeatRight,     Sprite.fHeatRightWet1,     Sprite.fHeatRightWet2);
        return         pick(variant, Sprite.fHeatCenter,    Sprite.fHeatCenterWet1,    Sprite.fHeatCenterWet2);
    }
}
