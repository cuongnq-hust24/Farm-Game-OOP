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
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); 
	
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

	// Boundary for the interactable farm area
	private final int FARM_X_START = 2;
	private final int FARM_Y_START = 2;
	private final int FARM_X_END = 22;
	private final int FARM_Y_END = 11;

	// UI Buttons
	private final int BTN_W = 80, BTN_H = 20;
	private final int ADV_X = 300, ADV_Y = 190;
	
	private final int WHEAT_X = 140, WHEAT_Y = 190;
	private final int TOMATO_X = 165, TOMATO_Y = 190;
	private final int HARVEST_X = 190, HARVEST_Y = 190;
	private final int WATER_X = 240, WATER_Y = 190;
	private final int SWORD_X = 265, SWORD_Y = 190;

	// Costs
	private final int WHEAT_COST = 5;
	private final int TOMATO_COST = 10;

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		screen = new Screen(width, height);
		handler = new StateHandler();
		handler.setState("Game");
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		keyboard = new Keyboard();
		addKeyListener(keyboard);
		
		grid = new FarmGrid(25, 14);
		
		// Pre-plant some crops for visual testing
		grid.getCell(5, 5).plantCrop(new Wheat());
		grid.getCell(6, 5).plantCrop(new Tomato());
		grid.getCell(5, 5).setMoistureLevel(50);
		grid.getCell(6, 5).setMoistureLevel(80);

		guiFont = new Font();
		guiFont.initChars("Small");
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
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
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
	private boolean keyTriggered = false;
	private boolean scaleKeyTriggered = false;

	public void update() {
		keyboard.update();
		int mx = mouse.getX() / scale;
		int my = mouse.getY() / scale;

		// Handle Tool Selection Shortcuts
		if (keyboard.k1) selectedTool = Tool.PLANT_WHEAT;
		else if (keyboard.k2) selectedTool = Tool.PLANT_TOMATO;
		else if (keyboard.k3) selectedTool = Tool.HARVEST;
		else if (keyboard.k4) selectedTool = Tool.WATERING_CAN;
		else if (keyboard.k5) selectedTool = Tool.SWORD;

		// Handle Scale Changes
		if (keyboard.bracketLeft || keyboard.bracketRight) {
			if (!scaleKeyTriggered) {
				if (keyboard.bracketLeft) changeScale(-1);
				if (keyboard.bracketRight) changeScale(1);
				scaleKeyTriggered = true;
			}
		} else {
			scaleKeyTriggered = false;
		}

		// Handle Space for Advance Day
		if (keyboard.space) {
			if (!keyTriggered) {
				day++;
				grid.advanceDay();
				checkGameOver();
				keyTriggered = true;
			}
		} else {
			keyTriggered = false;
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
		if (newScale < 1) newScale = 1;
		if (newScale > 6) newScale = 6;
		
		if (newScale != scale) {
			scale = newScale;
			Dimension size = new Dimension(width * scale, height * scale);
			this.setPreferredSize(size);
			this.setMinimumSize(size);
			this.setMaximumSize(size);
			frame.pack();
			frame.setLocationRelativeTo(null);
			message = "Resolution: " + (width*scale) + "x" + (height*scale);
		}
	}

	private void handleClicks(int mx, int my) {
		// Tool selections
		if (mx >= WHEAT_X && mx <= WHEAT_X + 16 && my >= WHEAT_Y && my <= WHEAT_Y + 16) {
			selectedTool = Tool.PLANT_WHEAT;
		} else if (mx >= TOMATO_X && mx <= TOMATO_X + 16 && my >= TOMATO_Y && my <= TOMATO_Y + 16) {
			selectedTool = Tool.PLANT_TOMATO;
		} else if (mx >= HARVEST_X && mx <= HARVEST_X + 16 && my >= HARVEST_Y && my <= HARVEST_Y + 16) {
			selectedTool = Tool.HARVEST;
		} else if (mx >= WATER_X && mx <= WATER_X + 16 && my >= WATER_Y && my <= WATER_Y + 16) {
			selectedTool = Tool.WATERING_CAN;
		} else if (mx >= SWORD_X && mx <= SWORD_X + 16 && my >= SWORD_Y && my <= SWORD_Y + 16) {
			selectedTool = Tool.SWORD;
		} 
		// Advance Day button
		else if (mx >= ADV_X && mx <= ADV_X + BTN_W && my >= ADV_Y && my <= ADV_Y + BTN_H) {
			day++;
			grid.advanceDay();
			checkGameOver();
		} 
		// Grid interaction
		else {
			int gx = mx / 16;
			int gy = my / 16;
			if (gx >= FARM_X_START && gx <= FARM_X_END && gy >= FARM_Y_START && gy <= FARM_Y_END) {
				selectedX = gx;
				selectedY = gy;
				applyTool(gx, gy);
			}
		}
	}

	private void applyTool(int gx, int gy) {
		FarmCell cell = grid.getCell(gx, gy);
		if (cell == null) return;

		switch (selectedTool) {
			case WATERING_CAN -> cell.waterCell();
			case SWORD -> cell.clearPests();
			case PLANT_WHEAT -> {
				if (cell.isAvailable()) {
					if (balance >= WHEAT_COST) {
						cell.plantCrop(new Wheat());
						balance -= WHEAT_COST;
						message = "Planted Wheat! -$5";
					} else message = "Not enough money!";
				}
			}
			case PLANT_TOMATO -> {
				if (cell.isAvailable()) {
					if (balance >= TOMATO_COST) {
						cell.plantCrop(new Tomato());
						balance -= TOMATO_COST;
						message = "Planted Tomato! -$10";
					} else message = "Not enough money!";
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
            default -> {}
		}
	}

	private void checkGameOver() {
		if (balance < WHEAT_COST) {
			boolean hasActiveCrops = false;
			for(int y=0; y<grid.getCols(); y++) {
				for(int x=0; x<grid.getRows(); x++) {
					if (grid.getCell(x,y).getCurrentCrop() != null && grid.getCell(x,y).getCurrentCrop().getStage() != GrowthStage.DEAD) {
						hasActiveCrops = true;
						break;
					}
				}
			}
			if (!hasActiveCrops) message = "GAME OVER: Out of funds!";
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		renderGrid();
		renderSelection();
		renderHUD();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	private void renderGrid() {
		for (int y = 0; y < grid.getCols(); y++) {
			for (int x = 0; x < grid.getRows(); x++) {
				FarmCell cell = grid.getCell(x, y);
				Sprite sprite = Sprite.grass;
				if (x >= FARM_X_START && x <= FARM_X_END && y >= FARM_Y_START && y <= FARM_Y_END) sprite = Sprite.soil;

				screen.renderSprite(x * 16, y * 16, sprite, false);

				Crop crop = cell.getCurrentCrop();
				if (crop != null) {
					Sprite cropSprite = null;
					if (crop instanceof Wheat) {
						if (crop.getStage() == GrowthStage.SEED) cropSprite = Sprite.cornSeed;
						else if (crop.getStage() == GrowthStage.SEEDLING) cropSprite = Sprite.cornSprout;
						else if (crop.getStage() == GrowthStage.MATURE) cropSprite = Sprite.cornFull;
						else if (crop.getStage() == GrowthStage.DEAD) cropSprite = Sprite.tomato1Dead;
					} else if (crop instanceof Tomato) {
						if (crop.getStage() == GrowthStage.SEED) cropSprite = Sprite.tomatoSeed;
						else if (crop.getStage() == GrowthStage.SEEDLING) cropSprite = Sprite.tomatoSprout;
						else if (crop.getStage() == GrowthStage.MATURE) cropSprite = Sprite.tomatoFull;
						else if (crop.getStage() == GrowthStage.DEAD) cropSprite = Sprite.tomato1Dead;
					}
					if (cropSprite != null) screen.renderSprite(x * 16, y * 16, cropSprite, false);
				}
				if (cell.hasPests()) screen.renderSprite(x * 16, y * 16, Sprite.rock, false);
			}
		}
	}

	private void renderSelection() {
		if (selectedX != -1) {
			screen.renderOutline(selectedX * 16, selectedY * 16, 16, 16, 0xffffffff);
		}
	}

	private void renderHUD() {
		// Top Right Display: Balance and Day (stacked and moved further left to prevent clipping)
		guiFont.render(screen, 8, "BALANCE: $" + balance, width - 180, 10, 0, false);
		guiFont.render(screen, 8, "DAY: " + day, width - 180, 20, 0, false);
		
		// Status Messages (Top Left)
		guiFont.render(screen, 8, message, 10, 10, 0, false);

		// Bottom Left Info Area
		int hudX = 10;
		int hudYStart = 175;
		guiFont.render(screen, 8, "WEATHER: " + grid.getCurrentWeather().getName().toUpperCase(), hudX, hudYStart, 0, false);
		guiFont.render(screen, 8, "TOOL: " + selectedTool.toString().replace("_", " "), hudX, hudYStart + 10, 0, false);

		if (selectedX != -1) {
			FarmCell cell = grid.getCell(selectedX, selectedY);
			String cellMain = "CELL: " + selectedX + "," + selectedY + " WATER: " + cell.getMoistureLevel();
			if (cell.hasPests()) cellMain += " [PEST!]";
			guiFont.render(screen, 8, cellMain, hudX, hudYStart + 20, 0, false);

			if (cell.getCurrentCrop() != null) {
				String cropInfo = "CROP: " + cell.getCurrentCrop().getClass().getSimpleName() + " [" + cell.getCurrentCrop().getStage() + "]";
				guiFont.render(screen, 8, cropInfo, hudX, hudYStart + 30, 0, false);
			}
		}
		
		// Tool Icons (Shifted Right to avoid overlapping with text)
		int iconXBase = 210;
		renderToolIcon(iconXBase, 190, Sprite.cornSeedBag, Tool.PLANT_WHEAT, "1");
		renderToolIcon(iconXBase + 20, 190, Sprite.tomatoSeedBag, Tool.PLANT_TOMATO, "2");
		renderToolIcon(iconXBase + 40, 190, Sprite.hoe, Tool.HARVEST, "3");
		renderToolIcon(iconXBase + 60, 190, Sprite.wateringCan, Tool.WATERING_CAN, "4");
		renderToolIcon(iconXBase + 80, 190, Sprite.sword, Tool.SWORD, "5");

		// Advance Button (Moved further right)
		int advX = 315;
		screen.renderSprite(advX, 190, Sprite.actionMenu, false);
		guiFont.render(screen, 8, "ADV (SP)", advX + 5, 195, 0, false);
	}

	private void renderToolIcon(int x, int y, Sprite s, Tool t, String label) {
		screen.renderSprite(x, y, s, false);
		guiFont.render(screen, 8, label, x + 5, y - 8, 0, false);
		if (selectedTool == t) screen.renderSprite(x, y, Sprite.select, false);
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
