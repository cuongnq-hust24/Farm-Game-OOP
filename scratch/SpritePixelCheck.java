import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpritePixelCheck {
    public static void main(String[] args) {
        try {
            // Re-simulate SpriteSheet loading and Sprite extraction
            File file = new File("src/main/resources/textures/plant_test.png");
            BufferedImage image = ImageIO.read(file);
            int w = image.getWidth();
            int h = image.getHeight();
            int[] sheetPixels = new int[w * h];
            image.getRGB(0, 0, w, h, sheetPixels, 0, w);

            // Check Seed (Row 3, Column 0) - Size 16x32
            int startX = 0 * 16;
            int startY = 3 * 32;
            int spriteW = 16;
            int spriteH = 32;
            
            System.out.println("Sheet Size: " + w + "x" + h);
            System.out.println("Extracting at: (" + startX + ", " + startY + ")");
            
            int nonPinkCount = 0;
            int totalCount = 0;
            for (int y = 0; y < spriteH; y++) {
                for (int x = 0; x < spriteW; x++) {
                    int col = sheetPixels[(x + startX) + (y + startY) * w];
                    if (col != 0xffff00ff) nonPinkCount++;
                    totalCount++;
                }
            }
            
            System.out.println("Sprite Extracted! Total pixels: " + totalCount);
            System.out.println("Non-Pink Pixels: " + nonPinkCount);
            if (nonPinkCount == 0) {
                System.out.println("WARNING: Sprite is 100% pink or wrong coordinates!");
            }
            
            // Check first few pixels
            for(int i=0; i<10; i++) {
                System.out.printf("Pixel %d: %x\n", i, sheetPixels[startX + startY*w + i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
