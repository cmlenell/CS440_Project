import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Textures {

        public int[] pixels;
        private String location;
        public final int SIZE;
        public Textures(String location, int size) {
            location = location;
            SIZE = size;
            pixels = new int[SIZE * SIZE];
            load();
        }
        private void load() {
            try {
                BufferedImage image = ImageIO.read(new File(location));
                int w = image.getWidth();
                int h = image.getHeight();
                image.getRGB(0, 0, w, h, pixels, 0, w);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

