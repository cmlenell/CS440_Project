package com.escaperoom.game.actors;

import java.awt.*;
import java.util.ArrayList;

import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.game.levels.Level;
import com.escaperoom.ui.component.Inventory;

public class Player  {
    Inventory display;

	
	private ArrayList<Sprites> inventory = new ArrayList<Sprites>(5);





    public void addItemToInventory(Sprites sprite) {
    	inventory.add(sprite);
    	display.update(this,sprite);
    }
    
    public void removeItemFromInventory(Sprites sprite) {
    	if(inventory.contains(sprite)) {
    		inventory.remove(sprite);
            display.remove(sprite);
    	}

    }
    
    public boolean hasItem(Sprites sprite) {
    	return inventory.contains(sprite);
    }
    public ArrayList<Sprites> getInventory(){return inventory;}
    public void setDisplay(Inventory display){
         this.display = display;
    }

   
}
