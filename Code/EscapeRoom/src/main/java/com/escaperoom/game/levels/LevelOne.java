package com.escaperoom.game.levels;

import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.engine.cosmetics.Textures;
import com.escaperoom.game.GameInfo;

import java.awt.event.KeyEvent;

public class LevelOne extends Level {
    private boolean gotKey;

    private Sprites doorKey = new Sprites(3, 4, Textures.doorKey, false,2,2,192);
    private Sprites tv = new Sprites(1.30,9.20,Textures.tv,false,2,2,0);

    public LevelOne() {
        int[][] map = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 4},
                {1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4}};
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
        super.clearAllSprites();
        super.addSprite(doorKey);
        super.addSprite(tv);


    }

    @Override
    public void levelLogic(GameInfo gameInfo) {
        double cameraX = gameInfo.getCameraPositionX();
        double cameraY = gameInfo.getCameraPositionY();
        KeyEvent lastKeyPressed = gameInfo.getLastKeyPressed();

        // If the player pressed the pickup button
        if (lastKeyPressed != null && lastKeyPressed.getKeyCode() == KeyEvent.VK_E) {
            if (super.isNearObject(doorKey, gameInfo.getCameraPositionX(), gameInfo.getCameraPositionY())) {
                gotKey = true;
                gameInfo.getPlayer().addItemToInventory(doorKey);
                super.removeSprite(doorKey);
            }
        }

    }
}
