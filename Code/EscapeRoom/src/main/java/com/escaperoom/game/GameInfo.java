package com.escaperoom.game;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import com.escaperoom.game.actors.Player;

public class GameInfo {

	private Player player;
	private double cameraPositionX;
	private double cameraPositionY;
	private KeyEvent lastKeyPressed;
	private JPanel activePuzzle = null;
	public boolean change_pos;

	public GameInfo(Player player) {
		this.player = player;
	}
	
	// Setters
	public void setCameraPositionX(double cameraPositionX) {
		this.cameraPositionX = cameraPositionX;
	}

	public void setCameraPositionY(double cameraPositionY) {
		this.cameraPositionY = cameraPositionY;
	}
	
	public void setLastKeyPressed(KeyEvent lastKeyPressed) {
		this.lastKeyPressed = lastKeyPressed;
	}
	
	public void setActivePuzzle(JPanel activePuzzle) {
		this.activePuzzle = activePuzzle;
	}

	// Getters
	public Player getPlayer() {
		return player;
	}

	public double getCameraPositionX() {
		return cameraPositionX;
	}

	public double getCameraPositionY() {
		return cameraPositionY;
	}
	
	public KeyEvent getLastKeyPressed() {
		return lastKeyPressed;
	}
	
	public JPanel getActivePuzzle() {
		return activePuzzle;
	}

}
