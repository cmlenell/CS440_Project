import java.util.ArrayList;
import java.awt.Color;
import java.util.Vector;

public class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Textures> textures;

    public Screen(int[][] m, int mapW, int mapH, ArrayList<Textures> tex, int w, int h) {
        map = m;
        mapWidth = mapW;
        mapHeight = mapH;
        textures = tex;
        width = w;
        height = h;
    }

    public int[] update(Camera camera, int[] pixels) {

        // Fill in ceiling
        for(int n=0; n<pixels.length/2; n++) {
            if(pixels[n] != Color.GREEN.getRGB()) pixels[n] = Color.GREEN.getRGB();
        }

        double posX = 22.0, posY = 11.5;  //x and y start position
        double dirX = -1.0, dirY = 0.0; //initial direction vector
        double planeX = 0.0, planeY = 0.66; //the 2d raycaster version of camera plane
        //FLOOR CASTING

        // Make some random textures for the floor and ceiling
        Vector<Integer> floor_texture = new Vector<>();
        Vector<Integer> ceiling_texture = new Vector<>();

        for(int x = 0; x < 64; x++)
            for(int y = 0; y < 64; y++) {
                int xorcolor = (x * 256 / 64) ^ (y * 256 / 64);

                floor_texture.add(xorcolor + 256 * xorcolor + 65536 * xorcolor);
                ceiling_texture.add(256 * xorcolor);
            }

        for(int y = 0; y < height; y++)
        {
            // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
            double rayDirX0 = dirX - planeX;
            double rayDirY0 = dirY - planeY;
            double rayDirX1 = dirX + planeX;
            double rayDirY1 = dirY + planeY;

            // Current y position compared to the center of the screen (the horizon)
            int p = y - height / 2;

            // Vertical position of the camera.
            double posZ = 0.5 * height;

            // Horizontal distance from the camera to the floor for the current row.
            // 0.5 is the z position exactly in the middle between floor and ceiling.
            double rowDistance = posZ / p;

            // calculate the real world step vector we have to add for each x (parallel to camera plane)
            // adding step by step avoids multiplications with a weight in the inner loop
            double floorStepX = rowDistance * (rayDirX1 - rayDirX0) / width;
            double floorStepY = rowDistance * (rayDirY1 - rayDirY0) / width;

            // real world coordinates of the leftmost column. This will be updated as we step to the right.
            double floorX = posX + rowDistance * rayDirX0;
            double floorY = posY + rowDistance * rayDirY0;

            for(int x = 0; x < width; ++x)
            {
                // the cell coord is simply got from the integer parts of floorX and floorY
                int cellX = (int)(floorX);
                int cellY = (int)(floorY);

                // get the texture coordinate from the fractional part
                int tx = (int)(64 * (floorX - cellX)) & (64 - 1);
                int ty = (int)(64 * (floorY - cellY)) & (64 - 1);

                floorX += floorStepX;
                floorY += floorStepY;


                int color;

                // floor
                //color = floor_texture.get(64 * ty + tx);
                color = textures.get(1).pixels[64*ty+tx];
                color = (color >> 1) & 8355711; // make a bit darker
                if( (y*(width) + x) > (pixels.length/2))
                    pixels[y*(width) + x] = color;

                //ceiling (symmetrical, at screenHeight - y - 1 instead of y)
                /*color = ceiling_texture.get(64 * ty + tx);
                color = textures.get(2).pixels[64*ty+tx];
                color = (color >> 1) & 8355711; // make a bit darker
                if( ((height - y - 1) * x) < (pixels.length/2))
                    pixels[(height - y - 1) * x] = color;*/
            }
        }

        for(int x=0; x<width; x=x+1) {
            double cameraX = 2 * x / (double)(width) -1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            //Map position
            int mapX = (int)camera.xPos;
            int mapY = (int)camera.yPos;
            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;
            //Length of ray from one side to next in map
            double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
            double perpWallDist;
            //Direction to go in x and y
            int stepX, stepY;
            boolean hit = false;//was a wall hit
            int side=0;//was the wall vertical or horizontal
            //Figure out the step direction and initial distance to a side
            if (rayDirX < 0)
            {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            }
            else
            {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0)
            {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            }
            else
            {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }
            //Loop to find where the ray hits a wall
            while(!hit) {
                //Jump to next square
                if (sideDistX < sideDistY)
                {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }
                else
                {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                //System.out.println(mapX + ", " + mapY + ", " + map[mapX][mapY]);
                if(map[mapX][mapY] > 0) hit = true;
            }

            //Calculate distance to the point of impact
            int texNum = map[mapX][mapY] - 1;
            if(side==0)
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
            //Now calculate the height of the wall based on the distance from the camera
            int lineHeight;
            if(perpWallDist > 0) {
                if(texNum == 0)
                    lineHeight = Math.abs((int)(height / perpWallDist));// Change this variable to change the height of walls
                else
                    lineHeight = Math.abs((int)(height / perpWallDist));// Change this variable to change the height of walls
            }
            else lineHeight = height;
            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight/2+ height/2;
            if(drawStart < 0)
                drawStart = 0;
            int drawEnd = lineHeight/2 + height/2;
            if(drawEnd >= height)
                drawEnd = height - 1;
            //add a texture
            // int texNum = map[mapX][mapY] - 1;
            double wallX;//Exact position of where wall was hit
            if(side==1) {//If its a y-axis wall
                wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {//X-axis wall
                wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            wallX-=Math.floor(wallX);
            //x coordinate on the texture

            int texX = (int)(wallX * (textures.get(texNum).SIZE));
            if(side == 0 && rayDirX > 0) texX = textures.get(texNum).SIZE - texX - 1;
            if(side == 1 && rayDirY < 0){ texX = textures.get(texNum).SIZE - texX - 1;}

            //calculate y coordinate on texture
            for(int y=drawStart; y<drawEnd; y++) {
                int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if(side==0) color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)]; // Change texNum to change sides texture
                else color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)]>>1) & 8355711;//Change texNum to change front and back texture
                pixels[x + y*(width)] = color;
            }
        }
        return pixels;
    }
}
