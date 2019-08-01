package MapFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall{
        private BufferedImage image;
        private int width;
        private int height;
        private int locateX = 0;
        private int locateY = 0;

    public UnbreakableWall(BufferedImage img, int x, int y) {
        this.isbreakable = false;
        this.locateX = x;
        this.locateY = y;
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

    public void render(Graphics2D g2d) {
        //System.out.println("UnBreakable Wall Block: \nLocateX: " + locateX + " LocateY: " +locateY + "\n");
        g2d.drawImage(image, locateX, locateY, null);
    }
}
