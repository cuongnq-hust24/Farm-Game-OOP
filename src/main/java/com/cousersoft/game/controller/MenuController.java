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
            if (mx >= 155 && mx <= 155 + 90) {
                if      (my >= 70 && my <= 70 + 27) { ctx.reset(); ctx.handler.setState(GameState.GAME); }
                else if (my >= 105  && my <= 105  + 27) { ctx.handler.setState(GameState.HELP); }
                else if (my >= 140  && my <= 140  + 27) { ctx.showQuitConfirm = true; }
            }
        }

        if (inputManager.isJustPressed(ctx.keyboard.enter, "enterTriggered")) {
            ctx.reset();
            ctx.handler.setState(GameState.GAME);
        }
    }
}
