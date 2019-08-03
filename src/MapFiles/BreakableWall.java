package MapFiles;

import GameFiles.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall implements Collidable {
    private BufferedImage image;
    private int width;
    private int height;
    private int locateX = 0;
    private int locateY = 0;
    private int health = 10;

    public BreakableWall(BufferedImage img, int x, int y) {
        this.isbreakable = true;
        locateX = x;
        locateY = y;
        this.image = img;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private void setLocation(int x, int y){
        this.locateX = x;
        this.locateY = y;
    }


    public void render(Graphics2D g2d) {
        //System.out.println("Breakable Wall Block: \nLocateX: " + locateX + "LocateY: " +locateY);
        g2d.drawImage(image, locateX, locateY, null);
    }
}
