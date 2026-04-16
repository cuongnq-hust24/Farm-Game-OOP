package com.cousersoft.game.graphics;

import java.util.Random;

public class Screen {
	public int width;
	public int height;
	public int[] pixels; //Array to hold every pixel on the screen
	public final int MAP_SIZE = 64; //Map size in tiles ( * 16 to get total number of pixels)
	public final int MAP_SIZE_MASK = MAP_SIZE -1;
	public int xOffset;
	public int yOffset;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE]; // the array size should be map size by map size. 64 tiles along x axis and y axis
	private Random random = new Random();
	public int red;
	public int blue;
	public int green;
	
	//Initializer for screen object
	public Screen(int width, int height) { //All screens require a width and a height
		//Set the screen's width and height to the variables that were passed into the method
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; //pixels array should be as big as the window
		
		//Cycle through each pixel and set them to black
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	//Method to render sprites to the screen
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		for (int y = 0; y < h; y++) {
			int ya = y + yp;
			for (int x = 0; x < w; x++) {
				int xa = x + xp;
				if (xa < -w || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * w];
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderParticle(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		int tempCol;
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		int w = sprite.getWidth();
		int h = sprite.getHeight();
		for (int y = 0; y < h; y++) {
			int ya = y + yp;
			for (int x = 0; x < w; x++) {
				int xa = x + xp;
				if (xa < -w || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				if (color == 0)
					tempCol = sprite.pixels[x + y * w];
				else
					tempCol = color;
				if (tempCol != 0xffff00ff) {
					pixels[xa + ya * width] = tempCol;
				}
			}
		}
	}
	
	public void renderText(int xp, int yp, Sprite sprite, int textColor, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < 16; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff) {
					if (textColor == 0) {
						pixels[xa + ya * width] = col;
					} else {
						pixels[xa + ya * width] = textColor;
					}
				}
			}
		}
	}
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
	public void renderSmallText(int xp, int yp, Sprite sprite, int textColor, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < 8; y++) {
			int ya = y + yp;
			for (int x = 0; x < 8; x++) {
				int xa = x + xp;
				if (xa < -8 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * 8];
				if (col != 0xffff00ff) {
					if (textColor == 0) {
						pixels[xa + ya * width] = col;
					} else {
						pixels[xa + ya * width] = textColor;
					}
				}
			}
		}
	}
	
			
	//Method to clear the screen
	public void clear() {
		//Cycle through each pixel and set it to black
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void renderPixel(int xp, int yp, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		if (xp < 0 || xp >= width || yp < 0 || yp >= height)
			return;
		if (color != 0xffff00ff)
			pixels[xp + yp * width] = color;
	}
	
	public int getRGB(String hex) {
		if (hex.length() > 1) {
			String temp;
			int colVal;
			temp = hex.substring(2, 4);
			red = Integer.parseInt(temp, 16);
			temp = hex.substring(4, 6);
			green = Integer.parseInt(temp, 16);
			temp = hex.substring(6, 8);
			blue = Integer.parseInt(temp, 16);
			colVal = darkenTile(red, green, blue);
			return colVal;
		} else {
			return 0;
		}
	}
	
    public int darkenTile(int r, int g, int b) {
        int col = 0;
        if (r > 20) r -= 20; else r = 0;
        if (g > 20) g -= 20; else g = 0;
        if (b > 20) b -= 20; else b = 0;
        
        int colVal = (r << 16) | (g << 8) | b;
        return colVal;
    }

    public void renderOutline(int xp, int yp, int w, int h, int color) {
        for (int x = xp; x < xp + w; x++) {
            if (x < 0 || x >= width) continue;
            if (yp >= 0 && yp < height) pixels[x + yp * width] = color;
            if (yp + h - 1 >= 0 && yp + h - 1 < height) pixels[x + (yp + h - 1) * width] = color;
        }
        for (int y = yp; y < yp + h; y++) {
            if (y < 0 || y >= height) continue;
            if (xp >= 0 && xp < width) pixels[xp + y * width] = color;
            if (xp + w - 1 >= 0 && xp + w - 1 < width) pixels[(xp + w - 1) + y * width] = color;
        }
    }

    /**
     * Fills a rectangle with the given color.
     */
    public void fillRect(int xp, int yp, int w, int h, int color) {
        for (int y = yp; y < yp + h; y++) {
            if (y < 0 || y >= height) continue;
            for (int x = xp; x < xp + w; x++) {
                if (x < 0 || x >= width) continue;
                pixels[x + y * width] = color;
            }
        }
    }

    /**
     * Applies a rain overlay effect: renders rain sprites at fixed positions
     * and adds a subtle blue color tint to the entire screen.
     */
    public void applyRainOverlay(int tick) {
        // Select rain sprite frame based on tick for animation
        Sprite rainSprite;
        int frame = (tick / 10) % 3;
        if (frame == 0) rainSprite = Sprite.rain1;
        else if (frame == 1) rainSprite = Sprite.rain2;
        else rainSprite = Sprite.rain3;

        // Render rain sprites at several positions across the grid area
        int spacing = 48;
        int yShift = (tick * 2) % spacing; // Falling effect
        for (int rx = 0; rx < width; rx += spacing) {
            for (int ry = -16 + yShift; ry < height; ry += spacing) {
                renderSprite(rx, ry, rainSprite, false);
            }
        }

        // Apply a subtle blue tint to all pixels
        for (int i = 0; i < pixels.length; i++) {
            int col = pixels[i];
            int r = (col >> 16) & 0xFF;
            int g = (col >> 8) & 0xFF;
            int b = col & 0xFF;
            r = (int)(r * 0.92);
            g = (int)(g * 0.95);
            b = Math.min(255, (int)(b * 1.08));
            pixels[i] = (r << 16) | (g << 8) | b;
        }
    }

    /**
     * Applies a heat wave overlay: shifts all pixel colors towards orange/red.
     */
    public void applyHeatOverlay() {
        for (int i = 0; i < pixels.length; i++) {
            int col = pixels[i];
            int r = (col >> 16) & 0xFF;
            int g = (col >> 8) & 0xFF;
            int b = col & 0xFF;
            r = Math.min(255, (int)(r * 1.1));
            g = (int)(g * 0.85);
            b = (int)(b * 0.6);
            pixels[i] = (r << 16) | (g << 8) | b;
        }
    }

    /**
     * Applies a snow overlay: adds a slight white tint to the screen
     * and a falling snow particle animation.
     */
    public void applySnowOverlay(int tick) {
        // Falling snow particles (using actual snow.png sprites)
        Sprite snowSprite;
        int frame = (tick / 20) % 3; // Slower animation than rain
        if (frame == 0) snowSprite = Sprite.snow1;
        else if (frame == 1) snowSprite = Sprite.snow2;
        else snowSprite = Sprite.snow3;

        int spacing = 64; // Sparser than rain
        int yShift = (tick / 2) % spacing; // Slower fall speed
        for (int rx = 0; rx < width + spacing; rx += spacing) {
            // Add a horizontal jitter for a "drifting" effect
            int drift = (int) (Math.sin((tick + rx) * 0.05) * 8);
            for (int ry = -16 + yShift; ry < height; ry += spacing) {
                renderSprite(rx + drift, ry, snowSprite, false);
            }
        }

        // Apply a subtle white tint to all pixels
        for (int i = 0; i < pixels.length; i++) {
            int col = pixels[i];
            int r = (col >> 16) & 0xFF;
            int g = (col >> 8) & 0xFF;
            int b = col & 0xFF;
            // Shift towards white
            r = Math.min(255, (int)(r * 1.03 + 5));
            g = Math.min(255, (int)(g * 1.03 + 5));
            b = Math.min(255, (int)(b * 1.05 + 8));
            pixels[i] = (r << 16) | (g << 8) | b;
        }
    }
	
}

