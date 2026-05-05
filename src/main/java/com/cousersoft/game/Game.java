package com.cousersoft.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.*;

import com.cousersoft.game.graphics.text.BitmapFont;
import com.cousersoft.game.graphics.Screen;
import com.cousersoft.game.graphics.Sprite;
import com.cousersoft.game.input.Mouse;
import com.cousersoft.game.input.Keyboard;
import com.cousersoft.game.simulation.*;
import com.cousersoft.game.input.Tool;
import static com.cousersoft.game.GameConstants.*;

import java.awt.Graphics;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = SCREEN_WIDTH;
	public static int height = SCREEN_HEIGHT;
	public GameContext ctx;
	public com.cousersoft.game.input.InputManager inputManager;
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int numUpdates = 0;
	public int numFrames = 0;

	// Debounce flags
	private boolean tabTriggered = false;
	private boolean mouseClicked = false;
	private boolean spaceTriggered = false;
	private boolean scaleKeyTriggered = false;
	private boolean arrowTriggered = false;
	private boolean enterTriggered = false;
	private boolean escapeTriggered = false;
	private boolean weatherKeyTriggered = false;
	private boolean pKeyTriggered = false;

	public Game() {
		ctx = new GameContext();
		inputManager = new com.cousersoft.game.input.InputManager();

		Dimension size = new Dimension(width * ctx.scale, height * ctx.scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		ctx.screen = new Screen(width, height);
		ctx.handler = new StateHandler();
		ctx.handler.setState(GameState.MENU);

		ctx.mouse = new Mouse();
		addMouseListener(ctx.mouse);
		addMouseMotionListener(ctx.mouse);

		ctx.keyboard = new Keyboard();
		addKeyListener(ctx.keyboard);
		setFocusTraversalKeysEnabled(false);

		ctx.grid = new FarmGrid(GRID_ROWS, GRID_COLS);

		ctx.guiFont = new BitmapFont("/font maps/monogram-bitmap.json");
		ctx.cropCatalog = CropData.getAllCrops();
	}

	private void resetGame() {
		ctx.day = STARTING_DAY;
		ctx.balance = STARTING_BALANCE;
		ctx.selectedX = -1;
		ctx.selectedY = -1;
		ctx.selectedTool = Tool.NONE;
		ctx.message = "Welcome to Smart Farm!";
		ctx.grid = new FarmGrid(GRID_ROWS, GRID_COLS);
		ctx.showQuitConfirm = false;
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


	private com.cousersoft.game.state.StateUpdater menuUpdater = new com.cousersoft.game.state.MenuUpdater();
	private com.cousersoft.game.state.StateUpdater helpUpdater = new com.cousersoft.game.state.HelpUpdater();
	private com.cousersoft.game.state.StateUpdater gameUpdater = new com.cousersoft.game.state.GameUpdater();
	private com.cousersoft.game.state.StateUpdater shopUpdater = new com.cousersoft.game.state.ShopUpdater();
	private com.cousersoft.game.render.StateRenderer menuRenderer = new com.cousersoft.game.render.MenuRenderer();
	private com.cousersoft.game.render.StateRenderer helpRenderer = new com.cousersoft.game.render.HelpRenderer();
	private com.cousersoft.game.render.StateRenderer gameRenderer = new com.cousersoft.game.render.GameRenderer();
	private com.cousersoft.game.render.StateRenderer shopRenderer = new com.cousersoft.game.render.ShopRenderer();

	public void update() {
		ctx.keyboard.update();
		ctx.tickCounter++;

		GameState state = ctx.handler.getState();

		switch (state) {
			case MENU -> menuUpdater.update(ctx, inputManager);
			case HELP -> helpUpdater.update(ctx, inputManager);
			case GAME -> gameUpdater.update(ctx, inputManager);
			case SHOP -> shopUpdater.update(ctx, inputManager);
		}

		if (ctx.scaleChanged) {
			ctx.scaleChanged = false;
			Dimension size = new Dimension(width * ctx.scale, height * ctx.scale);
			this.setPreferredSize(size);
			this.setMinimumSize(size);
			this.setMaximumSize(size);
			frame.pack();
			frame.setLocationRelativeTo(null);
		}
	}

	// ==================== RENDERING ====================

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		ctx.screen.clear();

		GameState state = ctx.handler.getState();
		switch (state) {
			case MENU -> menuRenderer.render(ctx);
			case HELP -> helpRenderer.render(ctx);
			case GAME -> gameRenderer.render(ctx);
			case SHOP -> shopRenderer.render(ctx);
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = ctx.screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	// renderShop extracted

	// renderMenu and renderHelp extracted

	// renderGameScreen and rendering helpers extracted

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


