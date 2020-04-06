package com.escaperoom.ui.component;

import com.escaperoom.engine.cosmetics.Sprites;
import com.escaperoom.game.GameInfo;
import com.escaperoom.game.actors.Player;
import com.escaperoom.game.levels.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {
    private JFrame frame;
    private JPanel bottom;
    private ArrayList<JLabel> jLabels;
    private ArrayList<String> jLabelsSprites = new ArrayList<>();
    private int slot = 0;
    private int currentSlotOpen = 0;


    public Inventory(Rectangle mainScreen, Dimension screenSize, Level currentLevel) {
        frame = new JFrame(); // Created an new JFrame to be independent of the main game screen
        bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 5));
        jLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel("");
            label.setSize(mainScreen.width / 5, 100);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBackground(Color.BLACK);
            jLabels.add(label);
            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            bottom.add(label);
        }
        bottom.setBackground(Color.BLACK);
        frame.add(bottom);
        frame.setSize(screenSize.width - 400, 100);
        frame.setLocation(mainScreen.x, mainScreen.y + mainScreen.height);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.GRAY));
    }

    public void update(Player player,Sprites sprite) {

        BufferedImage picture = null;
        int index = player.getInventory().indexOf(sprite);
        if(!player.getInventory().isEmpty())
            if (player.getInventory().contains(sprite)) {
                try {
                    picture = ImageIO.read(new File(player.getInventory().get(index).texture.getLocation()));
                } catch (Exception e) {
                    System.out.println("Could not read image file path correctly");
                }
                Image image;
                image = picture.getScaledInstance(jLabels.get(index).getWidth(), jLabels.get(index).getHeight(), Image.SCALE_SMOOTH);
                jLabels.get(index).setIcon(new ImageIcon(image));


                jLabels.get(currentSlotOpen).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        System.out.println(player.getInventory().get(index).texture.getName());
                        jLabelsSprites.add(index,player.getInventory().get(index).texture.getName());
                    }
                });

            }
        }


    public ArrayList<JLabel> getjLabels(){
        return jLabels;
    }
    public void remove(Sprites sprite){
        if(jLabelsSprites.contains(sprite.texture.getName())){
            jLabels.get(jLabelsSprites.indexOf(sprite.texture.getName())).setIcon(null);
            jLabels.get(jLabelsSprites.indexOf(sprite.texture.getName())).revalidate();
        }
    }
}



