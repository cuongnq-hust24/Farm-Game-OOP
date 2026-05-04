package com.cousersoft.game.state;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.GameState;
import com.cousersoft.game.input.InputManager;
import com.cousersoft.game.input.Tool;

import static com.cousersoft.game.GameConstants.*;

public class ShopUpdater implements StateUpdater {

    @Override
    public void update(GameContext ctx, InputManager inputManager) {
        int mx = ctx.mouse.getX() / ctx.scale;
        int my = ctx.mouse.getY() / ctx.scale;

        // Close shop with P or Escape
        if (inputManager.isJustPressed(ctx.keyboard.kP, "pKeyTriggered")) {
            ctx.handler.setState(GameState.GAME);
        }
        if (inputManager.isJustPressed(ctx.keyboard.escape, "escapeTriggered")) {
            ctx.handler.setState(GameState.GAME);
        }

        int totalPages = (ctx.cropCatalog.length + CROPS_PER_PAGE - 1) / CROPS_PER_PAGE;

        // Keyboard pagination
        if (inputManager.isJustPressed(ctx.keyboard.left, "arrowLeft")) {
            ctx.shopPage = (ctx.shopPage - 1 + totalPages) % totalPages;
        } else if (inputManager.isJustPressed(ctx.keyboard.right, "arrowRight")) {
            ctx.shopPage = (ctx.shopPage + 1) % totalPages;
        }

        if (inputManager.isJustPressed(ctx.mouse.getButton() == 1, "mouseLeft")) {
            // Prev button
            if (mx >= SHOP_PREV_X && mx < SHOP_PREV_X + SHOP_NAV_W && my >= SHOP_NAV_Y && my < SHOP_NAV_Y + SHOP_NAV_H) {
                ctx.shopPage = (ctx.shopPage - 1 + totalPages) % totalPages;
            }
            // Next button
            else if (mx >= SHOP_NEXT_X && mx < SHOP_NEXT_X + SHOP_NAV_W && my >= SHOP_NAV_Y && my < SHOP_NAV_Y + SHOP_NAV_H) {
                ctx.shopPage = (ctx.shopPage + 1) % totalPages;
            }
            // Click on wooden boards to select crop
            else {
                for (int i = 0; i < CROPS_PER_PAGE; i++) {
                    int cropIdx = ctx.shopPage * CROPS_PER_PAGE + i;
                    if (cropIdx >= ctx.cropCatalog.length) break;
                    if (mx >= SHOP_BOARD_X && mx < SHOP_BOARD_X + 308 && my >= SHOP_BOARD_YS[i] && my < SHOP_BOARD_YS[i] + 48) {
                        ctx.seedIndex = cropIdx;
                        ctx.selectedTool = Tool.SEED_SHOP;
                        ctx.message = "Equipped: " + ctx.cropCatalog[cropIdx].name;
                        ctx.handler.setState(GameState.GAME);
                        break;
                    }
                }
            }
        }
    }
}
