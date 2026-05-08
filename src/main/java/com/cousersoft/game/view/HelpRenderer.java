package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;

import static com.cousersoft.game.GameConstants.*;

/**
 * VIEW — Renders the help / how-to-play screen.
 * P1 FIX: No Game.width reference; uses SCREEN_WIDTH/SCREEN_HEIGHT constants.
 */
public class HelpRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        ctx.screen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, COLOR_MENU_BG);

        ctx.guiFont.render(ctx.screen, "HOW TO PLAY", 165, 15, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "-----------------------------------", 100, 25, COLOR_TEXT_GRAY, 1, true, false);

        int y = 40;
        int x = 30;

        ctx.guiFont.render(ctx.screen, "1. LEFT CLICK to use selected tool",        x, y,        COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "2. 1-5 keys or click HUD to select tools",  x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "3. P key to open Seed Shop",                x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "4. TAB to cycle through equipped seeds",    x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "5. ENTER or click Adv Day to end turn",     x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "6. ARROWS/SPACE for keyboard play",         x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "7. [ and ] to adjust window scale",         x, y += 15,  COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "8. Plant needs water EVERY DAY",            x, y += 15,  COLOR_WHITE, 1, true, false);

        // Back Button
        ctx.screen.fillRect(MENU_BTN_X, 190, MENU_BTN_W, MENU_BTN_H, COLOR_BTN_BLUE);
        ctx.screen.renderOutline(MENU_BTN_X, 190, MENU_BTN_W, MENU_BTN_H, COLOR_OUTLINE_WHITE);
        ctx.guiFont.render(ctx.screen, "BACK", MENU_BTN_X + 38, 192, COLOR_WHITE, 1, true, false);
    }
}
