package com.escaperoom.ui.menu;

import java.awt.Color;
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
import com.escaperoom.ui.component.SliderPanel;

@SuppressWarnings("serial")
public class OptionsMenu extends JPanel {

	// Cosmetic variables about the buttons
	private final int buttonWidth = 400;
	private final int buttonHeight = 75;

	// Components of the OptionsMenu
	private Picture gameLogo;
	private SliderPanel sensitivitySlider;
	private SliderPanel musicSlider;
	private Button backButton;

	// Layout of the JPanel (Similar to HBox or VBox in JavaFX)
	private BoxLayout boxLayout;

	public OptionsMenu() {
		super.setBackground(Color.LIGHT_GRAY);

		createComponents();
		addComponents();
	}

	// Adds all the components to the JPanel
	private void addComponents() {
		super.setLayout(boxLayout);

		addPadding(25, 25);
		super.add(gameLogo);
		addPadding(25, 25);
		super.add(sensitivitySlider);
		super.add(musicSlider);
		addPadding(25, 25);
		super.add(backButton);
		addPadding(25, 25);
		addPadding(25, 25);

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

		// Initialize components
		sensitivitySlider = new SliderPanel("SENSITIVITY", 45, 155, 85);
		musicSlider = new SliderPanel("MUSIC", 0, 100, 10);
		backButton = new Button("BACK", buttonWidth, buttonHeight);

		// Initialize layout
		boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
	}

	// Attaches an ActionListener to all the buttons. ActionListener is from
	// EscapeRoom.java
	public void addActionListner(ActionListener actionListener) {
		backButton.addActionListener(actionListener);
	}

	public Button getBackButton() {
		return backButton;
	}

	// Gets the rotation speed from the slider
	public double getRotationSpeed() {
		return sensitivitySlider.getValue() * .001;
	}
	
	// Gets the volume from the slider
	public double getVolume() 
	{
		return musicSlider.getValue();
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Image backgroundImage = new ImageIcon("src/main/resources/pause-menu-bg.jpg").getImage();
		g.drawImage(backgroundImage, 0, 0, null);
	}

}
