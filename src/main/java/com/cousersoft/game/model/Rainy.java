package com.cousersoft.game.model;

import com.cousersoft.game.graphics.Sprite;

public class Rainy extends Sunny {

    @Override
    public void apply(FarmCell cell) {
        cell.setMoistureLevel(cell.getMoistureLevel() + 20);
    }

    @Override
    public String getName() {
        return "Rainy";
    }

    @Override
    public Sprite getGrassSprite(int baseSeed) {
        // 70% chance of wet variants (4 & 5), 30% chance of normal variants
        int chance = baseSeed % 10;
        if (chance < 7) {
            return (baseSeed % 2 == 0) ? Sprite.nGrass4 : Sprite.nGrass5;
        } else {
            int variant = baseSeed % 3;
            return switch (variant) {
                case 0  -> Sprite.nGrass1;
                case 1  -> Sprite.nGrass2;
                default -> Sprite.nGrass3;
            };
        }
    }

    // Farmland sprites — reuse Sunny's normal farmland (rain soaks it, shown via moisture variant)
    @Override
    public Sprite getFarmlandSprite(boolean u, boolean d, boolean l, boolean r, int moisture) {
        return super.getFarmlandSprite(u, d, l, r, moisture);
    }
}
