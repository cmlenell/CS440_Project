package com.escaperoom.game.actors;

import java.util.ArrayList;

import com.escaperoom.engine.cosmetics.Sprites;

public class Player {
	
	private ArrayList<Sprites> inventory = new ArrayList<Sprites>();
 
    
    public void addItemToInventory(Sprites sprite) {
    	inventory.add(sprite);
    }
    
    public void removeItemFromInventory(Sprites sprite) {
    	if(inventory.contains(sprite)) {
    		inventory.remove(sprite);
    	}
    }
    
    public boolean hasItem(Sprites sprite) {
    	return inventory.contains(sprite);
    }
   
}
