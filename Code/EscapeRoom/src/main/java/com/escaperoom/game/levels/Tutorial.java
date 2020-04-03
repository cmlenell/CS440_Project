package com.escaperoom.game.levels;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;

import com.escaperoom.engine.Audio;
import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.engine.cosmetics.Textures;
import com.escaperoom.game.GameInfo;
import com.escaperoom.ui.component.Inventory;

public class Tutorial extends Level {

	// Sprites that can be interacted with
	private Sprites doorKey = new Sprites(1.5, 13, Textures.doorKey, false);
	private Sprites redKey = new Sprites(4.5, 5.5, Textures.redKey, false);
	private Sprites chest = new Sprites(7, 11.5, Textures.chest,true);


	private boolean isDoorOpen = false;
	private boolean hasRedKey = false;


	public Tutorial() {
		int[][] map = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
										{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
										{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
										{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
										{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 2, 1, 1, 1 },
										{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
										{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
										{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
										{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
										{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 },
										{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 },
										{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 1, 1, 1, 1, 0, 4 },
										{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 1, 1, 1, 1, 0, 4 },
										{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
										{ 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };
						
		super.setMap(map);
		loadTextures();
		loadSprites();
	}



	@Override
	protected void loadTextures() {
		super.addTexture(Textures.wall);
		super.addTexture(Textures.brick);
		super.addTexture(Textures.door);
		super.addTexture(Textures.water);

	}

	@Override
	protected void loadSprites() {
		super.addSprite(new Sprites(4, 3.5, Textures.barrel, true));
		super.addSprite(new Sprites(3.5, 4.5, Textures.barrel, true));
		super.addSprite(chest);
		super.addSprite(doorKey);
	}

	@Override
	public void levelLogic(GameInfo gameInfo) {
		double cameraX = gameInfo.getCameraPositionX();
		double cameraY = gameInfo.getCameraPositionY();
		KeyEvent lastKeyPressed = gameInfo.getLastKeyPressed();

		// If the player pressed the pickup button
		if (lastKeyPressed != null && lastKeyPressed.getKeyCode() == KeyEvent.VK_E) {

			// If the player is near the door key
			if (super.isNearObject(doorKey, cameraX, cameraY) && !gameInfo.getPlayer().hasItem(doorKey)) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				gameInfo.getPlayer().addItemToInventory(doorKey);
				super.removeSprite(doorKey);
				super.addSprite(redKey);

			} else if (super.isNearObject(redKey, cameraX, cameraY) && isDoorOpen && !hasRedKey) {
				Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
				gameInfo.getPlayer().addItemToInventory(redKey);
				super.removeSprite(redKey);
				hasRedKey = true;


			} else if (super.isNearObject(chest, cameraX, cameraY) && gameInfo.getPlayer().hasItem(redKey)) {
				//Remove item from inventory
				gameInfo.getPlayer().removeItemFromInventory(redKey);

				JOptionPane.showMessageDialog(null, "The chest is open!!! \n You can leave now");
			}
		}

		// If the player is near the door and has the key
		if ((cameraY > 10 && cameraY < 11.5) && (cameraX > 3.8) && gameInfo.getPlayer().hasItem(doorKey)
				&& !isDoorOpen) {
			// Open the door
			super.getMap()[4][11] = 0;
			super.getMap()[2][2] = 2;
			isDoorOpen = true;

			//Remove item from inventory
			gameInfo.getPlayer().removeItemFromInventory(doorKey);
		}

	}

}
