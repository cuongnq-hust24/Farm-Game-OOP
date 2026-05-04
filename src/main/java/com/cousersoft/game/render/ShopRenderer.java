package com.cousersoft.game.render;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.simulation.CropData;

import static com.cousersoft.game.GameConstants.*;

public class ShopRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        // Draw blurred background
        ctx.screen.renderSprite(0, 0, Sprite.bgBlur, false);

        // Title Board centered at top
        ctx.screen.renderSprite(SHOP_TITLE_X, SHOP_TITLE_Y, Sprite.titleBoard, false);
        // Title text centered on the board
        ctx.guiFont.render(ctx.screen, "SEED SHOP", SHOP_TITLE_TEXT_X, SHOP_TITLE_TEXT_Y, COLOR_BOARD_TEXT, 1, false, false);

        int totalPages = (ctx.cropCatalog.length + CROPS_PER_PAGE - 1) / CROPS_PER_PAGE;

        for (int i = 0; i < CROPS_PER_PAGE; i++) {
            int cropIdx = ctx.shopPage * CROPS_PER_PAGE + i;
            if (cropIdx >= ctx.cropCatalog.length) break;

            CropData crop = ctx.cropCatalog[cropIdx];

            // Render wooden board
            ctx.screen.renderSprite(SHOP_BOARD_X, SHOP_BOARD_YS[i], Sprite.woodenBoard, false);

            // Render crop sprite on the left side of the board (centered vertically)
            int spriteX = SHOP_BOARD_X + SHOP_SPRITE_OFFSET_X;
            int spriteY = SHOP_BOARD_YS[i] + SHOP_SPRITE_OFFSET_Y;
            ctx.screen.renderSprite(spriteX, spriteY, crop.matureSprite, false);

            // Render text info on the right side
            int textX = SHOP_BOARD_X + SHOP_TEXT_OFFSET_X;
            int textBaseY = SHOP_BOARD_YS[i] + SHOP_TEXT_OFFSET_Y;

            // Line 1: Name and cost
            String line1 = crop.name.toUpperCase() + "  -  $" + crop.cost;
            ctx.guiFont.render(ctx.screen, line1, textX, textBaseY, COLOR_BOARD_TEXT, 1, false, false);

            // Line 2: Growth time
            String line2 = "GROWS: " + crop.growthDays + " DAYS";
            ctx.guiFont.render(ctx.screen, line2, textX, textBaseY + SHOP_TEXT_LINE_SPACING, COLOR_BOARD_TEXT, 1, false, false);

            // Line 3: Water need and harvest value
            String line3 = "WATER: " + crop.dailyWater + "/D  SELL: $" + crop.harvestValue;
            ctx.guiFont.render(ctx.screen, line3, textX, textBaseY + SHOP_TEXT_LINE_SPACING * 2, COLOR_BOARD_TEXT, 1, false, false);
        }

        // Navigation buttons
        ctx.screen.renderSprite(SHOP_PREV_X, SHOP_NAV_Y, Sprite.prevPage, false);
        ctx.screen.renderSprite(SHOP_NEXT_X, SHOP_NAV_Y, Sprite.nextPage, false);

        // Page indicator
        String pageText = "PAGE " + (ctx.shopPage + 1) + "/" + totalPages;
        ctx.guiFont.render(ctx.screen, pageText, SHOP_PAGE_TEXT_X, SHOP_PAGE_TEXT_Y, COLOR_BOARD_TEXT, 1, false, false);

        // Hint text
        ctx.guiFont.render(ctx.screen, "CLICK TO EQUIP  |  P/ESC: CLOSE", SHOP_HINT_X, SHOP_HINT_Y, COLOR_BOARD_TEXT, 1, false, false);
    }
}
