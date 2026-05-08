package com.cousersoft.game.controller;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.GameState;
import com.cousersoft.game.input.InputManager;

import static com.cousersoft.game.GameConstants.*;

public class HelpController implements StateUpdater {

    @Override
    public void update(GameContext ctx, InputManager inputManager) {
        int mx = ctx.mouse.getX() / ctx.scale;
        int my = ctx.mouse.getY() / ctx.scale;

        // Back button click
        if (inputManager.isJustPressed(ctx.mouse.getButton() == 1, "mouseLeft")) {
            if (mx >= MENU_BTN_X && mx <= MENU_BTN_X + MENU_BTN_W && my >= 190 && my <= 204) {
                ctx.handler.setState(GameState.MENU);
            }
        }

        if (inputManager.isJustPressed(ctx.keyboard.escape, "escapeTriggered")) {
            ctx.handler.setState(GameState.MENU);
        }
    }
}
