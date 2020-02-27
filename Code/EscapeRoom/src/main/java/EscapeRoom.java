import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EscapeRoom extends JFrame implements Runnable, ActionListener, KeyListener {
	private boolean seenDoor = false;
	private static final long serialVersionUID = 1L;
	private int mapWidth = 15;
	private int mapHeight = 15;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	private int[] pixels;
	private ArrayList<Textures> textures;
	private ArrayList<Sprites> sprites;
	private Camera camera;
	private Screen screen;
	private Dimension screenSize;
	private static EscapeRoom game;
	private static JFrame inventory; // Bottom container Jframe
	private static JPanel bottom;
	private static Sprites redKey = new Sprites(4.5, 5.5, Textures.redKey);
	private static Sprites doorKey = new Sprites(1.5, 13, Textures.doorKey);
	private Player character;
	private boolean isDoorOpen = false;
	private PauseMenu pauseMenu;

	public static int[][] map = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 3, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 }, { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };

	public EscapeRoom() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		thread = new Thread(this);
		image = new BufferedImage(screenSize.width - 400, screenSize.height - 400, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Textures>();
		textures.add(Textures.wall); // 1
		textures.add(Textures.brick);// 2
		textures.add(Textures.door); // 3
		textures.add(Textures.water);// 4
		sprites = new ArrayList<Sprites>();
		sprites.add(new Sprites(4, 3.5, Textures.barrel));
		sprites.add(new Sprites(3.5, 4.5, Textures.barrel));
		sprites.add(new Sprites(7, 11.5, Textures.chest));
		sprites.add(doorKey);
		camera = new Camera(4.5, 5.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, sprites, screenSize.width - 400,
				screenSize.height - 400, seenDoor);
		setSize(screenSize.width - 400, screenSize.height - 400);
		setUndecorated(true);
		addKeyListener(camera);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setVisible(true);
		super.addKeyListener(this);
		pauseMenu = new PauseMenu();
		pauseMenu.addActionListners(this);

		start();
		character = new Player();

		// This section is all for the bottom of the screen inventory
		final Rectangle mainScreen = this.getBounds();
		inventory = new JFrame(); // Created an new JFrame to be independent of the main game screen
		bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 5));
		bottom.setBackground(Color.gray);
		inventory.add(bottom);
		inventory.setSize(screenSize.width - 400, 100);
		inventory.setLocation(mainScreen.x, mainScreen.y + mainScreen.height);
		inventory.setUndecorated(true);
		inventory.setVisible(true);
		inventory.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.yellow));

	}

	private synchronized void start() {
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

		bs.show();

	}

	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 30.0;// 60 times per second
		double delta = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta = delta + ((now - lastTime) / ns);
			lastTime = now;
			while (delta >= 1)// Make sure update is only happening 60 times a second
			{

				// handles all of the logic restricted time
				screen.update(camera, pixels);

				gameChecks();

				camera.update(map);
				delta--;
			}
			render();// displays to the screen unrestricted time
		}
	}

	public static void main(String[] args) {
		game = new EscapeRoom();
		// This JOptionPane is to only show at the start of the game
		JOptionPane.showMessageDialog(game, "USE THE ARROWS KEYS TO MOVE CHARACTER" + "\n" + "Move Foward: Up Arrow"
				+ "\n" + "Move Backward: Down Arrow" + "\n" + "Move Camera Left/Right: Left and Right Arrows");
	}

	void gameChecks() {

		if ((camera.yPos > 12.5 && camera.yPos < 13) && (camera.xPos > 1 && camera.xPos < 2)
				&& !character.HasDoorKey()) {
			if (camera.getLastKey().getKeyCode() == KeyEvent.VK_E) {
				playPickupSound();
				sprites.remove(doorKey);
				character.setHasDoorKey();
				sprites.add(redKey);
				System.out.println("Player Location: " + camera.xPos + ", " + camera.yPos);
			}

		}
		if ((camera.yPos > 10 && camera.yPos < 11.5) && (camera.xPos > 3.8) && character.HasDoorKey() && !isDoorOpen) {
			map[4][11] = 0;
			map[2][2] = 2;
			isDoorOpen = true;
		}

		if ((camera.xPos > 4 && camera.xPos < 5) && (camera.yPos > 5 && camera.yPos < 6) && isDoorOpen) {
			if (camera.getLastKey().getKeyCode() == KeyEvent.VK_E) {
				playPickupSound();
				sprites.remove(redKey);
				character.setHasChestKey();
			}
		}
		if ((camera.yPos > 11 && camera.yPos < 11.7) && (camera.xPos > 6.5 && camera.xPos < 7.5)
				&& character.HasChestKey()) {
			if (camera.getLastKey().getKeyCode() == KeyEvent.VK_E) {
				JOptionPane.showMessageDialog(game, "The chest is open!!! \n You can leave now");
			}
		}
	}

	// This method is just to be used for pickup item sound effect to warn the
	// player they have the item now
	void playPickupSound() {
		Clip clip = null;
		AudioInputStream sound = null;
		File file = new File("src\\main\\resources\\ItemPickupSound.wav");
		try {
			sound = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		assert clip != null;
		clip.setFramePosition(0);
		clip.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent event) {
		char letterReleased = event.getKeyChar();

		// If the user wants to pause the game
		if (letterReleased == 'p' || letterReleased == 'P') {
			stop();

			/*
			 * This ensures that we update the GUI in the Application Thread (Basically
			 * Platform.runLater() in JavaFX)
			 */
			SwingUtilities.invokeLater(() -> {
				requestFocus();
				super.add(pauseMenu);
				super.revalidate();
				super.repaint();
			});

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src.equals(pauseMenu.getQuitButton())) {
			System.exit(0);

			// If the user wants to resume the game
		} else if (src.equals(pauseMenu.getResumeButton())) {
			SwingUtilities.invokeLater(() -> {
				camera.setRotationSpeed(pauseMenu.getOptionsMenu().getRotationSpeed());
				requestFocus();
				super.remove(pauseMenu);
				super.revalidate();
				super.repaint();

				running = true;
				new Thread(this).start();

			});

		} else if (src.equals(pauseMenu.getOptionsButton())) {
			SwingUtilities.invokeLater(() -> {
				requestFocus();
				super.remove(pauseMenu);
				super.add(pauseMenu.getOptionsMenu());
				super.revalidate();
				super.repaint();
			});

		} else if (src.equals(pauseMenu.getOptionsMenu().getBackButton())) {
			SwingUtilities.invokeLater(() -> {
				requestFocus();
				super.remove(pauseMenu.getOptionsMenu());
				super.add(pauseMenu);
				super.revalidate();
				super.repaint();
			});
		}

	}

}
