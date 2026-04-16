import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCheck {
    public static void main(String[] args) {
        try {
            File file = new File("src/main/resources/textures/test_tile01.png");
            BufferedImage img = ImageIO.read(file);
            System.out.println("Width: " + img.getWidth());
            System.out.println("Height: " + img.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
