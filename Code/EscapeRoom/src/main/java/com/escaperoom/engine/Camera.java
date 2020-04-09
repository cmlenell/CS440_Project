package com.escaperoom.engine;

import com.escaperoom.engine.cosmetics.Sprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Camera implements KeyListener {
        public double xPos;
		public double yPos;
		double xDir;
		double yDir;
		double xPlane;
		double yPlane;
        private boolean left, right, forward, back;
        private final double MOVE_SPEED = .10;
        private double rotationSpeed = .085;
        boolean deleteRedkey = false;
        private KeyEvent lastKey;
        public ArrayList<Sprites> sprites;
        public Camera(double x, double y, double xd, double yd, double xp, double yp, ArrayList<Sprites> _sprites)
        {
            xPos = x;
            yPos = y;
            xDir = xd;
            yDir = yd;
            xPlane = xp;
            yPlane = yp;
            sprites = _sprites;
        }

        public void keyTyped(KeyEvent key) {

        }

        public void keyPressed(KeyEvent key) {
            int pressed = key.getKeyCode();
        /*
            if((key.getKeyCode() == KeyEvent.VK_LEFT))
                left = true;
            if((key.getKeyCode() == KeyEvent.VK_RIGHT))
                right = true;
            if((key.getKeyCode() == KeyEvent.VK_UP))
                forward = true;
            if((key.getKeyCode() == KeyEvent.VK_DOWN))
                back = true;
            if((key.getKeyCode() == KeyEvent.VK_E)) {
                System.out.println("Player Location: " + xPos + " ," + yPos);
            }
*/
            if((pressed == KeyEvent.VK_A))
                left = true;
            if((pressed == KeyEvent.VK_D))
                right = true;
            if((pressed == KeyEvent.VK_W))
                forward = true;
            if((pressed == KeyEvent.VK_S))
                back = true;
            if((pressed == KeyEvent.VK_E)) {
                System.out.println("Player Location: " + xPos + " ," + yPos);
            }

        }

        public void keyReleased(KeyEvent key) {
            lastKey = key;
            /*
            if((key.getKeyCode() == KeyEvent.VK_LEFT))
                left = false;
            if((key.getKeyCode() == KeyEvent.VK_RIGHT))
                right = false;
            if((key.getKeyCode() == KeyEvent.VK_UP))
                forward = false;
            if((key.getKeyCode() == KeyEvent.VK_DOWN))
                back = false;
            if((key.getKeyCode() == KeyEvent.VK_E)) {
			}*/

            int pressed = key.getKeyCode();

            if((pressed == KeyEvent.VK_A))
                left = false;
            if((pressed == KeyEvent.VK_D))
                right = false;
            if((pressed == KeyEvent.VK_W))
                forward = false;
            if((pressed == KeyEvent.VK_S))
                back = false;
            if((pressed == KeyEvent.VK_E)) {
                //System.out.println("Player Location: " + xPos + " ," + yPos);
            }
        }
        public void update(int[][] map) {
            if(forward) {
                /*
                boolean near_solid_sprite = false;

                for(int i = 0; i < sprites.size(); i++){
                    if( sprites.get(i).isSolid &&
                        (yPos > (sprites.get(i).y-0.2) && yPos < (sprites.get(i).y+0.2)) &&
                        (xPos > (sprites.get(i).x-0.2) && xPos < (sprites.get(i).x+0.2)) )
                    near_solid_sprite = true;}
*/
                if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) { // && !near_solid_sprite) {
                    xPos+=xDir*MOVE_SPEED;
                }
                if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0)  // && !near_solid_sprite)
                    yPos+=yDir*MOVE_SPEED;
            }

            if(back) {
                if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0 )
                    xPos-=xDir*MOVE_SPEED;
                if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
                    yPos-=yDir*MOVE_SPEED;
            }

            if(right) {
                double oldxDir=xDir;
                xDir=xDir*Math.cos(-rotationSpeed) - yDir*Math.sin(-rotationSpeed);
                yDir=oldxDir*Math.sin(-rotationSpeed) + yDir*Math.cos(-rotationSpeed);
                double oldxPlane = xPlane;
                xPlane=xPlane*Math.cos(-rotationSpeed) - yPlane*Math.sin(-rotationSpeed);
                yPlane=oldxPlane*Math.sin(-rotationSpeed) + yPlane*Math.cos(-rotationSpeed);
            }
            if(left) {
                double oldxDir=xDir;
                xDir=xDir*Math.cos(rotationSpeed) - yDir*Math.sin(rotationSpeed);
                yDir=oldxDir*Math.sin(rotationSpeed) + yDir*Math.cos(rotationSpeed);
                double oldxPlane = xPlane;
                xPlane=xPlane*Math.cos(rotationSpeed) - yPlane*Math.sin(rotationSpeed);
                yPlane=oldxPlane*Math.sin(rotationSpeed) + yPlane*Math.cos(rotationSpeed);
            }

        }
        public KeyEvent getLastKey(){
            return lastKey;
        }
        
        public void setLastKeyPressed(KeyEvent key) {
			lastKey = key;	
		}
        
        //Sets the rotation speed to a value chosen by the user in the pause menu
        public void setRotationSpeed(double rotationSpeed) {
        	this.rotationSpeed = rotationSpeed;
        }
    }
