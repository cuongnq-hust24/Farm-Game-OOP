package com.cousersoft.game.state;

import com.cousersoft.game.GameContext;
import com.cousersoft.game.GameState;
import com.cousersoft.game.input.InputManager;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.simulation.Crop;
import com.cousersoft.game.simulation.FarmCell;
import com.cousersoft.game.simulation.GrowthStage;
import com.cousersoft.game.simulation.*;

import java.awt.Dimension;

import static com.cousersoft.game.GameConstants.*;

public class GameUpdater implements StateUpdater {

    @Override
    public void update(GameContext ctx, InputManager inputManager) {
        int mx = ctx.mouse.getX() / ctx.scale;
        int my = ctx.mouse.getY() / ctx.scale;

        // Handle Tool Selection Shortcuts
        if (ctx.keyboard.k1) ctx.selectedTool = Tool.SEED_SHOP;
        else if (ctx.keyboard.k2) ctx.selectedTool = Tool.HARVEST;
        else if (ctx.keyboard.k3) ctx.selectedTool = Tool.WATERING_CAN;
        else if (ctx.keyboard.k4) ctx.selectedTool = Tool.SWORD;
        else if (ctx.keyboard.k5) ctx.selectedTool = Tool.FERTILIZER;

        // Handle Tab to cycle crops
        if (inputManager.isJustPressed(ctx.keyboard.tab, "tabCycle")) {
            if (ctx.selectedTool == Tool.SEED_SHOP) {
                ctx.seedIndex = (ctx.seedIndex + 1) % SEED_COUNT;
                ctx.message = "Selected: " + ctx.cropCatalog[ctx.seedIndex].name;
            }
        }

        // Handle Scale Changes
        if (inputManager.isJustPressed(ctx.keyboard.bracketLeft || ctx.keyboard.bracketRight, "scaleKey")) {
            if (ctx.keyboard.bracketLeft) changeScale(ctx, -1);
            if (ctx.keyboard.bracketRight) changeScale(ctx, 1);
        }

        // Handle Arrow Keys for cell navigation (Excel-style)
        if (inputManager.isJustPressed(ctx.keyboard.up || ctx.keyboard.down || ctx.keyboard.left || ctx.keyboard.right, "arrowGridNav")) {
            if (ctx.selectedX == -1) {
                // No cell selected yet, start at center
                ctx.selectedX = ctx.grid.getRows() / 2;
                ctx.selectedY = ctx.grid.getCols() / 2;
            } else {
                if (ctx.keyboard.up && ctx.selectedY > 0) ctx.selectedY--;
                if (ctx.keyboard.down && ctx.selectedY < ctx.grid.getCols() - 1) ctx.selectedY++;
                if (ctx.keyboard.left && ctx.selectedX > 0) ctx.selectedX--;
                if (ctx.keyboard.right && ctx.selectedX < ctx.grid.getRows() - 1) ctx.selectedX++;
            }
        }

        // Handle Space to apply tool on selected cell
        if (inputManager.isJustPressed(ctx.keyboard.space, "spaceTriggered")) {
            if (ctx.selectedX != -1 && ctx.selectedY != -1) {
                applyTool(ctx, ctx.selectedX, ctx.selectedY);
            }
        }

        // Handle Escape to go back to menu
        if (inputManager.isJustPressed(ctx.keyboard.escape, "escapeTriggered")) {
            ctx.handler.setState(GameState.MENU);
        }

        // Handle Enter for Advance Day
        if (inputManager.isJustPressed(ctx.keyboard.enter, "enterTriggered")) {
            ctx.day++;
            ctx.grid.advanceDay();
            checkGameOver(ctx);
        }

        // Handle Manual Weather Triggers
        if (inputManager.isJustPressed(ctx.keyboard.kR || ctx.keyboard.kH || ctx.keyboard.kY || ctx.keyboard.kS, "weatherShortcuts")) {
            if (ctx.keyboard.kR) {
                ctx.grid.setWeather(new Rainy());
                ctx.message = "Forced RAINY weather!";
            } else if (ctx.keyboard.kH) {
                ctx.grid.setWeather(new HeatWave());
                ctx.message = "Forced HEAT WAVE!";
            } else if (ctx.keyboard.kY) {
                ctx.grid.setWeather(new Sunny());
                ctx.message = "Forced SUNNY weather!";
            } else if (ctx.keyboard.kS) {
                ctx.grid.setWeather(new Snowy());
                ctx.message = "Forced SNOWY weather!";
            }
        }

        // Handle P key to open Shop
        if (inputManager.isJustPressed(ctx.keyboard.kP, "pKeyTriggered")) {
            ctx.shopPage = 0;
            ctx.handler.setState(GameState.SHOP);
        }

        if (inputManager.isJustPressed(ctx.mouse.getButton() == 1, "mouseLeft")) {
            handleClicks(ctx, mx, my);
        }
    }

    private void changeScale(GameContext ctx, int amount) {
        int newScale = ctx.scale + amount;
        if (newScale < MIN_SCALE) newScale = MIN_SCALE;
        if (newScale > MAX_SCALE) newScale = MAX_SCALE;

        if (newScale != ctx.scale) {
            ctx.scale = newScale;
            ctx.scaleChanged = true;
            ctx.message = "Resolution: " + (SCREEN_WIDTH * ctx.scale) + "x" + (SCREEN_HEIGHT * ctx.scale);
        }
    }

    private void handleClicks(GameContext ctx, int mx, int my) {
        int iconXBase = HUD_TOOL_ICON_X;
        if (my >= HUD_TOOL_CLICK_Y_MIN && my <= HUD_TOOL_CLICK_Y_MAX) {
            if (mx >= iconXBase && mx < iconXBase + TILE_SIZE) {
                ctx.selectedTool = Tool.SEED_SHOP;
                return;
            } else if (mx >= iconXBase + HUD_TOOL_SPACING && mx < iconXBase + HUD_TOOL_SPACING + TILE_SIZE) {
                ctx.selectedTool = Tool.HARVEST;
                return;
            } else if (mx >= iconXBase + HUD_TOOL_SPACING * 2 && mx < iconXBase + HUD_TOOL_SPACING * 2 + TILE_SIZE) {
                ctx.selectedTool = Tool.WATERING_CAN;
                return;
            } else if (mx >= iconXBase + HUD_TOOL_SPACING * 3 && mx < iconXBase + HUD_TOOL_SPACING * 3 + TILE_SIZE) {
                ctx.selectedTool = Tool.SWORD;
                return;
            } else if (mx >= iconXBase + HUD_TOOL_SPACING * 4 && mx < iconXBase + HUD_TOOL_SPACING * 4 + TILE_SIZE) {
                ctx.selectedTool = Tool.FERTILIZER;
                return;
            }
        }

        int advX = HUD_ADV_BTN_X;
        if (mx >= advX && mx <= advX + HUD_ADV_BTN_W && my >= HUD_ADV_BTN_Y && my <= HUD_ADV_BTN_Y + HUD_ADV_BTN_H) {
            ctx.day++;
            ctx.grid.advanceDay();
            checkGameOver(ctx);
            return;
        }

        if (mx >= SCREEN_WIDTH - 40 && mx <= SCREEN_WIDTH && my >= 0 && my <= 12) {
            ctx.handler.setState(GameState.MENU);
            return;
        }

        int gx = mx / TILE_SIZE;
        int gy = my / TILE_SIZE;
        if (gx >= 0 && gx < ctx.grid.getRows() && gy >= 0 && gy < ctx.grid.getCols()) {
            ctx.selectedX = gx;
            ctx.selectedY = gy;
            applyTool(ctx, gx, gy);
        }
    }

    private void applyTool(GameContext ctx, int gx, int gy) {
        FarmCell cell = ctx.grid.getCell(gx, gy);
        if (cell == null) return;

        switch (ctx.selectedTool) {
            case WATERING_CAN -> {
                cell.waterCell();
                ctx.message = "Watered cell " + gx + "," + gy;
            }
            case SWORD -> {
                cell.clearPests();
                ctx.message = "Cleared pests at " + gx + "," + gy;
            }
            case FERTILIZER -> {
                if (ctx.balance >= FERTILIZER_COST) {
                    ctx.balance -= FERTILIZER_COST;
                    cell.fertilizeCell();
                    ctx.message = "FERTILIZED SOIL! (-$" + FERTILIZER_COST + ")";
                } else {
                    ctx.message = "NOT ENOUGH MONEY ($" + FERTILIZER_COST + ")";
                }
            }
            case SEED_SHOP -> {
                if (ctx.grid.getTileType(gx, gy) == 'S' && cell.getCurrentCrop() == null) {
                    int cost = ctx.cropCatalog[ctx.seedIndex].cost;
                    if (ctx.balance >= cost) {
                        Crop newCrop = switch (ctx.seedIndex) {
                            case 0 -> new Rice();
                            case 1 -> new Cabbage();
                            case 2 -> new Corn();
                            case 3 -> new Carrot();
                            case 4 -> new WhiteRadish();
                            case 5 -> new Tomato();
                            case 6 -> new Pumpkin();
                            case 7 -> new Eggplant();
                            case 8 -> new Chili();
                            case 9 -> new Pepper();
                            default -> new Rice();
                        };
                        cell.plantCrop(newCrop);
                        ctx.balance -= cost;
                        ctx.message = "Planted " + ctx.cropCatalog[ctx.seedIndex].name + "! -$" + cost;
                    } else {
                        ctx.message = "Not enough money!";
                    }
                }
            }
            case HARVEST -> {
                Crop crop = cell.getCurrentCrop();
                if (crop != null && crop.getStage() == GrowthStage.MATURE) {
                    int val = crop.harvest();
                    ctx.balance += val;
                    cell.removeCrop();
                    ctx.message = "Harvested! +$" + val;
                } else if (crop != null && crop.getStage() == GrowthStage.DEAD) {
                    cell.removeCrop();
                    ctx.message = "Cleared dead crop.";
                }
            }
            default -> {}
        }
    }

    private void checkGameOver(GameContext ctx) {
        if (ctx.balance < MIN_SEED_COST) {
            boolean hasActiveCrops = false;
            for (int y = 0; y < ctx.grid.getCols(); y++) {
                for (int x = 0; x < ctx.grid.getRows(); x++) {
                    if (ctx.grid.getCell(x, y).getCurrentCrop() != null
                            && ctx.grid.getCell(x, y).getCurrentCrop().getStage() != GrowthStage.DEAD) {
                        hasActiveCrops = true;
                        break;
                    }
                }
            }
            if (!hasActiveCrops) ctx.message = "GAME OVER: Out of funds!";
        }
    }
}
