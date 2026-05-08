package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;

import static com.cousersoft.game.GameConstants.*;

/**
 * VIEW — Renders the main menu screen.
 * P1 FIX: No Game.width reference; uses SCREEN_WIDTH/SCREEN_HEIGHT constants.
 */
public class MenuRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        ctx.screen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, COLOR_MENU_BG);

        ctx.guiFont.render(ctx.screen, "SMART FARM SIMULATOR", 140, 40, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "CROP GROWTH AND RESOURCE MANAGEMENT", 100, 50, COLOR_SUBTITLE, 1, true, false);

        renderMenuButton(ctx, MENU_BTN_X, MENU_START_Y, MENU_BTN_W, MENU_BTN_H, "START GAME", COLOR_BTN_GREEN);
        renderMenuButton(ctx, MENU_BTN_X, MENU_HELP_Y,  MENU_BTN_W, MENU_BTN_H, "HELP",       COLOR_BTN_BLUE);
        renderMenuButton(ctx, MENU_BTN_X, MENU_QUIT_Y,  MENU_BTN_W, MENU_BTN_H, "QUIT",       COLOR_BTN_RED);

        ctx.guiFont.render(ctx.screen, "PRESS ENTER TO START", 140, 170, COLOR_WHITE, 1, true, false);

        if (ctx.showQuitConfirm) {
            ctx.screen.fillRect(100, 85, 200, 40, COLOR_OVERLAY_BLACK);
            ctx.screen.renderOutline(100, 85, 200, 40, COLOR_OUTLINE_WHITE);
            ctx.guiFont.render(ctx.screen, "REALLY QUIT?",       165, 95,  COLOR_WHITE,     1, true, false);
            ctx.guiFont.render(ctx.screen, "Y = YES   ESC = NO", 150, 110, COLOR_TEXT_GRAY, 1, true, false);
        }
    }

    private void renderMenuButton(GameContext ctx, int x, int y, int w, int h, String text, int color) {
        ctx.screen.fillRect(x, y, w, h, color);
        ctx.screen.renderOutline(x, y, w, h, COLOR_OUTLINE_WHITE);
        int textX = x + (w - text.length() * 6) / 2;
        ctx.guiFont.render(ctx.screen, text, textX, y + 2, COLOR_WHITE, 1, true, false);
    }
}
