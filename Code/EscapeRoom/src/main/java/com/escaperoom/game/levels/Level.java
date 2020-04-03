package com.escaperoom.game.levels;

import java.util.ArrayList;

import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.engine.cosmetics.Textures;
import com.escaperoom.game.GameInfo;

public abstract class Level {

	private ArrayList<Textures> textures = new ArrayList<Textures>();
	private ArrayList<Sprites> sprites = new ArrayList<Sprites>();
	private int[][] map;
	
	
	//Abstract functions
	protected abstract void loadTextures();
	protected abstract void loadSprites();
	public abstract void levelLogic(GameInfo gameInfo);
	private Sprites addToInventory;
	
	
	//Setters
	public void addTexture(Textures texture) {
		textures.add(texture);
	}
	
	public void removeTexture(Textures texture) {
		if(textures.contains(texture)) {
			textures.remove(texture);
		}
	}
	
	public void addSprite(Sprites sprite) {
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprites sprite) {
		if(sprites.contains(sprite)) {
			sprites.remove(sprite);
		}
	}
	public void clearAllSprites(){
		sprites.clear();
	}
	public void setMap(int[][] map) {
		this.map = map;
	}
	
	
	//Getters
	public ArrayList<Textures> getTextures() {
		return textures;
	}
	
	public ArrayList<Sprites> getSprites() {
		return sprites;
	}
	
	public int[][] getMap() {
		return map;
	}
	
	
	//Returns true if the player is between -0.5 or +0.5 positions away from the object
	public boolean isNearObject(Sprites sprite, double currentCameraX, double currentCameraY) {
		return (currentCameraY > (sprite.y-0.5) && currentCameraY < (sprite.y+0.5)) && 
				(currentCameraX > (sprite.x-0.5) && currentCameraX < (sprite.x+0.5));
	}
	public void setAddToInventory(Sprites sprite){
		this.addToInventory = sprite;
	}
	public Sprites addInventory(){
		return addToInventory;
	}
}
