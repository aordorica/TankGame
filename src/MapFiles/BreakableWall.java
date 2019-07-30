package MapFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    BufferedImage image;
    private int width;
    private int height;
    private int locateX = 0;
    private int locateY = 0;

    public BreakableWall(BufferedImage img, int width, int height) {
        this.isbreakable = true;
        this.width = width;
        this.height = height;
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

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, locateX, locateY, width, height, null);
    }
}
