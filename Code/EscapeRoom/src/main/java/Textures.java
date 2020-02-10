
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import javax.imageio.ImageIO;
public class Textures {
        public int[] pixels;
        private String location;
        public final int SIZE;
        public Textures(String location, int size) {
            this.location = location;
            SIZE = size;
            load();
        }
        private void load() {
            try {
                BufferedImage image = ImageIO.read(new File(location));
                int w = image.getWidth();
                int h = image.getHeight();
                System.out.println( w + " " + h);
                pixels = new int [w*h];
                image.getRGB(0, 0, w, h, pixels, 0,w);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    public static Textures wood = new Textures("C:\\Users\\Deonvell Shed\\Documents\\GitHub\\Group-1-Spring-2020\\Code\\EscapeRoom\\src\\main\\resources\\small.png",64);
    public static Textures brick = new Textures("C:\\Users\\Deonvell Shed\\Documents\\GitHub\\Group-1-Spring-2020\\Code\\EscapeRoom\\src\\main\\resources\\real.png",64 );
    public static Textures bluestone = new Textures("C:\\Users\\Deonvell Shed\\Documents\\GitHub\\Group-1-Spring-2020\\Code\\EscapeRoom\\src\\main\\resources\\real.png", 64);
    public static Textures chest = new Textures("C:\\Users\\Deonvell Shed\\Documents\\GitHub\\Group-1-Spring-2020\\Code\\EscapeRoom\\src\\main\\resources\\real.png", 64);
}

