package com.cousersoft.game.graphics.text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.cousersoft.game.graphics.Screen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A crisp bitmap font renderer that supports custom character mapping
 * from a JSON bitmask definition.
 */
public class BitmapFont {
    
    private Map<String, int[]> charMap;
    private final int CHAR_HEIGHT = 12;
    private final int CHAR_WIDTH = 5;

    public BitmapFont(String jsonPath) {
        try {
            Gson gson = new Gson();
            InputStream is = BitmapFont.class.getResourceAsStream(jsonPath);
            if (is == null) {
                System.err.println("Could not find font map: " + jsonPath);
                return;
            }
            InputStreamReader reader = new InputStreamReader(is);
            Type type = new TypeToken<Map<String, int[]>>(){}.getType();
            charMap = gson.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders text to the screen using the bitmask map.
     * Includes support for the legacy color tags (\r Red, \b Blue, \" Green, \' Default).
     */
    public void render(Screen screen, String text, int xp, int yp, int color, int scale, boolean shadow, boolean fixed) {
        if (charMap == null) return;
        
        // Pass 1: Render Shadow (Black)
        if (shadow) {
            renderPass(screen, text, xp + 1, yp + 1, 0x000000, scale, fixed);
        }

        // Pass 2: Render main color
        renderPass(screen, text, xp, yp, color, scale, fixed);
    }

    private void renderPass(Screen screen, String text, int xp, int yp, int color, int scale, boolean fixed) {
        int xOffset = 0;
        int yOffset = 0;
        int currentColor = color;
        int originalColor = color;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Handle legacy color codes
            if (c == '\r') { currentColor = 0xffff0000; continue; }
            if (c == '\b') { currentColor = 0xff0000ff; continue; }
            if (c == '\"') { currentColor = 0xff00ff00; continue; }
            if (c == '\'') { currentColor = originalColor; continue; }

            // Handle layout
            if (c == '\n') {
                xOffset = 0;
                yOffset += (CHAR_HEIGHT + 2) * scale;
                continue;
            }
            if (c == ' ') {
                xOffset += (CHAR_WIDTH + 1) * scale;
                continue;
            }

            int[] mask = charMap.get(String.valueOf(c));
            if (mask != null) {
                renderChar(screen, mask, xp + xOffset, yp + yOffset, currentColor, scale, fixed);
                xOffset += (CHAR_WIDTH + 1) * scale;
            }
        }
    }

    private void renderChar(Screen screen, int[] mask, int xp, int yp, int color, int scale, boolean fixed) {
        for (int row = 0; row < mask.length; row++) {
            int rowValue = mask[row];
            // Each char is 5 pixels wide (bits 0-4)
            for (int col = 0; col < CHAR_WIDTH; col++) {
                // Bit 0 is the leftmost pixel in this format
                int bit = (rowValue >> col) & 1;
                if (bit == 1) {
                    // Draw a scaled block
                    for (int sx = 0; sx < scale; sx++) {
                        for (int sy = 0; sy < scale; sy++) {
                            screen.renderPixel(xp + col * scale + sx, yp + row * scale + sy, color, fixed);
                        }
                    }
                }
            }
        }
    }
}
