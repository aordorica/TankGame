package MapFiles;

import GameFiles.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    private BufferedImage img;
    private Map map;
    private Tank tank;

    public Camera(Tank tank, Map map) {
        this.img = map.img;
        this.map = map;
        this.tank = tank;
    }

   private void render(Graphics2D g2d) {
        this.tank.render(g2d);
        this.map.render(g2d);
   }
}
