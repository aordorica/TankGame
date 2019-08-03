package MapFiles;

import GameFiles.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundLandscape implements GameObject {

    private int width;
    private int height;
    private BufferedImage img;

    public BackgroundLandscape(int width, int height, BufferedImage img) {
        this.width = width;
        this.height = height;
        this.img = img;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(img,0, 0, width, height, null);
    }
}
