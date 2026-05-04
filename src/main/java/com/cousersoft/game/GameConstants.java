package com.cousersoft.game;

/**
 * Centralized constants for the Smart Farm Simulator.
 * All magic numbers from Game.java are extracted here for maintainability.
 */
public final class GameConstants {

    private GameConstants() {} // Prevent instantiation

    // ==================== SCREEN ====================
    public static final int SCREEN_WIDTH = 400;
    public static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 9;
    public static final int DEFAULT_SCALE = 3;
    public static final int MIN_SCALE = 1;
    public static final int MAX_SCALE = 6;

    // ==================== GRID ====================
    public static final int GRID_ROWS = 25;
    public static final int GRID_COLS = 14;
    public static final int TILE_SIZE = 16;

    // ==================== GAMEPLAY ====================
    public static final int STARTING_DAY = 1;
    public static final int STARTING_BALANCE = 100;
    public static final int FERTILIZER_COST = 15;
    public static final int MIN_SEED_COST = 5; // Used for game-over check (cheapest seed)
    public static final int SEED_COUNT = 10;

    // ==================== SHOP ====================
    public static final int CROPS_PER_PAGE = 3;
    public static final int SHOP_BOARD_X = 46;
    public static final int[] SHOP_BOARD_YS = {45, 98, 151};
    public static final int SHOP_TITLE_X = 155;
    public static final int SHOP_TITLE_Y = 10;
    public static final int SHOP_TITLE_TEXT_X = 171;
    public static final int SHOP_TITLE_TEXT_Y = 19;
    public static final int SHOP_PREV_X = 129;
    public static final int SHOP_NEXT_X = 245;
    public static final int SHOP_NAV_Y = 200;
    public static final int SHOP_NAV_W = 26;
    public static final int SHOP_NAV_H = 28;
    public static final int SHOP_PAGE_TEXT_X = 174;
    public static final int SHOP_PAGE_TEXT_Y = 208;
    public static final int SHOP_HINT_X = 105;
    public static final int SHOP_HINT_Y = 222;
    public static final int SHOP_SPRITE_OFFSET_X = 10;
    public static final int SHOP_SPRITE_OFFSET_Y = 12;
    public static final int SHOP_TEXT_OFFSET_X = 35;
    public static final int SHOP_TEXT_OFFSET_Y = 5;
    public static final int SHOP_TEXT_LINE_SPACING = 12;

    // ==================== MENU ====================
    public static final int MENU_BTN_X = 150;
    public static final int MENU_BTN_W = 100;
    public static final int MENU_BTN_H = 14;
    public static final int MENU_START_Y = 100;
    public static final int MENU_HELP_Y = 120;
    public static final int MENU_QUIT_Y = 140;

    // ==================== HUD ====================
    public static final int HUD_TOOL_ICON_X = 210;
    public static final int HUD_TOOL_ICON_Y = 190;
    public static final int HUD_TOOL_SPACING = 20;
    public static final int HUD_TOOL_CLICK_Y_MIN = 182;
    public static final int HUD_TOOL_CLICK_Y_MAX = 210;
    public static final int HUD_ADV_BTN_X = 315;
    public static final int HUD_ADV_BTN_Y = 190;
    public static final int HUD_ADV_BTN_W = 52;
    public static final int HUD_ADV_BTN_H = 28;
    public static final int HUD_INFO_X = 10;
    public static final int HUD_INFO_Y = 175;

    // ==================== COLORS ====================
    public static final int COLOR_BOARD_TEXT = 0xff603931;
    public static final int COLOR_MENU_BG = 0x1a1a2e;
    public static final int COLOR_WHITE = 0xffffffff;
    public static final int COLOR_SUBTITLE = 0xffcccccc;
    public static final int COLOR_BTN_GREEN = 0x2d6a4f;
    public static final int COLOR_BTN_BLUE = 0x1d3557;
    public static final int COLOR_BTN_RED = 0x6b2737;
    public static final int COLOR_OVERLAY_BLACK = 0x000000;
    public static final int COLOR_OUTLINE_WHITE = 0xffffff;
    public static final int COLOR_TEXT_GRAY = 0xffaaaaaa;
    public static final int COLOR_TEXT_DIM = 0xff888888;
    public static final int COLOR_HELP_TEXT = 0xffdddddd;
    public static final int COLOR_NUTRIENT_TEXT = 0xffffffaa;
}
