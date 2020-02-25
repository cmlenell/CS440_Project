import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JOptionPane;

public class EscapeRoom extends JFrame implements Runnable{
    private boolean  seenDoor = false;
    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Textures> textures;
    public Camera camera;
    public Screen screen;
    public Dimension screenSize;
    private static EscapeRoom game;
    private static JFrame inventory;
    public static JPanel bottom;



    public static int[][] map =
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,1,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,1,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,1,1,1,3,1,1,1},
                    {1,1,1,1,1,1,1,0,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,1,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,1,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                    {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
            };
    public EscapeRoom() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        thread = new Thread(this);
        image = new BufferedImage(screenSize.width-400, screenSize.height-400, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Textures>();
        textures.add(Textures.wood); // 1
        textures.add(Textures.brick);// 2
        textures.add(Textures.door); // 3
        textures.add(Textures.water);// 4
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
        screen = new Screen(map, mapWidth, mapHeight, textures, screenSize.width-400, screenSize.height-400);
        setSize(screenSize.width-400, screenSize.height-400);
        setUndecorated(true);
        addKeyListener(camera);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);
        start();

        // This section is all for the bottom of the screen inventory
        final Rectangle mainScreen = this.getBounds();
        inventory = new JFrame();                         // Created an new JFrame to be independent of the main game screen
        bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,5));
        bottom.setBackground(Color.gray);
        inventory.add(bottom);
        inventory.setSize(screenSize.width-400,100);
        inventory.setLocation(mainScreen.x,mainScreen.y+mainScreen.height);
        inventory.setUndecorated(true);
        inventory.setVisible(true);
        inventory.getRootPane().setBorder(BorderFactory.createMatteBorder(0,4,4,4,Color.yellow));

        /*
         * TODO Add an action text box 
         * 	would go somewhere here
         */

    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(),image.getHeight(),null);

        bs.show();

    }
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 30.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //handles all of the logic restricted time
                screen.update(camera, pixels);
                if (map[screen.xPlayerpostion][screen.yPlayerpostion] == 3 && !seenDoor && (camera.yPos>= 9 && camera.xPos>=2)){
                    System.out.println("Player Location: "+ camera.xPos + ", " + camera.yPos);
                    JOptionPane.showMessageDialog(game,"Hello");
                    seenDoor = true;
                }
                camera.update(map);

                delta--;
            }
            render();//displays to the screen unrestricted time
        }
    }
    public static void main(String [] args) {
        game = new EscapeRoom();
        // This JOptionPane is to only show at the start of the game
        JOptionPane.showMessageDialog(game,"USE THE ARROWS KEYS TO MOVE CHARACTER" + "\n"+
                "Move Foward: Up Arrow" + "\n"+
                "Move Backward: Down Arrow" + "\n"+
                "Move Camera Left/Right: Left and Right Arrows");
    }
}
