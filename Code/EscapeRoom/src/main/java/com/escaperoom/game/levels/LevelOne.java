package com.escaperoom.game.levels;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import com.escaperoom.engine.Audio;
import com.escaperoom.engine.Camera;
import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.engine.cosmetics.Textures;
import com.escaperoom.game.GameInfo;
import com.escaperoom.game.levels.puzzles.SlidingPuzzle;

public class LevelOne extends Level {
	private boolean gotKey;

	private Sprites doorKey = new Sprites(1.5, 5, Textures.doorKey, false, 2, 2, 192);
	private Sprites tv = new Sprites(1.30, 9.20, Textures.tv, false, 2, 2, 192);
	private Sprites blueBox = new Sprites(2, 13.99, Textures.blueBox, false, 5, 5, 0);
	private Sprites redBox = new Sprites(3, 13.99, Textures.redBox, false, 5, 5, 0);
	private Sprites greenBox = new Sprites(4, 13.99, Textures.greenBox, false, 5, 5, 0);
	private Sprites hintEqual = new Sprites(2, 9.20, Textures.hintEqual, false, 4, 4, 0);
	private Sprites hitButtonSign = new Sprites(5, 13.90, Textures.hitButtonSign, false, 4, 4, 0);
	private Sprites glassOne = new Sprites(5.90, 11, Textures.zeroPercent, false, 2, 2, 0);
	private Sprites glassTwo = new Sprites(5.90, 12, Textures.zeroPercent, false, 2, 2, 0);
	private Sprites glassThree = new Sprites(5.90, 13, Textures.zeroPercent, false, 2, 2, 0);
	
	//top left room stuff
	private Sprites binNum = new Sprites(7.5, 1.5, Textures.BIN_NUM, false, 5, 5, 32);
	private Sprites decNum = new Sprites(7.5, 2.5, Textures.DEC_NUM, false, 5, 5, 32);
	private Sprites hexNum = new Sprites(7.5, 3.5, Textures.HEX_NUM, false, 5, 5, 32);
	private Sprites prim1 = new Sprites(4.5, 3.5, Textures.prim1, false, 5, 5, 32);
	private Sprites prim2 = new Sprites(4.5, 2.5, Textures.prim2, false, 5, 5, 32);
	private Sprites scnd = new Sprites(4.5, 1.5, Textures.scnd, false, 5, 5, 32);
	private Sprites cat = new Sprites(2.5, 1.5, Textures.cat, false, 5, 5, 32);
	private Sprites mouse = new Sprites(2.5, 3.5, Textures.mouse, false, 5, 5, 32);
	private Sprites bird = new Sprites(2.5, 2.5, Textures.bird, false, 5, 5, 32);
	
	
	private long lastButtonPressTime;
	private int buttonOne, buttonTwo, buttonThree = 0;
	private boolean doorClosed = false;
	private boolean roomDone = false;
	private boolean TLbool = false;

	int slidingPuzzleSize = 2;
	private SlidingPuzzle slidingPuzzle = new SlidingPuzzle(slidingPuzzleSize);
	private Sprites slidingPuzzleSprite = new Sprites(11.6, 1.10, Textures.PUZZLE_ACTIVATE, false, 5, 5, 0);
	private Sprites greenKey = new Sprites(0, 0, Textures.GREEN_KEY, false, 0, 0, 0);

	public LevelOne() {
		int[][] map = { 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4 }, 
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, 
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4 } };
		super.setMap(map);
		loadTextures();
		loadSprites();
	}

	@Override
	protected void loadTextures() {
		super.addTexture(Textures.Lv1Wall);
		super.addTexture(Textures.brick);
		super.addTexture(Textures.door);
		super.addTexture(Textures.Lv1Floor);
		super.addTexture(Textures.bloodWall);
		super.addTexture(Textures.topLeftHint);
	}

	@Override
	protected void loadSprites() {
		super.clearAllSprites();
		super.addSprite(doorKey);
		super.addSprite(tv);
		super.addSprite(redBox);
		super.addSprite(blueBox);
		super.addSprite(greenBox);
		super.addSprite(hitButtonSign);
		super.addSprite(slidingPuzzleSprite);
		super.addSprite(binNum);
		super.addSprite(decNum);
		super.addSprite(hexNum);
		super.addSprite(prim1);
		super.addSprite(prim2);
		super.addSprite(scnd);
		super.addSprite(cat);
		super.addSprite(mouse);
		super.addSprite(bird);
	}

	@Override
	public void levelLogic(GameInfo gameInfo) {
		double cameraX = gameInfo.getCameraPositionX();
		double cameraY = gameInfo.getCameraPositionY();
		KeyEvent lastKeyPressed = gameInfo.getLastKeyPressed();

		// If the player is doing a puzzle
		if (gameInfo.getActivePuzzle() != null) {
			// Wait for puzzle completion
			while (!slidingPuzzle.isPuzzleSolved() && !slidingPuzzle.isQuitting()) {
				// Wait 500 seconds
				sleep(500);
			}

			//If the user solved the puzzle
			if(slidingPuzzle.isPuzzleSolved()) {
				// Give player green key
				gameInfo.getPlayer().addItemToInventory(greenKey);
			}

			// End the method
			return;

		}

		// If the player pressed the pickup button
		if (lastKeyPressed != null && lastKeyPressed.getKeyCode() == KeyEvent.VK_E) {
			if (super.isNearObject(doorKey, cameraX, cameraY) && !gotKey) {
				gotKey = true;
				gameInfo.getPlayer().addItemToInventory(doorKey);
				super.removeSprite(doorKey);
			}
			// Actions taken after player interacts with the red button
			if (super.isNearObject(redBox, cameraX, cameraY) && super.getMap()[4][8] == 0) {
				lastButtonPressTime = System.currentTimeMillis();
				Audio.playSound(new File("src\\main\\resources\\deeplaugh.wav"));
				super.removeSprite(hitButtonSign);
				super.addSprite(hintEqual);
				super.addSprite(glassOne);
				super.addSprite(glassTwo);
				super.addSprite(glassThree);
				doorClosed = true;
			}

			if (System.currentTimeMillis() - lastButtonPressTime > 5000 && doorClosed
					&& nearAnyButton(cameraX, cameraY)) {
				lastButtonPressTime = System.currentTimeMillis();
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				System.out.println(buttonOne);
				System.out.println(buttonTwo);
				System.out.println(buttonThree);
				bloodGlassesLogic();
			}

			// If the player pressed the pickup button
			if (lastKeyPressed != null && lastKeyPressed.getKeyCode() == KeyEvent.VK_E) {
				bloodRoomLogic(cameraX, cameraY);
			}
			
			// If the player activated the sliding puzzle
			if (!slidingPuzzle.isPuzzleSolved() && super.isNearObject(slidingPuzzleSprite, cameraX, cameraY)) {
				// If the user has quit before
				if (slidingPuzzle.isQuitting()) {
					// Genrate a new puzzle
					slidingPuzzle = new SlidingPuzzle(slidingPuzzleSize);
				}

				gameInfo.setActivePuzzle(slidingPuzzle);

			}

			if((int)cameraX == 7 && (int)cameraY == 5 && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				super.getMap()[7][4] = 0;
				super.getMap()[7][6] = 1;
				TLbool = true;
			}
			
			if(super.isNearObject(hexNum, cameraX, cameraY) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				super.getMap()[6][2] = 0;
			}
			else if( (super.isNearObject(binNum, cameraX, cameraY) || super.isNearObject(decNum, cameraX, cameraY) ) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				TLbool = false;
				super.getMap()[7][4] = 6;
				super.getMap()[7][6] = 0;
				gameInfo.change_pos = true;
				gameInfo.setCameraPositionX(7);
				gameInfo.setCameraPositionY(13);
			}
			
			if(super.isNearObject(scnd, cameraX, cameraY) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				super.getMap()[3][2] = 0;
			}
			else if( (super.isNearObject(prim1, cameraX, cameraY) || super.isNearObject(prim2, cameraX, cameraY) ) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				TLbool = false;
				super.getMap()[7][4] = 6;
				super.getMap()[7][6] = 0;
				gameInfo.change_pos = true;
				gameInfo.setCameraPositionX(7);
				gameInfo.setCameraPositionY(13);
			}

			if(super.isNearObject(bird, cameraX, cameraY) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				super.getMap()[1][4] = 0;
				super.getMap()[1][6] = 0;
				super.getMap()[7][4] = 0;
				super.getMap()[7][6] = 0;
				super.removeSprite(binNum); super.removeSprite(decNum); super.removeSprite(hexNum);
				super.removeSprite(scnd); super.removeSprite(prim1); super.removeSprite(prim2);
				super.removeSprite(mouse); super.removeSprite(cat); super.removeSprite(bird);
				TLbool = false;
			}
			else if( (super.isNearObject(cat, cameraX, cameraY) || super.isNearObject(mouse, cameraX, cameraY) ) && TLbool && lastKeyPressed.getKeyCode() == KeyEvent.VK_E)
			{
				TLbool = false;
				super.getMap()[7][4] = 6;
				super.getMap()[7][6] = 0;
				gameInfo.change_pos = true;
				gameInfo.setCameraPositionX(7);
				gameInfo.setCameraPositionY(13);
			}
			
		}

	}

	// All the below methods are used for the bloodRoom
	private void bloodRoomLogic(double cameraX, double cameraY) {
		// Actions taken after player interacts with the red button
		if (super.isNearObject(redBox, cameraX, cameraY) && super.getMap()[4][8] == 0) {
			lastButtonPressTime = System.currentTimeMillis();
			Audio.playSound(new File("src\\main\\resources\\deeplaugh.wav"));
			super.removeSprite(hitButtonSign);
			super.addSprite(hintEqual);
			super.addSprite(glassOne);
			super.addSprite(glassTwo);
			super.addSprite(glassThree);
			super.getMap()[4][8] = 5;
			doorClosed = true;
			System.out.println("Entering changing walls trapped");
			changeWallsTrapped();
		}

		// Making sure with the time that the player does not spam the interact button
		if (System.currentTimeMillis() - lastButtonPressTime > 5000 && doorClosed && nearAnyButton(cameraX, cameraY)) {
			lastButtonPressTime = System.currentTimeMillis();
			Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
			bloodGlassesLogic();
		}
	}

	// Method to see what textures to change the buttons based on player button
	// presses
	private void bloodGlassesLogic() {
		int indexOfGlassOne = super.getSprites().indexOf(glassOne);
		int indexOfGlassTwo = super.getSprites().indexOf(glassTwo);
		int indexOfGlassThree = super.getSprites().indexOf(glassThree);
		ArrayList<Sprites> spriteList = super.getSprites();
		if (buttonOne == 1 && buttonTwo == 0 && buttonThree == 0) {
			spriteList.get(indexOfGlassOne).texture = Textures.tenPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.eightyPercent;
		} else if (buttonOne == 1 && buttonTwo == 1 && buttonThree == 0) {
			spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.tenPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.eightyPercent;
		} else if (buttonOne == 1 && buttonTwo == 1 && buttonThree == 1) {
			spriteList.get(indexOfGlassOne).texture = Textures.tenPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.zeroPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.eightyPercent;
		} else if (buttonOne == 0 && buttonTwo == 1 && buttonThree == 1) {
			spriteList.get(indexOfGlassOne).texture = Textures.eightyPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.tenPercent;
		} else if (buttonOne == 0 && buttonTwo == 0 && buttonThree == 1) {
			spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.tenPercent;
		}
		// Equal
		else if (buttonOne == 1 && buttonTwo == 0 && buttonThree == 1) {
			spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.fiftyPercent;
			roomDone = true;
			getMap()[4][8] = 0;
		} else if (buttonOne == 0 && buttonTwo == 1 && buttonThree == 0) {
			spriteList.get(indexOfGlassOne).texture = Textures.eightyPercent;
			spriteList.get(indexOfGlassTwo).texture = Textures.zeroPercent;
			spriteList.get(indexOfGlassThree).texture = Textures.zeroPercent;
		}
	}

	// Method to count amount of presses of the buttons in a room
	private boolean nearAnyButton(double cameraX, double cameraY) {
		if (super.isNearObject(redBox, cameraX, cameraY)) {
			if (buttonTwo < 1)
				buttonTwo++;
			else
				buttonTwo--;
			return true;

		} else if (super.isNearObject(blueBox, cameraX, cameraY)) {
			if (buttonOne < 1)
				buttonOne++;
			else
				buttonOne--;
			return true;
		} else if (super.isNearObject(greenBox, cameraX, cameraY)) {
			if (buttonThree < 1)
				buttonThree++;
			else
				buttonThree--;
			return true;

		}
		return false;
	}

	private void changeWallsTrapped() {
		for (int i = 8; i < 15; i++) {
			getMap()[0][i] = 5;
		}

		for (int i = 1; i < 6; i++) {
			getMap()[i][14] = 5;
		}
		for (int i = 8; i < 14; i++) {
			getMap()[6][i] = 5;
		}
		for (int i = 1; i < 6; i++) {
			getMap()[i][8] = 5;
		}
	}
	// End of bloodRoom Methods

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
