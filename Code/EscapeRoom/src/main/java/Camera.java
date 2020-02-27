import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Camera implements KeyListener {
        double xPos, yPos, xDir, yDir, xPlane, yPlane;
        private boolean left, right, forward, back, interact;
        private final double MOVE_SPEED = .10;
        private double rotationSpeed = .085;
        boolean deleteRedkey = false;
        private KeyEvent lastKey;
        Camera(double x, double y, double xd, double yd, double xp, double yp)
        {
            xPos = x;
            yPos = y;
            xDir = xd;
            yDir = yd;
            xPlane = xp;
            yPlane = yp;
        }

        public void keyTyped(KeyEvent key) {

        }

        public void keyPressed(KeyEvent key) {

            if((key.getKeyCode() == KeyEvent.VK_LEFT))
                left = true;
            if((key.getKeyCode() == KeyEvent.VK_RIGHT))
                right = true;
            if((key.getKeyCode() == KeyEvent.VK_UP))
                forward = true;
            if((key.getKeyCode() == KeyEvent.VK_DOWN))
                back = true;
            if((key.getKeyCode() == KeyEvent.VK_E)) {
                interact = true;
               // System.out.println("Player Location: " + xPos + " ," + yPos);  // Use for debugging what is a good distance from a item that you can pick up
            }


        }

        public void keyReleased(KeyEvent key) {
            lastKey = key;
            if((key.getKeyCode() == KeyEvent.VK_LEFT))
                left = false;
            if((key.getKeyCode() == KeyEvent.VK_RIGHT))
                right = false;
            if((key.getKeyCode() == KeyEvent.VK_UP))
                forward = false;
            if((key.getKeyCode() == KeyEvent.VK_DOWN))
                back = false;
            if((key.getKeyCode() == KeyEvent.VK_E))
                interact = false;
        }
        public void update(int[][] map) {
            if(forward) {
                if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                    xPos+=xDir*MOVE_SPEED;
                }
                if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
                    yPos+=yDir*MOVE_SPEED;
            }
            if(back) {
                if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
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
        
        //Sets the rotation speed to a value chosen by the user in the pause menu
        public void setRotationSpeed(double rotationSpeed) {
        	this.rotationSpeed = rotationSpeed;
        }
    }
