package com.cousersoft.game.model.weather;

import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.model.FarmCell;

public class Sunny implements Weather {

    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() - 5);
    }

    @Override
    public String getName() {
        return "Sunny";
    }

    @Override
    public Sprite getGrassSprite(int baseSeed) {
        int variant = baseSeed % 3;
        return switch (variant) {
            case 0 -> Sprite.nGrass1;
            case 1 -> Sprite.nGrass2;
            default -> Sprite.nGrass3;
        };
    }

    @Override
    public Sprite getFarmlandSprite(boolean u, boolean d, boolean l, boolean r, int moisture) {
        int variant = getMoistureVariant(moisture);
        if (!u && !l) return pick(variant, Sprite.fTopLeft,    Sprite.fTopLeftWet1,    Sprite.fTopLeftWet2);
        if (!u && !r) return pick(variant, Sprite.fTopRight,   Sprite.fTopRightWet1,   Sprite.fTopRightWet2);
        if (!d && !l) return pick(variant, Sprite.fBotLeft,    Sprite.fBotLeftWet1,    Sprite.fBotLeftWet2);
        if (!d && !r) return pick(variant, Sprite.fBotRight,   Sprite.fBotRightWet1,   Sprite.fBotRightWet2);
        if (!u)       return pick(variant, Sprite.fTop,        Sprite.fTopWet1,        Sprite.fTopWet2);
        if (!d)       return pick(variant, Sprite.fBot,        Sprite.fBotWet1,        Sprite.fBotWet2);
        if (!l)       return pick(variant, Sprite.fLeft,       Sprite.fLeftWet1,       Sprite.fLeftWet2);
        if (!r)       return pick(variant, Sprite.fRight,      Sprite.fRightWet1,      Sprite.fRightWet2);
        return         pick(variant, Sprite.fCenter,     Sprite.fCenterWet1,     Sprite.fCenterWet2);
    }

    // ── helpers ─────────────────────────────────────────────────────────────

    protected static int getMoistureVariant(int moisture) {
        if (moisture >= 80) return 2;
        if (moisture >= 40) return 1;
        return 0;
    }

    protected static Sprite pick(int variant, Sprite dry, Sprite wet1, Sprite wet2) {
        return switch (variant) {
            case 1  -> wet1;
            case 2  -> wet2;
            default -> dry;
        };
    }
}
