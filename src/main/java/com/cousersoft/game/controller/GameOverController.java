package com.cousersoft.game.controller;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.GameState;
import com.cousersoft.game.input.InputManager;

import static com.cousersoft.game.GameConstants.*;

public class GameOverController implements StateUpdater {

    @Override
    public void update(GameContext ctx, InputManager inputManager) {
        int mx = ctx.mouse.getX() / ctx.scale;
        int my = ctx.mouse.getY() / ctx.scale;

        if (inputManager.isJustPressed(ctx.mouse.getButton() == 1, "mouseLeft")) {
            if (mx >= 155 && mx <= 155 + 90) {
                if (my >= 120 && my <= 120 + 27) { 
                    ctx.reset(); 
                    ctx.handler.setState(GameState.GAME); 
                } else if (my >= 155 && my <= 155 + 27) { 
                    ctx.handler.setState(GameState.MENU); 
                }
            }
        }
    }
}
