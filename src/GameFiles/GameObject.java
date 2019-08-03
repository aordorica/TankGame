package GameFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GameObject {
    int y = 0;
    int x = 0;
    int health = 0;
    BufferedImage img = null;
    Boolean canBeShot = null;

    int getWidth();

    int getHeight();

    void render(Graphics2D g2d);
}
