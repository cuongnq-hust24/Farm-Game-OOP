package com.cousersoft.game.model.weather;

import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.model.FarmCell;

/**
 * Defines the contract for all weather types.
 * Each weather knows how to:
 *  - apply its daily effect to a FarmCell
 *  - return its display name
 *  - supply the correct grass/farmland sprites (keeping sprite logic out of the View)
 */
public interface Weather {
    void apply(FarmCell cell);
    String getName();

    // ── Sprite selection methods (moved from GameRenderer) ──────────────────

    /**
     * Returns the appropriate grass tile sprite for a non-farmland cell.
     * @param baseSeed  a deterministic hash derived from tile coordinates
     */
    Sprite getGrassSprite(int baseSeed);

    /**
     * Returns the appropriate farmland tile sprite based on adjacency and moisture.
     *
     * @param up       is the tile above also farmland?
     * @param down     is the tile below also farmland?
     * @param left     is the tile to the left also farmland?
     * @param right    is the tile to the right also farmland?
     * @param moisture moisture level of this cell (0-100)
     */
    Sprite getFarmlandSprite(boolean up, boolean down, boolean left, boolean right, int moisture);
}
