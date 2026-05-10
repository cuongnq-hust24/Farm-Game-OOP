package com.cousersoft.game.view;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.model.crop.Crop;
import com.cousersoft.game.model.FarmCell;
import com.cousersoft.game.model.weather.Rainy;
import com.cousersoft.game.model.weather.Snowy;
import com.cousersoft.game.model.weather.Weather;

import static com.cousersoft.game.GameConstants.*;

/**
 * VIEW — Renders the main gameplay screen.
 *
 * P1 FIX: No longer references Game.width/Game.height; uses SCREEN_WIDTH/SCREEN_HEIGHT constants.
 * P2 FIX: Grass and farmland sprite selection delegated to Weather subclasses via
 *          Weather#getGrassSprite() and Weather#getFarmlandSprite(); no string comparisons remain.
 */
public class GameRenderer implements StateRenderer {

    @Override
    public void render(GameContext ctx) {
        renderGrid(ctx);
        renderSelection(ctx);
        renderHUD(ctx);

        Weather w = ctx.grid.getCurrentWeather();
        if (w instanceof Rainy) {
            ctx.screen.applyRainOverlay(ctx.tickCounter);
        } else if (w instanceof Snowy) {
            ctx.screen.applySnowOverlay(ctx.tickCounter);
        }
    }

    // ── Grid ────────────────────────────────────────────────────────────────

    private void renderGrid(GameContext ctx) {
        Weather weather = ctx.grid.getCurrentWeather();

        for (int y = 0; y < ctx.grid.getCols(); y++) {
            for (int x = 0; x < ctx.grid.getRows(); x++) {
                FarmCell cell = ctx.grid.getCell(x, y);
                char type    = ctx.grid.getTileType(x, y);

                Sprite sprite;
                if (type == 'S') {
                    boolean u = ctx.grid.getTileType(x, y - 1) == 'S';
                    boolean d = ctx.grid.getTileType(x, y + 1) == 'S';
                    boolean l = ctx.grid.getTileType(x - 1, y) == 'S';
                    boolean r = ctx.grid.getTileType(x + 1, y) == 'S';
                    // Delegate sprite selection entirely to the Weather object
                    sprite = weather.getFarmlandSprite(u, d, l, r, cell.getMoistureLevel());
                } else {
                    int baseSeed = Math.abs(x * 7 + y * 13);
                    sprite = weather.getGrassSprite(baseSeed);
                }

                ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE, sprite, false);

                // Render crop
                Crop crop = cell.getCurrentCrop();
                if (crop != null) {
                    Sprite cropSprite = crop.getSprite();
                    if (cropSprite != null) {
                        int yOffset = (cropSprite.getHeight() > TILE_SIZE) ? -TILE_SIZE : 0;
                        ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE + yOffset, cropSprite, false);
                    }
                }

                // Render pest indicator
                if (cell.hasPests()) {
                    ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE, Sprite.ladybug, false);
                }
            }
        }
    }

    // ── Selection highlight ──────────────────────────────────────────────────

    private void renderSelection(GameContext ctx) {
        if (ctx.selectedX != -1) {
            ctx.screen.renderOutline(
                ctx.selectedX * TILE_SIZE,
                ctx.selectedY * TILE_SIZE,
                TILE_SIZE, TILE_SIZE, COLOR_WHITE);
        }
    }

    // ── HUD ─────────────────────────────────────────────────────────────────

    private void renderHUD(GameContext ctx) {
        // P1 FIX: Use SCREEN_WIDTH instead of Game.width
        ctx.guiFont.render(ctx.screen, "BALANCE: $" + ctx.balance, SCREEN_WIDTH - 200, 2, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "DAY: " + ctx.day,          SCREEN_WIDTH - 110, 2, COLOR_WHITE, 1, true, false);

        ctx.guiFont.render(ctx.screen, ctx.message, HUD_INFO_X, 10, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "ESC:MENU", SCREEN_WIDTH - 50, 2, COLOR_WHITE, 1, true, false);

        int hudX     = HUD_INFO_X;
        int hudYStart = HUD_INFO_Y;
        ctx.guiFont.render(ctx.screen, "WEATHER: " + ctx.grid.getCurrentWeather().getName().toUpperCase(), hudX, hudYStart - 12, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "TOOL: " + ctx.selectedTool.toString().replace("_", " "), hudX, hudYStart, COLOR_WHITE, 1, true, false);

        if (ctx.selectedX != -1) {
            FarmCell cell = ctx.grid.getCell(ctx.selectedX, ctx.selectedY);
            String cellMain = "CELL: " + ctx.selectedX + "," + ctx.selectedY + " WATER: " + cell.getMoistureLevel();
            if (cell.hasPests()) cellMain += " [PEST!]";
            ctx.guiFont.render(ctx.screen, cellMain, hudX, hudYStart + 12, COLOR_WHITE, 1, true, false);

            if (cell.getCurrentCrop() != null) {
                String cropInfo = "CROP: " + cell.getCurrentCrop().getClass().getSimpleName()
                                + " [" + cell.getCurrentCrop().getStage().name() + "]";
                ctx.guiFont.render(ctx.screen, cropInfo, hudX, hudYStart + 24, COLOR_WHITE, 1, true, false);
            } else {
                ctx.guiFont.render(ctx.screen, "NUTRIENTS: " + cell.getNutrientLevel(), hudX, hudYStart + 24, COLOR_NUTRIENT_TEXT, 1, true, false);
            }
        }

        // Tool icons
        int iconXBase = HUD_TOOL_ICON_X;
        renderToolIcon(ctx, iconXBase,                     HUD_TOOL_ICON_Y, ctx.cropCatalog[ctx.seedIndex].matureSprite, Tool.SEED_SHOP,    "1", -10);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING,  HUD_TOOL_ICON_Y, Sprite.hoe,        Tool.HARVEST,      "2", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 2, HUD_TOOL_ICON_Y, Sprite.wateringCan, Tool.WATERING_CAN, "3", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 3, HUD_TOOL_ICON_Y, Sprite.sword,    Tool.SWORD,        "4", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 4, HUD_TOOL_ICON_Y, Sprite.fertilizer, Tool.FERTILIZER, "5", 0);

        // Advance Day button
        ctx.screen.renderSprite(HUD_ADV_BTN_X, HUD_ADV_BTN_Y, Sprite.advDayBtn, false);
        ctx.guiFont.render(ctx.screen, "ADV DAY", HUD_ADV_BTN_X + 8, 200, COLOR_BOARD_TEXT, 1, false, false);

        renderWeatherButtons(ctx);
    }

    private void renderToolIcon(GameContext ctx, int x, int y, Sprite s, Tool t, String label, int yOffset) {
        ctx.screen.renderSprite(x, y + yOffset, s, false);
        ctx.guiFont.render(ctx.screen, label, x + 5, y + 18, COLOR_WHITE, 1, true, false);
        if (ctx.selectedTool == t) {
            ctx.screen.renderSprite(x, y, Sprite.select, false);
        }
    }

    private void renderWeatherButtons(GameContext ctx) {
        int x = 370;
        int[] ys = {50, 82, 114, 146};
        Sprite[] icons = {Sprite.sunnyIcon, Sprite.rainIcon, Sprite.heatwaveIcon, Sprite.snowIcon};
        
        for (int i = 0; i < 4; i++) {
            ctx.screen.renderSprite(x, ys[i], icons[i], false);
        }
        
        Weather w = ctx.grid.getCurrentWeather();
        int activeIndex = 0;
        if (w instanceof Rainy) activeIndex = 1;
        else if (w instanceof com.cousersoft.game.model.weather.HeatWave) activeIndex = 2;
        else if (w instanceof Snowy) activeIndex = 3;
        
        ctx.screen.renderOutline(x, ys[activeIndex], 26, 28, COLOR_WHITE);
    }
}
