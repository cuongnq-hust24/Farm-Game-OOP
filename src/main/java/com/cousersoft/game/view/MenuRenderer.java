package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;

import static com.cousersoft.game.GameConstants.*;

/**
 * VIEW — Renders the main menu screen.
 * Shows a CONTINUE button when the player has an active game session.
 */
public class MenuRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        ctx.screen.renderSprite(0, 0, Sprite.bgBlur, false);

        // Title board
        ctx.screen.renderSprite(46, 15, Sprite.woodenBoard, false);
        ctx.guiFont.render(ctx.screen, "SMART FARM SIMULATOR", 80, 27, COLOR_WHITE, 2, true, false);

        if (ctx.hasActiveGame) {
            renderMenuButton(ctx, 155, 70,  "CONTINUE");
            renderMenuButton(ctx, 155, 100, "NEW GAME");
            renderMenuButton(ctx, 155, 130, "HELP");
            renderMenuButton(ctx, 155, 160, "QUIT");
        } else {
            renderMenuButton(ctx, 155, 70,  "START GAME");
            renderMenuButton(ctx, 155, 105, "HELP");
            renderMenuButton(ctx, 155, 140, "QUIT");
            ctx.guiFont.render(ctx.screen, "PRESS ENTER TO START", 140, 175, COLOR_WHITE, 1, true, false);
        }

        if (ctx.showQuitConfirm) {
            ctx.screen.fillRect(100, 85, 200, 40, COLOR_OVERLAY_BLACK);
            ctx.screen.renderOutline(100, 85, 200, 40, COLOR_OUTLINE_WHITE);
            ctx.guiFont.render(ctx.screen, "REALLY QUIT?",       165, 95,  COLOR_WHITE,     1, true, false);
            ctx.guiFont.render(ctx.screen, "Y = YES   ESC = NO", 150, 110, COLOR_TEXT_GRAY, 1, true, false);
        }
    }

    private void renderMenuButton(GameContext ctx, int x, int y, String text) {
        ctx.screen.renderSprite(x, y, Sprite.smallWoodenBoard, false);
        int textX = x + (90 - text.length() * 6) / 2;
        ctx.guiFont.render(ctx.screen, text, textX, y + 10, COLOR_WHITE, 1, true, false);
    }
}
