package com.escaperoom.engine.cosmetics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Textures {
        public int[] pixels;
        private String location,name;
        public final int SIZE;

        public Textures(String location, int size, String name) {
            this.location = location;
            this.name = name;
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
        public String getLocation(){
            return location;
        }
        public String getName(){
            return name;
        }


    public static Textures wall = new Textures("src\\main\\resources\\walltexture.png",64,"wall");
    public static Textures brick = new Textures("src\\main\\resources\\sign.png",64,"brick");
    public static Textures water = new Textures("src\\main\\resources\\floor.png", 64,"water");
    public static Textures door = new Textures("src\\main\\resources\\door.png", 64,"door");
    public static Textures barrel = new Textures("src\\main\\resources\\barrel.png",64,"barrel");
    public static Textures chest = new Textures("src\\main\\resources\\chest.png",64,"chest");
    public static Textures redKey = new Textures("src\\main\\resources\\redKey.png",64,"redKey");
    public static Textures doorKey = new Textures("src\\main\\resources\\key.png",64,"doorKey");
    public static Textures tv = new Textures("src\\main\\resources\\tv.png",64,"tv");



}

