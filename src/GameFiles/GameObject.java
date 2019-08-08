package GameFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GameObject {
    BufferedImage img = null;
    Boolean showHitbox = true;

    int getWidth();

    int getHeight();

    void render(Graphics2D g2d);
}
