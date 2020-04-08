package com.escaperoom.engine.cosmetics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Textures {
	public int[] pixels;
	private String location, name;
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
			System.out.println(w + " " + h);
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public static final Textures wall = new Textures("src\\main\\resources\\walltexture.png", 64, "wall");
	public static final Textures brick = new Textures("src\\main\\resources\\sign.png", 64, "brick");
	public static final Textures water = new Textures("src\\main\\resources\\floor.png", 64, "water");
	public static final Textures door = new Textures("src\\main\\resources\\door.png", 64, "door");
	public static final Textures barrel = new Textures("src\\main\\resources\\barrel.png", 64, "barrel");
	public static final Textures chest = new Textures("src\\main\\resources\\chest.png", 64, "chest");
	public static final Textures redKey = new Textures("src\\main\\resources\\redKey.png", 64, "redKey");
	public static final Textures doorKey = new Textures("src\\main\\resources\\key.png", 64, "doorKey");
	public static final Textures tv = new Textures("src\\main\\resources\\tv.png", 64, "tv");
	public static final Textures blueBox = new Textures("src\\main\\resources\\blueBox.png", 64, "blueBox");
	public static final Textures redBox = new Textures("src\\main\\resources\\redBox.png", 64, "redBox");
	public static final Textures greenBox = new Textures("src\\main\\resources\\greenBox.png", 64, "greenBox");
	public static final Textures hitButtonSign = new Textures("src\\main\\resources\\hitButtonSign.png", 64,
			"hitButtonSignBox");
	public static final Textures hintEqual = new Textures("src\\main\\resources\\hintEqual.png", 64, "hintEqual");
	public static final Textures blockWall = new Textures("src\\main\\resources\\blockWall.png", 64, "blockWall");
	public static final Textures zeroPercent = new Textures("src\\main\\resources\\zero.png", 64, "zero");
	public static final Textures tenPercent = new Textures("src\\main\\resources\\tenPercent.png", 64, "tenPercent");
	public static final Textures fiftyPercent = new Textures("src\\main\\resources\\fiftyPercent.png", 64, "fiftyPercent");
	public static final Textures eightyPercent = new Textures("src\\main\\resources\\eightyPercent.png", 64, "eightyPercent");
	public static final Textures PUZZLE_ACTIVATE = new Textures("src\\main\\resources\\puzzleActivate.jpg", 64, "puzzleActivate");
	public static final Textures GREEN_KEY = new Textures("src\\main\\resources\\greenKey.png", 64, "greenKey");;

	
}
