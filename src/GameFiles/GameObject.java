package GameFiles;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface GameObject {
    int y = 0;
    int x = 0;
    int health = 0;
    BufferedImage img = null;
    Boolean canBeShot = null;

    public int getWidth();

    public int getHeight();

    public void render(Graphics g);
}
