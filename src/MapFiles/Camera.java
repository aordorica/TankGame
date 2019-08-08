package MapFiles;

import GameFiles.GameConstants;
import GameFiles.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    private BufferedImage img;
    private Map map;
    private Tank tank;
    private int x;
    private int y;
    private int width;
    private int height;

    public Camera(Tank tank, Map map) {
        this.map = map;
        this.tank = tank;
        this.width = GameConstants.GAME_SCREEN_WIDTH/ 2;
        this.height = GameConstants.GAME_SCREEN_HEIGHT;
    }



   private void render(Graphics2D g2d) {
       tank.render(g2d);
       map.render(g2d);
   }
}
