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

import javax.swing.*;

public class LevelOne extends Level {
	private boolean gotKey;
	// bloodRoom stuff
    private Sprites blueKey = new Sprites(0,0,Textures.blueKey,false,1,1,0);
	private Sprites doorKey = new Sprites(1.5, 5, Textures.doorKey, false, 2, 2, 192);
	private Sprites tv = new Sprites(1.30, 9.20, Textures.tv, false, 2, 2, 192);
	private Sprites blueBox = new Sprites(2, 13.99, Textures.bloodButton, false, 5, 5, 0);
	private Sprites redBox = new Sprites(3, 13.99, Textures.bloodButton, false, 5, 5, 0);
	private Sprites greenBox = new Sprites(4, 13.99, Textures.bloodButton, false, 5, 5, 0);
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

	//bottom right
	private Sprites button1 = new Sprites(10.7,13.99,Textures.button, false,2,2,0);
	private Sprites button2 = new Sprites(13.99, 10.7, Textures.button, false,2,2,0);
	private Sprites button3 = new Sprites(13.99, 12.7, Textures.button, false,2,2,0);
	private Sprites button4 = new Sprites(12.7, 13.99, Textures.button, false,2,2,0);
	private Sprites spring = new Sprites(9.05, 10.05, Textures.spring, false,2,2,0);
	private Sprites summer = new Sprites(9.05, 10.5, Textures.summer, false,2,2,0);
	private Sprites fall = new Sprites(9.05, 11, Textures.fall, false,2,2,0);
	private Sprites winter = new Sprites(9.05, 11.5, Textures.winter, false,2,2,0);
	private Sprites redKey = new Sprites(11.5,11.5,Textures.redKey,false,2,2,0);
	
	private long lastButtonPressTime;
	private int buttonOne, buttonTwo, buttonThree = 0;
	private boolean doorClosed = false;
	private boolean bloodRoomDone = false;
	private boolean TLbool = false;
	private boolean fourButtonsDone = false;
	private boolean first = false, second = false, third = false, fourth = false;
	private boolean hasRedKey = false;
    private boolean hasBlueKey = false;
    private boolean hasGreenKey = false;
    private boolean hasAllKeys = false;

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
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 7 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, 
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 10 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 8, 4, 9, 4, 4 } };
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
		// bottom right
		super.addTexture(Textures.springWall);
		super.addTexture(Textures.summerWall);
		super.addTexture(Textures.fallWall);
		super.addTexture(Textures.winterWall);
		super.addTexture(Textures.finalDoor);
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
		//bottom right
		super.addSprite(button1);
		super.addSprite(button2);
		super.addSprite(button3);
		super.addSprite(button4);
		super.addSprite(spring);
		super.addSprite(summer);
		super.addSprite(fall);
		super.addSprite(winter);
	}

	@Override
	public void levelLogic(GameInfo gameInfo) {
		double cameraX = gameInfo.getCameraPositionX();
		double cameraY = gameInfo.getCameraPositionY();
		KeyEvent lastKeyPressed = gameInfo.getLastKeyPressed();
		if(hasGreenKey && hasBlueKey && hasRedKey && gotKey) {
            getMap()[7][14] = 11;
            hasAllKeys = true;
        }


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
				hasGreenKey = true;
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
			    if(hasAllKeys)
			        if(cameraX > 6 && cameraX < 8 && cameraY >13.5 && cameraX < 14)
                        System.exit(0);
			    if(!bloodRoomDone)
				    bloodRoomLogic(cameraX, cameraY);
                else
                   if(!hasBlueKey) {
                       gameInfo.getPlayer().addItemToInventory(blueKey);
                       hasBlueKey = true;
                   }
				if (!fourButtonsDone) {
					four_buttons(cameraX, cameraY);
				}
				else if (!hasRedKey){
					if (super.isNearObject(redKey,cameraX, cameraY)) {
						gameInfo.getPlayer().addItemToInventory(redKey);
						super.removeSprite(redKey);
						hasRedKey = true;
					}
				}

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
		if (System.currentTimeMillis() - lastButtonPressTime > 2500 && doorClosed && nearAnyButton(cameraX, cameraY)) {
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
			bloodRoomDone = true;
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

	private void four_buttons(double cameraX, double cameraY) {

		if(super.isNearObject(button1, cameraX, cameraY)) {
			if (!first) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				first = true;
				System.out.println("pressed first button");
			}
		}
		else if(super.isNearObject(button2, cameraX, cameraY)) {
			if (first && !second && !third && !fourth) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				second = true;
				System.out.println("pressed second button");
			}
		}
		else if(super.isNearObject(button3, cameraX, cameraY)) {
			if (first && second && !third && !fourth) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				third = true;
				System.out.println("pressed third button");
			}
		}
		else if(super.isNearObject(button4, cameraX, cameraY)) {
			if (first && second && third && !fourth) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				fourth = true;
				System.out.println("pressed fourth button");
			}
		}
		if(first && second && third && fourth && !fourButtonsDone) { // all buttons were pressed in the correct order
			Audio.playSound(new File("src\\main\\resources\\deeplaugh.wav"));
			System.out.println("All buttons pressed");
			fourButtonsDone = true;
			super.addSprite(redKey);

		}

	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
