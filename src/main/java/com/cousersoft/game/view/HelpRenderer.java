package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;

import static com.cousersoft.game.GameConstants.*;

/**
 * VIEW — Renders the help / how-to-play screen.
 * P1 FIX: No Game.width reference; uses SCREEN_WIDTH/SCREEN_HEIGHT constants.
 */
public class HelpRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        ctx.screen.renderSprite(0, 0, Sprite.bgBlur, false);
        ctx.screen.renderSprite(46, 35, Sprite.helpBoard, false);

        // Title board
        ctx.screen.renderSprite(155, 5, Sprite.titleBoard, false);
        ctx.guiFont.render(ctx.screen, "HELP", 188, 15, COLOR_WHITE, 1, true, false);

        int y = 50;
        int x = 60;

        ctx.guiFont.render(ctx.screen, "1. LEFT CLICK to use selected tool",        x, y,        COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "2. 1-5 keys or click HUD to select tools",  x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "3. P key to open Seed Shop",                x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "4. TAB to cycle through equipped seeds",    x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "5. ENTER or click Adv Day to end turn",     x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "6. ARROWS/SPACE for keyboard play",         x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "7. [ and ] to adjust window scale",         x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "8. Plant needs water EVERY DAY",            x, y += 15,  COLOR_WHITE, 1, true, false);

        // Back Button
        ctx.screen.renderSprite(155, 190, Sprite.smallWoodenBoard, false);
        ctx.guiFont.render(ctx.screen, "BACK", 188, 200, COLOR_WHITE, 1, true, false);
    }
}
