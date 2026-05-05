package com.cousersoft.game.render;

import com.cousersoft.game.Game;
import com.cousersoft.game.GameContext;
import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.simulation.Crop;
import com.cousersoft.game.simulation.FarmCell;
import com.cousersoft.game.simulation.Rainy;
import com.cousersoft.game.simulation.Snowy;
import com.cousersoft.game.simulation.Weather;

import static com.cousersoft.game.GameConstants.*;

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

    private void renderGrid(GameContext ctx) {
        for (int y = 0; y < ctx.grid.getCols(); y++) {
            for (int x = 0; x < ctx.grid.getRows(); x++) {
                FarmCell cell = ctx.grid.getCell(x, y);
                char type = ctx.grid.getTileType(x, y);
                Sprite sprite;

                if (type == 'S') {
                    sprite = getFarmlandSprite(ctx, x, y, cell.getMoistureLevel(), ctx.grid.getCurrentWeather());
                } else {
                    // Randomized grass variety based on coordinates and weather
                    int baseSeed = Math.abs(x * 7 + y * 13);
                    String weatherName = ctx.grid.getCurrentWeather().getName();

                    if (weatherName.equalsIgnoreCase("HeatWave") || weatherName.equalsIgnoreCase("Heat Wave")) {
                        // Heat Wave logic: use dry variants
                        int variant = baseSeed % 3;
                        sprite = switch (variant) {
                            case 0 -> Sprite.nGrassDry1;
                            case 1 -> Sprite.nGrassDry2;
                            default -> Sprite.nGrassDry3;
                        };
                    } else if (weatherName.equals("Rainy")) {
                        // Rainy logic: 70% chance of variants 4&5, 30% chance of variants 1,2,3
                        int chance = baseSeed % 10;
                        if (chance < 7) {
                            sprite = (baseSeed % 2 == 0) ? Sprite.nGrass4 : Sprite.nGrass5;
                        } else {
                            int variant = baseSeed % 3;
                            sprite = (variant == 0) ? Sprite.nGrass1 : (variant == 1) ? Sprite.nGrass2 : Sprite.nGrass3;
                        }
                    } else if (weatherName.equalsIgnoreCase("Snowy")) {
                        // 100% chance of new variants (11, 12, 13) for testing
                        int variant = baseSeed % 3;
                        sprite = (variant == 0) ? Sprite.nGrassWinter3
                                : (variant == 1) ? Sprite.nGrassWinter4 : Sprite.nGrassWinter5;
                    } else {
                        // Sunny / Default logic: use vibrant green variants 1,2,3
                        int variant = baseSeed % 3;
                        sprite = (variant == 0) ? Sprite.nGrass1 : (variant == 1) ? Sprite.nGrass2 : Sprite.nGrass3;
                    }
                }

                ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE, sprite, false);

                Crop crop = cell.getCurrentCrop();
                if (crop != null) {
                    Sprite cropSprite = crop.getSprite();
                    if (cropSprite != null) {
                        // Offset by -TILE_SIZE vertically for 32px sprites to "stand" on the cell
                        int yOffset = (cropSprite.getHeight() > TILE_SIZE) ? -TILE_SIZE : 0;
                        ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE + yOffset, cropSprite, false);
                    }
                }
                if (cell.hasPests())
                    ctx.screen.renderSprite(x * TILE_SIZE, y * TILE_SIZE, Sprite.ladybug, false);
            }
        }
    }

    private Sprite getFarmlandSprite(GameContext ctx, int x, int y, int moisture, Weather weather) {
        boolean u = ctx.grid.getTileType(x, y - 1) == 'S';
        boolean d = ctx.grid.getTileType(x, y + 1) == 'S';
        boolean l = ctx.grid.getTileType(x - 1, y) == 'S';
        boolean r = ctx.grid.getTileType(x + 1, y) == 'S';

        int variant = 0; // 0=Dry, 1=Wet1, 2=Wet2
        if (moisture >= 80)
            variant = 2;
        else if (moisture >= 40)
            variant = 1;

        String wName = (weather != null) ? weather.getName() : "Sunny";

        if (wName.equalsIgnoreCase("Snowy")) {
            // Snow Farmland (Col 9-11, Rows 1-3). Note: only 1 moisture variant shown for snow
            if (!u && !l) return Sprite.fSnowTopLeft;
            if (!u && !r) return Sprite.fSnowTopRight;
            if (!d && !l) return Sprite.fSnowBotLeft;
            if (!d && !r) return Sprite.fSnowBotRight;
            if (!u) return Sprite.fSnowTop;
            if (!d) return Sprite.fSnowBot;
            if (!l) return Sprite.fSnowLeft;
            if (!r) return Sprite.fSnowRight;
            return Sprite.fSnowCenter;
        }

        if (wName.equalsIgnoreCase("HeatWave") || wName.equalsIgnoreCase("Heat Wave")) {
            // HeatWave Farmland (Row 4-6)
            if (!u && !l) return (variant == 0) ? Sprite.fHeatTopLeft : (variant == 1) ? Sprite.fHeatTopLeftWet1 : Sprite.fHeatTopLeftWet2;
            if (!u && !r) return (variant == 0) ? Sprite.fHeatTopRight : (variant == 1) ? Sprite.fHeatTopRightWet1 : Sprite.fHeatTopRightWet2;
            if (!d && !l) return (variant == 0) ? Sprite.fHeatBotLeft : (variant == 1) ? Sprite.fHeatBotLeftWet1 : Sprite.fHeatBotLeftWet2;
            if (!d && !r) return (variant == 0) ? Sprite.fHeatBotRight : (variant == 1) ? Sprite.fHeatBotRightWet1 : Sprite.fHeatBotRightWet2;
            if (!u) return (variant == 0) ? Sprite.fHeatTop : (variant == 1) ? Sprite.fHeatTopWet1 : Sprite.fHeatTopWet2;
            if (!d) return (variant == 0) ? Sprite.fHeatBot : (variant == 1) ? Sprite.fHeatBotWet1 : Sprite.fHeatBotWet2;
            if (!l) return (variant == 0) ? Sprite.fHeatLeft : (variant == 1) ? Sprite.fHeatLeftWet1 : Sprite.fHeatLeftWet2;
            if (!r) return (variant == 0) ? Sprite.fHeatRight : (variant == 1) ? Sprite.fHeatRightWet1 : Sprite.fHeatRightWet2;
            return (variant == 0) ? Sprite.fHeatCenter : (variant == 1) ? Sprite.fHeatCenterWet1 : Sprite.fHeatCenterWet2;
        }

        // Default Normal Farmland
        if (!u && !l) return (variant == 0) ? Sprite.fTopLeft : (variant == 1) ? Sprite.fTopLeftWet1 : Sprite.fTopLeftWet2;
        if (!u && !r) return (variant == 0) ? Sprite.fTopRight : (variant == 1) ? Sprite.fTopRightWet1 : Sprite.fTopRightWet2;
        if (!d && !l) return (variant == 0) ? Sprite.fBotLeft : (variant == 1) ? Sprite.fBotLeftWet1 : Sprite.fBotLeftWet2;
        if (!d && !r) return (variant == 0) ? Sprite.fBotRight : (variant == 1) ? Sprite.fBotRightWet1 : Sprite.fBotRightWet2;
        if (!u) return (variant == 0) ? Sprite.fTop : (variant == 1) ? Sprite.fTopWet1 : Sprite.fTopWet2;
        if (!d) return (variant == 0) ? Sprite.fBot : (variant == 1) ? Sprite.fBotWet1 : Sprite.fBotWet2;
        if (!l) return (variant == 0) ? Sprite.fLeft : (variant == 1) ? Sprite.fLeftWet1 : Sprite.fLeftWet2;
        if (!r) return (variant == 0) ? Sprite.fRight : (variant == 1) ? Sprite.fRightWet1 : Sprite.fRightWet2;
        return (variant == 0) ? Sprite.fCenter : (variant == 1) ? Sprite.fCenterWet1 : Sprite.fCenterWet2;
    }

    private void renderSelection(GameContext ctx) {
        if (ctx.selectedX != -1) {
            ctx.screen.renderOutline(ctx.selectedX * TILE_SIZE, ctx.selectedY * TILE_SIZE, TILE_SIZE, TILE_SIZE, COLOR_WHITE);
        }
    }

    private void renderHUD(GameContext ctx) {
        // Top Right Display: Balance and Day
        ctx.guiFont.render(ctx.screen, "BALANCE: $" + ctx.balance, Game.width - 120, 10, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "DAY: " + ctx.day, Game.width - 120, 22, COLOR_WHITE, 1, true, false);

        // Status Messages (Top Left)
        ctx.guiFont.render(ctx.screen, ctx.message, HUD_INFO_X, 10, COLOR_WHITE, 1, true, false);

        // Back to menu hint (Top Right corner)
        ctx.guiFont.render(ctx.screen, "ESC:MENU", Game.width - 50, 2, COLOR_WHITE, 1, true, false);

        // Bottom Left Info Area
        int hudX = HUD_INFO_X;
        int hudYStart = HUD_INFO_Y;
        ctx.guiFont.render(ctx.screen, "WEATHER: " + ctx.grid.getCurrentWeather().getName().toUpperCase(), hudX, hudYStart - 12, COLOR_WHITE, 1, true, false);
        ctx.guiFont.render(ctx.screen, "TOOL: " + ctx.selectedTool.toString().replace("_", " "), hudX, hudYStart, COLOR_WHITE, 1, true, false);

        if (ctx.selectedX != -1) {
            FarmCell cell = ctx.grid.getCell(ctx.selectedX, ctx.selectedY);
            String cellMain = "CELL: " + ctx.selectedX + "," + ctx.selectedY + " WATER: " + cell.getMoistureLevel();
            if (cell.hasPests())
                cellMain += " [PEST!]";
            ctx.guiFont.render(ctx.screen, cellMain, hudX, hudYStart + 12, COLOR_WHITE, 1, true, false);

            if (cell.getCurrentCrop() != null) {
                String cropInfo = "CROP: " + cell.getCurrentCrop().getClass().getSimpleName() + " [" + cell.getCurrentCrop().getStage().name() + "]";
                ctx.guiFont.render(ctx.screen, cropInfo, hudX, hudYStart + 24, COLOR_WHITE, 1, true, false);
            } else {
                ctx.guiFont.render(ctx.screen, "NUTRIENTS: " + cell.getNutrientLevel(), hudX, hudYStart + 24, COLOR_NUTRIENT_TEXT, 1, true, false);
            }
        }

        // Tool Icons
        int iconXBase = HUD_TOOL_ICON_X;
        // Slot 1: Dynamic Seed Icon
        renderToolIcon(ctx, iconXBase, HUD_TOOL_ICON_Y, ctx.cropCatalog[ctx.seedIndex].matureSprite, Tool.SEED_SHOP, "1", -10);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING, HUD_TOOL_ICON_Y, Sprite.hoe, Tool.HARVEST, "2", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 2, HUD_TOOL_ICON_Y, Sprite.wateringCan, Tool.WATERING_CAN, "3", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 3, HUD_TOOL_ICON_Y, Sprite.sword, Tool.SWORD, "4", 0);
        renderToolIcon(ctx, iconXBase + HUD_TOOL_SPACING * 4, HUD_TOOL_ICON_Y, Sprite.fertilizer, Tool.FERTILIZER, "5", 0);

        // Advance Button
        int advX = HUD_ADV_BTN_X;
        ctx.screen.renderSprite(advX, HUD_ADV_BTN_Y, Sprite.advDayBtn, false);
        ctx.guiFont.render(ctx.screen, "ADV DAY", advX + 8, 200, COLOR_BOARD_TEXT, 1, false, false);
    }

    private void renderToolIcon(GameContext ctx, int x, int y, Sprite s, Tool t, String label, int yOffset) {
        ctx.screen.renderSprite(x, y + yOffset, s, false);
        ctx.guiFont.render(ctx.screen, label, x + 5, y + 18, COLOR_WHITE, 1, true, false);
        if (ctx.selectedTool == t)
            ctx.screen.renderSprite(x, y, Sprite.select, false);
    }
}
