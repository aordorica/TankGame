package MapFiles;

import GameFiles.GameConstants;
import GameFiles.Tank;

import java.awt.*;

public class Camera {

    private Map gameMap;
    private Tank tank;
    private int x;
    private int y;
    private int width;
    private int height;
    private float zoom;

    public Camera(Tank tank, Map map) {
        this.tank = tank;
        this.gameMap = map;
        this.zoom = 1.0f;
        this.x = 0;
        this.y = 0;
        this.width = GameConstants.GAME_SCREEN_WIDTH/ 2;
        this.height = GameConstants.GAME_SCREEN_HEIGHT;
    }

    public void render(Graphics2D g2d, Tank enemyTank) {
        // will need graphics.scale() to zoom camera and
        // will need graphics.translate to center the camera view on the tank

        g2d.scale(zoom, zoom);

        //System.out.println("Width: " + width + " height: " + height);
        if (tank.getX() <= width/4) {
            this.x = 0;
            System.out.println("X set to 0\n");
        } else this.x = tank.getX() * -1 + (width / 4);

//        if (tank.getX() >= width+(width/4)) {
//            this.x = width*-2;
//        }
        if (tank.getY() <= height/2) {
            this.y = 0;
        } else this.y = tank.getY() * -1 + height / 2;

        if (tank.getY() >= height- height/4) {
            this.y = (height- height/4);
            System.out.println("Tank y: " + tank.getY() + " Screen Y: " + this.y + " Screen height: " + height);
        }

        g2d.translate(x, y);
        gameMap.renderBackground(g2d);
        gameMap.render(g2d);
        tank.render(g2d);
        enemyTank.render(g2d);
        g2d.setColor(Color.cyan);
   }
}
