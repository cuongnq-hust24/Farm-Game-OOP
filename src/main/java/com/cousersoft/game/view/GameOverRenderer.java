package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;

import static com.cousersoft.game.GameConstants.*;

public class GameOverRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        ctx.screen.renderSprite(0, 0, Sprite.bgBlur, false);

        // Draw "GAME OVER" board
        ctx.screen.renderSprite(46, 60, Sprite.woodenBoard, false);
        ctx.guiFont.render(ctx.screen, "GAME OVER", 165, 75, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "YOUR FARM HAS GONE BANKRUPT", 100, 95, COLOR_WHITE, 1, true, false);

        // Buttons
        renderButton(ctx, 155, 120, "PLAY AGAIN");
        renderButton(ctx, 155, 155, "EXIT");
    }

    private void renderButton(GameContext ctx, int x, int y, String text) {
        ctx.screen.renderSprite(x, y, Sprite.smallWoodenBoard, false);
        int textX = x + (90 - text.length() * 6) / 2;
        ctx.guiFont.render(ctx.screen, text, textX, y + 10, COLOR_WHITE, 1, true, false);
    }
}
