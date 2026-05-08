package com.cousersoft.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.cousersoft.game.controller.GameController;
import com.cousersoft.game.controller.HelpController;
import com.cousersoft.game.controller.MenuController;
import com.cousersoft.game.controller.ShopController;
import com.cousersoft.game.controller.StateUpdater;
import com.cousersoft.game.graphics.Screen;
import com.cousersoft.game.graphics.text.BitmapFont;
import com.cousersoft.game.input.InputManager;
import com.cousersoft.game.input.Keyboard;
import com.cousersoft.game.input.Mouse;
import com.cousersoft.game.input.Tool;
import com.cousersoft.game.model.CropData;
import com.cousersoft.game.model.FarmGrid;
import com.cousersoft.game.view.GameRenderer;
import com.cousersoft.game.view.HelpRenderer;
import com.cousersoft.game.view.MenuRenderer;
import com.cousersoft.game.view.ShopRenderer;
import com.cousersoft.game.view.StateRenderer;

import static com.cousersoft.game.GameConstants.*;

/**
 * Core game loop and AWT canvas.
 * P3 FIX: {@code main()} has been moved to {@link GameLauncher}.
 *          This class now focuses exclusively on the game loop (start/stop/run/update/render).
 */
public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public GameContext ctx;
    public InputManager inputManager;

    private Thread thread;
    private boolean running = false;

    private final BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public int numUpdates = 0;
    public int numFrames = 0;

    // ── Controllers ──────────────────────────────────────────────────────────
    private final StateUpdater menuController = new MenuController();
    private final StateUpdater helpController  = new HelpController();
    private final StateUpdater gameController  = new GameController();
    private final StateUpdater shopController  = new ShopController();

    // ── Renderers ────────────────────────────────────────────────────────────
    private final StateRenderer menuRenderer = new MenuRenderer();
    private final StateRenderer helpRenderer  = new HelpRenderer();
    private final StateRenderer gameRenderer  = new GameRenderer();
    private final StateRenderer shopRenderer  = new ShopRenderer();

    // ── Init ─────────────────────────────────────────────────────────────────

    public Game() {
        ctx = new GameContext();
        inputManager = new InputManager();

        Dimension size = new Dimension(SCREEN_WIDTH * ctx.scale, SCREEN_HEIGHT * ctx.scale);
        setPreferredSize(size);

        ctx.screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT);
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

    // ── Lifecycle ────────────────────────────────────────────────────────────

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

    // ── Game Loop ────────────────────────────────────────────────────────────

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1_000_000_000.0 / 60.0;
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

    // ── Update ───────────────────────────────────────────────────────────────

    public void update() {
        ctx.keyboard.update();
        ctx.tickCounter++;

        switch (ctx.handler.getState()) {
            case MENU -> menuController.update(ctx, inputManager);
            case HELP -> helpController.update(ctx, inputManager);
            case GAME -> gameController.update(ctx, inputManager);
            case SHOP -> shopController.update(ctx, inputManager);
        }

        if (ctx.scaleChanged) {
            ctx.scaleChanged = false;
            if (ctx.scaleChangedCallback != null) {
                ctx.scaleChangedCallback.run();
            }
        }
    }

    // ── Render ───────────────────────────────────────────────────────────────

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        ctx.screen.clear();

        switch (ctx.handler.getState()) {
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
}
