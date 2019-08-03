package GameFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp implements GameObject {
    private BufferedImage image;
    private int width;
    private int height;
    private int locateX;
    private int locateY;

    public PowerUp(BufferedImage image, int locateX, int locateY) {
        this.image = image;
        this.locateX = locateX;
        this.locateY = locateY;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, locateX, locateY, null);
    }
}
