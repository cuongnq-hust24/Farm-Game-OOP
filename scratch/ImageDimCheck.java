import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageDimCheck {
    public static void main(String[] args) {
        try {
            File f = new File("src/main/resources/textures/snow.png");
            BufferedImage img = ImageIO.read(f);
            System.out.println("Width: " + img.getWidth());
            System.out.println("Height: " + img.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
