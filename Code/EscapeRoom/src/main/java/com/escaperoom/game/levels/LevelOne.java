package com.escaperoom.game.levels;

import com.escaperoom.engine.Audio;
import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.engine.cosmetics.Textures;
import com.escaperoom.game.GameInfo;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class LevelOne extends Level {
    private boolean gotKey;

    private Sprites doorKey = new Sprites(3, 4, Textures.doorKey, false,2,2,192);
    private Sprites tv = new Sprites(1.30,9.20,Textures.tv,false,2,2,192);
    private Sprites blueBox = new Sprites(2,13.99,Textures.blueBox,false,5,5,0);
    private Sprites redBox = new Sprites(3,13.99,Textures.redBox,false,5,5,0);
    private Sprites greenBox = new Sprites(4,13.99,Textures.greenBox,false,5,5,0);
    private Sprites hintEqual = new Sprites(2,9.20,Textures.hintEqual,false,4,4,0);
    private Sprites hitButtonSign = new Sprites(5,13.90,Textures.hitButtonSign,false,4,4,0);
    private Sprites glassOne = new Sprites(5.90,11,Textures.zeroPercent,false,2,2,0);
    private Sprites glassTwo = new Sprites(5.90,12,Textures.zeroPercent,false,2,2,0);
    private Sprites glassThree = new Sprites(5.90,13,Textures.zeroPercent,false,2,2,0);
    private long lastButtonPressTime;
    private Sprites lastButtonPress;
    private int buttonOne,buttonTwo,buttonThree = 0;
    private boolean doorClosed = false;




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
        super.addTexture(Textures.blockWall);
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
    }

    @Override
    public void levelLogic(GameInfo gameInfo) {
        double cameraX = gameInfo.getCameraPositionX();
        double cameraY = gameInfo.getCameraPositionY();
        KeyEvent lastKeyPressed = gameInfo.getLastKeyPressed();


        // If the player pressed the pickup button
        if (lastKeyPressed != null && lastKeyPressed.getKeyCode() == KeyEvent.VK_E) {
            if (super.isNearObject(doorKey, cameraX, cameraY) && !gotKey) {
                gotKey = true;
                gameInfo.getPlayer().addItemToInventory(doorKey);
                super.removeSprite(doorKey);
            }
            //Actions taken after player interacts with the red button
            if(super.isNearObject(redBox,cameraX,cameraY) && super.getMap()[4][8] == 0){
                lastButtonPressTime = System.currentTimeMillis();
                Audio.playSound(new File("src\\main\\resources\\deeplaugh.wav"));
                super.removeSprite(hitButtonSign);
                super.addSprite(hintEqual);
                super.addSprite(glassOne);
                super.addSprite(glassTwo);
                super.addSprite(glassThree);
                super.getMap()[4][8] = 5;
                doorClosed = true;
            }

            if (System.currentTimeMillis() - lastButtonPressTime >5000 &&doorClosed &&  nearAnyButton(cameraX,cameraY)){
                lastButtonPressTime = System.currentTimeMillis();
                Audio.playSound(new File("src\\main\\resources\\ItemPickupSound.wav"));
                System.out.println(buttonOne);
                System.out.println(buttonTwo);
                System.out.println(buttonThree);
                bloodGlassesLogic();
            }

        }

    }
    // Method to see what textures to change the buttons based on player button presses
    private void bloodGlassesLogic(){
        int indexOfGlassOne = super.getSprites().indexOf(glassOne);
        int indexOfGlassTwo = super.getSprites().indexOf(glassTwo);
        int indexOfGlassThree = super.getSprites().indexOf(glassThree);
        ArrayList<Sprites> spriteList = super.getSprites();
        if(buttonOne ==1 && buttonTwo == 0 && buttonThree == 0){
            spriteList.get(indexOfGlassOne).texture = Textures.tenPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.eightyPercent;
        }
        else if(buttonOne == 1 && buttonTwo == 1 && buttonThree == 0){
            spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.tenPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.eightyPercent;
        }
        else if(buttonOne == 1 && buttonTwo == 1 && buttonThree == 1){
            spriteList.get(indexOfGlassOne).texture = Textures.tenPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.zeroPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.eightyPercent;
        }
        else if(buttonOne == 0 && buttonTwo == 1 && buttonThree ==1){
            spriteList.get(indexOfGlassOne).texture = Textures.eightyPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.tenPercent;
        }
        else if(buttonOne == 0 && buttonTwo == 0 && buttonThree == 1){
            spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.tenPercent;
        }
        // Equal
        else if(buttonOne == 1 && buttonTwo == 0 && buttonThree == 1){
            spriteList.get(indexOfGlassOne).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.fiftyPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.fiftyPercent;
        }
        else if(buttonOne == 0 && buttonTwo == 1 && buttonThree == 0){
            spriteList.get(indexOfGlassOne).texture = Textures.eightyPercent;
            spriteList.get(indexOfGlassTwo).texture = Textures.zeroPercent;
            spriteList.get(indexOfGlassThree).texture = Textures.zeroPercent;
        }
    }
    // Method to count amount of presses of the buttons in a room 
    private boolean nearAnyButton(double cameraX, double cameraY){
        if(super.isNearObject(redBox,cameraX,cameraY)) {
            if(buttonTwo < 1)
                buttonTwo++;
            else
                buttonTwo--;
            return true;

        }
        else if(super.isNearObject(blueBox,cameraX,cameraY)) {
            if(buttonOne < 1)
                buttonOne++;
            else
                buttonOne--;
            return true;
        }
        else if(super.isNearObject(greenBox,cameraX,cameraY)) {
            if(buttonThree < 1)
                buttonThree++;
            else
                buttonThree--;
            return true;

        }
        return false;
    }
}
