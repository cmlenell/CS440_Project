
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import components.Button;
import components.Picture;

public class PauseMenu extends JPanel {

	// Cosmetic variables about the buttons
	private final int buttonWidth = 400;
	private final int buttonHeight = 75;

	// Components of the PauseMenu
	private Picture gameLogo;
	private Button resumeButton;
	private Button optionsButton;
	private Button quitButton;

	// Layout of the JPanel (Similar to HBox or VBox in JavaFX)
	private BoxLayout boxLayout;

	// Menus
	private OptionsMenu optionsMenu;

	public PauseMenu() {
		createComponents();
		addComponents();
	}

	// Adds all the components to the JPanel
	private void addComponents() {
		super.setLayout(boxLayout);

		addPadding(25, 25);
		super.add(gameLogo);
		addPadding(100, 85);
		super.add(resumeButton);
		addPadding(100, 20);
		super.add(optionsButton);
		addPadding(100, 20);
		super.add(quitButton);
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
		resumeButton = new Button("RESUME", buttonWidth, buttonHeight);
		optionsButton = new Button("OPTIONS", buttonWidth, buttonHeight);
		quitButton = new Button("QUIT", buttonWidth, buttonHeight);

		// Initialize layout
		boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

		// Initialize menus
		optionsMenu = new OptionsMenu();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Image backgroundImage = new ImageIcon("src/main/resources/pause-menu-bg.jpg").getImage();
		g.drawImage(backgroundImage , 0, 0, null);
	}

	// Attaches an ActionListener to all the buttons. ActionListener is from
	// EscapeRoom.java
	public void addActionListners(ActionListener actionListener) {
		resumeButton.addActionListener(actionListener);
		optionsButton.addActionListener(actionListener);
		quitButton.addActionListener(actionListener);
		optionsMenu.addActionListner(actionListener);
	}

	public Button getResumeButton() {
		return resumeButton;
	}

	public Button getOptionsButton() {
		return optionsButton;
	}

	public Button getQuitButton() {
		return quitButton;
	}

	public OptionsMenu getOptionsMenu() {
		return optionsMenu;
	}
}
