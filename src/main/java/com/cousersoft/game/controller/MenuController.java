package com.cousersoft.game.controller;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.GameState;
import com.cousersoft.game.input.InputManager;

import static com.cousersoft.game.GameConstants.*;

public class MenuController implements StateUpdater {

    @Override
    public void update(GameContext ctx, InputManager inputManager) {
        int mx = ctx.mouse.getX() / ctx.scale;
        int my = ctx.mouse.getY() / ctx.scale;

        if (ctx.showQuitConfirm) {
            if (ctx.keyboard.kY) System.exit(0);
            if (inputManager.isJustPressed(ctx.keyboard.escape, "escapeTriggered")) {
                ctx.showQuitConfirm = false;
            }
            return;
        }

        if (inputManager.isJustPressed(ctx.mouse.getButton() == 1, "mouseLeft")) {
            if (mx >= MENU_BTN_X && mx <= MENU_BTN_X + MENU_BTN_W) {
                if      (my >= MENU_START_Y && my <= MENU_START_Y + MENU_BTN_H) { ctx.reset(); ctx.handler.setState(GameState.GAME); }
                else if (my >= MENU_HELP_Y  && my <= MENU_HELP_Y  + MENU_BTN_H) { ctx.handler.setState(GameState.HELP); }
                else if (my >= MENU_QUIT_Y  && my <= MENU_QUIT_Y  + MENU_BTN_H) { ctx.showQuitConfirm = true; }
            }
        }

        if (inputManager.isJustPressed(ctx.keyboard.enter, "enterTriggered")) {
            ctx.reset();
            ctx.handler.setState(GameState.GAME);
        }
    }
}
