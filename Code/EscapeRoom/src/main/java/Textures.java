
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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


    public static Textures wall = new Textures("src\\main\\resources\\walltexture.png",64);
    public static Textures brick = new Textures("src\\main\\resources\\sign.png",64 );
    public static Textures water = new Textures("src\\main\\resources\\floor.png", 64);
    public static Textures door = new Textures("src\\main\\resources\\door.png", 64);
    public static Textures barrel = new Textures("C:\\Users\\Deonvell Shed\\Documents\\GitHub\\Group-1-Spring-2020\\Code\\EscapeRoom\\src\\main\\resources\\barrel.png",64);
}

