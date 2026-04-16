package com.cousersoft.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.*;

import com.cousersoft.game.graphics.text.Font;
import com.cousersoft.game.graphics.Screen;
import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.input.Mouse;
import com.cousersoft.game.input.Keyboard;
import com.cousersoft.game.simulation.*;
import com.cousersoft.game.input.Tool;

import java.awt.Graphics;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 400;
	public static int height = width / 16 * 9;
	private int scale = 4;

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private Screen screen;
	public StateHandler handler;
	private Mouse mouse;
	private Keyboard keyboard;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int numUpdates = 0;
	public int numFrames = 0;

	private Font guiFont;
	private FarmGrid grid;

	private int day = 1;
	private int balance = 100;
	private int selectedX = -1;
	private int selectedY = -1;

	private Tool selectedTool = Tool.NONE;
	private String message = "Welcome to Smart Farm!";

	// Tick counter for weather animation
	private int tickCounter = 0;

	// 10-Crop System State
	private int seedIndex = 0;
	private String[] seedNames = { "Rice", "Cabbage", "Corn", "Carrot", "Radish", "Tomato", "Pumpkin", "Eggplant", "Chili", "Pepper" };
	private int[] seedCosts = { 5, 8, 10, 6, 7, 12, 25, 15, 10, 12 };
	private Sprite[] matureSprites = {
		Sprite.sRice4, Sprite.sCabbage4, Sprite.sCorn4, Sprite.sCarrot4, Sprite.sRadish4,
		Sprite.sTomato4, Sprite.sPumpkin4, Sprite.sEggplant4, Sprite.sChili4, Sprite.sPepper4
	};
	private boolean tabTriggered = false;

	// UI Buttons
	private final int BTN_W = 80, BTN_H = 20;
	private final int ADV_X = 300, ADV_Y = 190;

	// Costs
	private final int WHEAT_COST = 5;
	private final int TOMATO_COST = 10;

	// Menu button areas (pixel coordinates in internal resolution)
	private final int MENU_BTN_X = 150, MENU_BTN_W = 100, MENU_BTN_H = 14;
	private final int MENU_START_Y = 100;
	private final int MENU_HELP_Y = 120;
	private final int MENU_QUIT_Y = 140;

	// Quit confirmation state
	private boolean showQuitConfirm = false;

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		screen = new Screen(width, height);
		handler = new StateHandler();
		handler.setState("Menu"); // Start at the menu

		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		keyboard = new Keyboard();
		addKeyListener(keyboard);
		setFocusTraversalKeysEnabled(false);

		grid = new FarmGrid(25, 14);

		guiFont = new Font();
		guiFont.initChars("Small");
	}

	private void resetGame() {
		day = 1;
		balance = 100;
		selectedX = -1;
		selectedY = -1;
		selectedTool = Tool.NONE;
		message = "Welcome to Smart Farm!";
		grid = new FarmGrid(25, 14);
		showQuitConfirm = false;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				numFrames = frames;
				numUpdates = updates;
				updates = 0;
				frames = 0;
			}
		}
	}

	private boolean mouseClicked = false;
	private boolean spaceTriggered = false;
	private boolean scaleKeyTriggered = false;
	private boolean arrowTriggered = false;
	private boolean enterTriggered = false;
	private boolean escapeTriggered = false;
	private boolean weatherKeyTriggered = false;

	public void update() {
		keyboard.update();
		tickCounter++;

		String state = handler.getState();

		if (state.equals("Menu")) {
			updateMenu();
		} else if (state.equals("Help")) {
			updateHelp();
		} else if (state.equals("Game")) {
			updateGame();
		}
	}

	// ==================== MENU STATE ====================

	private void updateMenu() {
		int mx = mouse.getX() / scale;
		int my = mouse.getY() / scale;

		if (showQuitConfirm) {
			// Y to confirm quit, N/Escape to cancel
			if (keyboard.kY) {
				System.exit(0);
			}
			if (keyboard.escape) {
				if (!escapeTriggered) {
					showQuitConfirm = false;
					escapeTriggered = true;
				}
			} else {
				escapeTriggered = false;
			}
			return;
		}

		if (mouse.getButton() == 1) {
			if (!mouseClicked) {
				if (mx >= MENU_BTN_X && mx <= MENU_BTN_X + MENU_BTN_W) {
					if (my >= MENU_START_Y && my <= MENU_START_Y + MENU_BTN_H) {
						resetGame();
						handler.setState("Game");
					} else if (my >= MENU_HELP_Y && my <= MENU_HELP_Y + MENU_BTN_H) {
						handler.setState("Help");
					} else if (my >= MENU_QUIT_Y && my <= MENU_QUIT_Y + MENU_BTN_H) {
						showQuitConfirm = true;
					}
				}
				mouseClicked = true;
			}
		} else {
			mouseClicked = false;
		}

		// Keyboard shortcuts for menu
		if (keyboard.enter) {
			if (!enterTriggered) {
				resetGame();
				handler.setState("Game");
				enterTriggered = true;
			}
		} else {
			enterTriggered = false;
		}
	}

	// ==================== HELP STATE ====================

	private void updateHelp() {
		int mx = mouse.getX() / scale;
		int my = mouse.getY() / scale;

		// Back button click
		if (mouse.getButton() == 1) {
			if (!mouseClicked) {
				if (mx >= MENU_BTN_X && mx <= MENU_BTN_X + MENU_BTN_W && my >= 190 && my <= 204) {
					handler.setState("Menu");
				}
				mouseClicked = true;
			}
		} else {
			mouseClicked = false;
		}

		// Escape to go back
		if (keyboard.escape) {
			if (!escapeTriggered) {
				handler.setState("Menu");
				escapeTriggered = true;
			}
		} else {
			escapeTriggered = false;
		}
	}

	// ==================== GAME STATE ====================

	private void updateGame() {
		int mx = mouse.getX() / scale;
		int my = mouse.getY() / scale;

		// Handle Tool Selection Shortcuts
		if (keyboard.k1)
			selectedTool = Tool.SEED_SHOP;
		else if (keyboard.k2)
			selectedTool = Tool.HARVEST;
		else if (keyboard.k3)
			selectedTool = Tool.WATERING_CAN;
		else if (keyboard.k4)
			selectedTool = Tool.SWORD;

		// Handle Tab to cycle crops
		if (keyboard.tab) {
			if (!tabTriggered) {
				if (selectedTool == Tool.SEED_SHOP) {
					seedIndex = (seedIndex + 1) % 10;
					message = "Selected: " + seedNames[seedIndex];
				}
				tabTriggered = true;
			}
		} else {
			tabTriggered = false;
		}

		// Handle Scale Changes
		if (keyboard.bracketLeft || keyboard.bracketRight) {
			if (!scaleKeyTriggered) {
				if (keyboard.bracketLeft)
					changeScale(-1);
				if (keyboard.bracketRight)
					changeScale(1);
				scaleKeyTriggered = true;
			}
		} else {
			scaleKeyTriggered = false;
		}

		// Handle Arrow Keys for cell navigation (Excel-style)
		if (keyboard.up || keyboard.down || keyboard.left || keyboard.right) {
			if (!arrowTriggered) {
				if (selectedX == -1) {
					// No cell selected yet, start at center
					selectedX = grid.getRows() / 2;
					selectedY = grid.getCols() / 2;
				} else {
					if (keyboard.up && selectedY > 0)
						selectedY--;
					if (keyboard.down && selectedY < grid.getCols() - 1)
						selectedY++;
					if (keyboard.left && selectedX > 0)
						selectedX--;
					if (keyboard.right && selectedX < grid.getRows() - 1)
						selectedX++;
				}
				arrowTriggered = true;
			}
		} else {
			arrowTriggered = false;
		}

		// Handle Space to apply tool on selected cell
		if (keyboard.space) {
			if (!spaceTriggered) {
				if (selectedX != -1 && selectedY != -1) {
					applyTool(selectedX, selectedY);
				}
				spaceTriggered = true;
			}
		} else {
			spaceTriggered = false;
		}

		// Handle Escape to go back to menu
		if (keyboard.escape) {
			if (!escapeTriggered) {
				handler.setState("Menu");
				escapeTriggered = true;
			}
		} else {
			escapeTriggered = false;
		}

		// Handle Enter for Advance Day
		if (keyboard.enter) {
			if (!enterTriggered) {
				day++;
				grid.advanceDay();
				checkGameOver();
				enterTriggered = true;
			}
		} else {
			enterTriggered = false;
		}

		// Handle Manual Weather Triggers (R=Rain, H=Heat, Y=Sunny, S=Snow)
		if (keyboard.kR || keyboard.kH || keyboard.kY || keyboard.kS) {
			if (!weatherKeyTriggered) {
				if (keyboard.kR) {
					grid.setWeather(new Rainy());
					message = "Forced RAINY weather!";
				} else if (keyboard.kH) {
					grid.setWeather(new HeatWave());
					message = "Forced HEAT WAVE!";
				} else if (keyboard.kY) {
					grid.setWeather(new Sunny());
					message = "Forced SUNNY weather!";
				} else if (keyboard.kS) {
					grid.setWeather(new Snowy());
					message = "Forced SNOWY weather!";
				}
				weatherKeyTriggered = true;
			}
		} else {
			weatherKeyTriggered = false;
		}

		if (mouse.getButton() == 1) {
			if (!mouseClicked) {
				handleClicks(mx, my);
				mouseClicked = true;
			}
		} else {
			mouseClicked = false;
		}
	}

	private void changeScale(int amount) {
		int newScale = scale + amount;
		if (newScale < 1)
			newScale = 1;
		if (newScale > 6)
			newScale = 6;

		if (newScale != scale) {
			scale = newScale;
			Dimension size = new Dimension(width * scale, height * scale);
			this.setPreferredSize(size);
			this.setMinimumSize(size);
			this.setMaximumSize(size);
			frame.pack();
			frame.setLocationRelativeTo(null);
			message = "Resolution: " + (width * scale) + "x" + (height * scale);
		}
	}

	private void handleClicks(int mx, int my) {
		// Tool icon area (bottom HUD)
		int iconXBase = 210;
		if (my >= 182 && my <= 210) {
			if (mx >= iconXBase && mx < iconXBase + 16) {
				selectedTool = Tool.SEED_SHOP;
				return;
			} else if (mx >= iconXBase + 20 && mx < iconXBase + 36) {
				selectedTool = Tool.HARVEST;
				return;
			} else if (mx >= iconXBase + 40 && mx < iconXBase + 56) {
				selectedTool = Tool.WATERING_CAN;
				return;
			} else if (mx >= iconXBase + 60 && mx < iconXBase + 76) {
				selectedTool = Tool.SWORD;
				return;
			}
		}

		// Advance Day button
		int advX = 315;
		if (mx >= advX && mx <= advX + 64 && my >= 190 && my <= 210) {
			day++;
			grid.advanceDay();
			checkGameOver();
			return;
		}

		// Back to Menu button (top-right corner)
		if (mx >= width - 40 && mx <= width && my >= 0 && my <= 12) {
			handler.setState("Menu");
			return;
		}

		// Grid interaction
		int gx = mx / 16;
		int gy = my / 16;
		if (gx >= 0 && gx < grid.getRows() && gy >= 0 && gy < grid.getCols()) {
			selectedX = gx;
			selectedY = gy;
			applyTool(gx, gy);
		}
	}

	private void applyTool(int gx, int gy) {
		FarmCell cell = grid.getCell(gx, gy);
		if (cell == null)
			return;

		switch (selectedTool) {
			case WATERING_CAN -> {
				cell.waterCell();
				message = "Watered cell " + gx + "," + gy;
			}
			case SWORD -> {
				cell.clearPests();
				message = "Cleared pests at " + gx + "," + gy;
			}
			case SEED_SHOP -> {
				if (grid.getTileType(gx, gy) == 'S' && cell.getCurrentCrop() == null) {
					int cost = seedCosts[seedIndex];
					if (balance >= cost) {
						Crop newCrop = switch (seedIndex) {
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
						balance -= cost;
						message = "Planted " + seedNames[seedIndex] + "! -$" + cost;
					} else {
						message = "Not enough money!";
					}
				}
			}
			case HARVEST -> {
				Crop crop = cell.getCurrentCrop();
				if (crop != null && crop.getStage() == GrowthStage.MATURE) {
					int val = crop.harvest();
					balance += val;
					cell.removeCrop();
					message = "Harvested! +$" + val;
				} else if (crop != null && crop.getStage() == GrowthStage.DEAD) {
					cell.removeCrop();
					message = "Cleared dead crop.";
				}
			}
			default -> {
			}
		}
	}

	private void checkGameOver() {
		if (balance < WHEAT_COST) {
			boolean hasActiveCrops = false;
			for (int y = 0; y < grid.getCols(); y++) {
				for (int x = 0; x < grid.getRows(); x++) {
					if (grid.getCell(x, y).getCurrentCrop() != null
							&& grid.getCell(x, y).getCurrentCrop().getStage() != GrowthStage.DEAD) {
						hasActiveCrops = true;
						break;
					}
				}
			}
			if (!hasActiveCrops)
				message = "GAME OVER: Out of funds!";
		}
	}

	// ==================== RENDERING ====================

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		String state = handler.getState();
		if (state.equals("Menu")) {
			renderMenu();
		} else if (state.equals("Help")) {
			renderHelp();
		} else if (state.equals("Game")) {
			renderGameScreen();
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	// ==================== MENU RENDERING ====================

	private void renderMenu() {
		// Dark background
		screen.fillRect(0, 0, width, height, 0x1a1a2e);

		// Title
		guiFont.render(screen, 8, "SMART FARM SIMULATOR", 110, 40, 0, false);
		guiFont.render(screen, 8, "CROP GROWTH AND RESOURCE MANAGEMENT", 62, 55, 0, false);

		// Buttons
		renderMenuButton(MENU_BTN_X, MENU_START_Y, MENU_BTN_W, MENU_BTN_H, "START GAME", 0x2d6a4f);
		renderMenuButton(MENU_BTN_X, MENU_HELP_Y, MENU_BTN_W, MENU_BTN_H, "HELP", 0x1d3557);
		renderMenuButton(MENU_BTN_X, MENU_QUIT_Y, MENU_BTN_W, MENU_BTN_H, "QUIT", 0x6b2737);

		// Controls hint
		guiFont.render(screen, 8, "PRESS ENTER TO START", 130, 170, 0, false);

		// Quit confirmation overlay
		if (showQuitConfirm) {
			screen.fillRect(100, 85, 200, 40, 0x000000);
			screen.renderOutline(100, 85, 200, 40, 0xffffff);
			guiFont.render(screen, 8, "REALLY QUIT?", 160, 95, 0, false);
			guiFont.render(screen, 8, "Y = YES   ESC = NO", 140, 110, 0, false);
		}
	}

	private void renderMenuButton(int x, int y, int w, int h, String text, int color) {
		screen.fillRect(x, y, w, h, color);
		screen.renderOutline(x, y, w, h, 0xffffff);
		int textX = x + (w - text.length() * 6) / 2; // Rough centering
		int textY = y + 3;
		guiFont.render(screen, 8, text, textX, textY, 0, false);
	}

	// ==================== HELP RENDERING ====================

	private void renderHelp() {
		screen.fillRect(0, 0, width, height, 0x1a1a2e);

		guiFont.render(screen, 8, "HOW TO PLAY", 155, 15, 0, false);
		guiFont.render(screen, 8, "-----------------------------------", 80, 25, 0, false);

		int y = 40;
		int x = 30;
		guiFont.render(screen, 8, "CONTROLS:", x, y, 0, false);
		guiFont.render(screen, 8, "ARROW KEYS: NAVIGATE CELLS", x + 10, y + 12, 0, false);
		guiFont.render(screen, 8, "ENTER: APPLY SELECTED TOOL", x + 10, y + 22, 0, false);
		guiFont.render(screen, 8, "1-5: SELECT TOOL", x + 10, y + 32, 0, false);
		guiFont.render(screen, 8, "SPACE: ADVANCE DAY", x + 10, y + 42, 0, false);
		guiFont.render(screen, 8, "R/H/Y: FORCE RAIN/HEAT/SUNNY", x + 10, y + 52, 0, false);
		guiFont.render(screen, 8, "ESC: BACK TO MENU", x + 10, y + 62, 0, false);

		guiFont.render(screen, 8, "TOOLS:", x, y + 80, 0, false);
		guiFont.render(screen, 8, "1 WHEAT SEED ($5)", x + 10, y + 92, 0, false);
		guiFont.render(screen, 8, "2 TOMATO SEED ($10)", x + 10, y + 102, 0, false);
		guiFont.render(screen, 8, "3 HARVEST (MATURE CROPS)", x + 10, y + 112, 0, false);
		guiFont.render(screen, 8, "4 WATERING CAN (+50 WATER)", x + 10, y + 122, 0, false);
		guiFont.render(screen, 8, "5 SWORD (CLEAR PESTS)", x + 10, y + 132, 0, false);

		guiFont.render(screen, 8, "AIM: DEMONSTRATE OOP PRINCIPLES", x, y + 150, 0, false);

		// Back button
		renderMenuButton(MENU_BTN_X, 190, MENU_BTN_W, MENU_BTN_H, "BACK (ESC)", 0x6b2737);
	}

	// ==================== GAME RENDERING ====================

	private void renderGameScreen() {
		renderGrid();
		renderSelection();
		renderHUD();

		Weather w = grid.getCurrentWeather();
		if (w instanceof Rainy) {
			screen.applyRainOverlay(tickCounter);
		} else if (w instanceof Snowy) {
			screen.applySnowOverlay(tickCounter);
		}
	}

	private void renderGrid() {
		for (int y = 0; y < grid.getCols(); y++) {
			for (int x = 0; x < grid.getRows(); x++) {
				FarmCell cell = grid.getCell(x, y);
				char type = grid.getTileType(x, y);
				Sprite sprite;

				if (type == 'S') {
					sprite = getFarmlandSprite(x, y, cell.getMoistureLevel(), grid.getCurrentWeather());
				} else {
					// Randomized grass variety based on coordinates and weather
					int baseSeed = Math.abs(x * 7 + y * 13);
					String weatherName = grid.getCurrentWeather().getName();

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
						sprite = (variant == 0) ? Sprite.nGrassWinter3 : (variant == 1) ? Sprite.nGrassWinter4 : Sprite.nGrassWinter5;
					} else {
						// Sunny / Default logic: use vibrant green variants 1,2,3
						int variant = baseSeed % 3;
						sprite = (variant == 0) ? Sprite.nGrass1 : (variant == 1) ? Sprite.nGrass2 : Sprite.nGrass3;
					}
				}

				screen.renderSprite(x * 16, y * 16, sprite, false);

				Crop crop = cell.getCurrentCrop();
				if (crop != null) {
					Sprite cropSprite = crop.getSprite();
					if (cropSprite != null) {
						// Offset by -16px vertically for 32px sprites to "stand" on the cell
						int yOffset = (cropSprite.getHeight() > 16) ? -16 : 0;
						screen.renderSprite(x * 16, y * 16 + yOffset, cropSprite, false);
					}
				}
				if (cell.hasPests())
					screen.renderSprite(x * 16, y * 16, Sprite.ladybug, false);
			}
		}
	}

    private Sprite getFarmlandSprite(int x, int y, int moisture, Weather weather) {
        boolean u = grid.getTileType(x, y - 1) == 'S';
        boolean d = grid.getTileType(x, y + 1) == 'S';
        boolean l = grid.getTileType(x - 1, y) == 'S';
        boolean r = grid.getTileType(x + 1, y) == 'S';

        int variant = 0; // 0=Dry, 1=Wet1, 2=Wet2
        if (moisture >= 80) variant = 2;
        else if (moisture >= 40) variant = 1;

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

	private void renderSelection() {
		if (selectedX != -1) {
			screen.renderOutline(selectedX * 16, selectedY * 16, 16, 16, 0xffffffff);
		}
	}

	private void renderHUD() {
		// Top Right Display: Balance and Day
		guiFont.render(screen, 8, "BALANCE: $" + balance, width - 180, 10, 0, false);
		guiFont.render(screen, 8, "DAY: " + day, width - 180, 20, 0, false);

		// Status Messages (Top Left)
		guiFont.render(screen, 8, message, 10, 10, 0, false);

		// Back to menu hint (Top Right corner)
		guiFont.render(screen, 8, "ESC:MENU", width - 50, 2, 0, false);

		// Bottom Left Info Area
		int hudX = 10;
		int hudYStart = 175;
		guiFont.render(screen, 8, "WEATHER: " + grid.getCurrentWeather().getName().toUpperCase(), hudX, hudYStart, 0,
				false);
		guiFont.render(screen, 8, "TOOL: " + selectedTool.toString().replace("_", " "), hudX, hudYStart + 10, 0, false);

		if (selectedX != -1) {
			FarmCell cell = grid.getCell(selectedX, selectedY);
			String cellMain = "CELL: " + selectedX + "," + selectedY + " WATER: " + cell.getMoistureLevel();
			if (cell.hasPests())
				cellMain += " [PEST!]";
			guiFont.render(screen, 8, cellMain, hudX, hudYStart + 20, 0, false);

			if (cell.getCurrentCrop() != null) {
				String cropInfo = "CROP: " + cell.getCurrentCrop().getClass().getSimpleName() + " ["
						+ cell.getCurrentCrop().getStage() + "]";
				guiFont.render(screen, 8, cropInfo, hudX, hudYStart + 30, 0, false);
			}
		}

		// Tool Icons
		int iconXBase = 210;
		// Slot 1: Dynamic Seed Icon (Adjusted 6px lower for height balance)
		renderToolIcon(iconXBase, 196, matureSprites[seedIndex], Tool.SEED_SHOP, "1", -16);
		renderToolIcon(iconXBase + 20, 190, Sprite.hoe, Tool.HARVEST, "2", 0);
		renderToolIcon(iconXBase + 40, 190, Sprite.wateringCan, Tool.WATERING_CAN, "3", 0);
		renderToolIcon(iconXBase + 60, 190, Sprite.sword, Tool.SWORD, "4", 0);

		// Advance Button
		int advX = 315;
		screen.renderSprite(advX, 190, Sprite.actionMenu, false);
		guiFont.render(screen, 8, "ADV (ENT)", advX + 5, 195, 0, false);
	}

	private void renderToolIcon(int x, int y, Sprite s, Tool t, String label, int yOffset) {
		screen.renderSprite(x, y + yOffset, s, false);
		guiFont.render(screen, 8, label, x + 5, y - 8, 0, false);
		if (selectedTool == t)
			screen.renderSprite(x, y, Sprite.select, false);
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.setTitle("Smart Farm Simulator");
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
