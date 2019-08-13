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

    public void update() {

    }

    public void render(Graphics2D g2d, Tank enemyTank) {
        // will need graphics.scale() to zoom camera and
        // will need graphics.translate to center the camera view on the tank
        g2d.scale(zoom, zoom);

        if (tank.getX() <= width/4) {
            this.x = 0;
            System.out.println("Tank coordinates: x = " + tank.getX() + " , Y = " + tank.getY());
            System.out.println("Width coordinates: width/4 = " + width/4 + " , height = " + height);
        } else this.x = tank.getX() * -1 + (width / 4);

        if (tank.getY() <= height/2) {
            this.y = 0;
            System.out.println("1Tank coordinates: x = " + tank.getX() + " , Y = " + tank.getY() );
        } else {
            this.y = tank.getY() * -1 + height / 2;
            System.out.println("2Tank coordinates: x = " + tank.getX() + " , Y = " + tank.getY());
        }
        //g2d.translate(tank.getX() * -1 + (width / 4), tank.getY() * -1 + height / 2);
        g2d.translate(x, y);
        gameMap.renderBackground(g2d);
        gameMap.render(g2d);
        tank.render(g2d);
        enemyTank.render(g2d);
        g2d.setColor(Color.cyan);
        g2d.drawLine(this.width, 0,this.width, this.height);
   }
}
