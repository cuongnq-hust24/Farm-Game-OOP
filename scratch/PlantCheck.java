import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlantCheck {
    public static void main(String[] args) {
        try {
            File file = new File("src/main/resources/textures/plant_test.png");
            BufferedImage img = ImageIO.read(file);
            System.out.println("Width: " + img.getWidth());
            System.out.println("Height: " + img.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
