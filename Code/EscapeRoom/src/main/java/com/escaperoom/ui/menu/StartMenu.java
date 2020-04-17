package com.escaperoom.ui.menu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.escaperoom.ui.component.Button;
import com.escaperoom.ui.component.Picture;

@SuppressWarnings("serial")
public class StartMenu extends JPanel {

	// Cosmetic variables about the buttons
	private final int buttonWidth = 400;
	private final int buttonHeight = 75;

	// Components of the PauseMenu
	private Picture gameLogo;
	private Button tutorial;
	private Button levelOneButton;

	// Layout of the JPanel (Similar to HBox or VBox in JavaFX)
	private BoxLayout boxLayout;

	public StartMenu() {
		createComponents();
		addComponents();
	}

	// Adds all the components to the JPanel
	private void addComponents() {
		super.setLayout(boxLayout);

		addPadding(25, 25);
		super.add(gameLogo);
		addPadding(100, 85);
		super.add(tutorial);
		addPadding(100, 20);
		super.add(levelOneButton);
		addPadding(100, 20);
	}

	// Adds an empty component to the JPanel that acts as padding for the other
	// components
	private void addPadding(int width, int height) {
		super.add(Box.createRigidArea(new Dimension(width, height)));
	}

	// Initializes all the components we will need for the pause menu
	private void createComponents() {
		// Initialize images
		gameLogo = new Picture("src/main/resources/logo.png", 500, 250);

		// Initialize buttons
		tutorial = new Button("Tutorial", buttonWidth, buttonHeight);
		levelOneButton = new Button("Level 1", buttonWidth, buttonHeight);

		// Initialize layout
		boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Image backgroundImage = new ImageIcon("src/main/resources/start-menu-bg.jpg").getImage();
		g.drawImage(backgroundImage , 0, 0, null);
	}

	// Attaches an ActionListener to all the buttons. ActionListener is from
	// EscapeRoom.java
	public void addActionListners(ActionListener actionListener) {
		tutorial.addActionListener(actionListener);
		levelOneButton.addActionListener(actionListener);
	}

	public Button getTutorialButton() {
		return tutorial;
	}

	public Button getLevelOneButton() {
		return levelOneButton;
	}
}
