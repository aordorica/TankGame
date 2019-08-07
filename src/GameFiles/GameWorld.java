package GameFiles;

import MapFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GameWorld extends JComponent implements Runnable{

    private int width = GameConstants.GAME_SCREEN_WIDTH;
    private int height = GameConstants.GAME_SCREEN_HEIGHT;

    private Thread thread;
    private Boolean running;
    private static JFrame frame;

    //GameWorld Objects
    private static GameWorld game;
    private Map map;
    private Tank tank1;
    private Tank tank2;
    private BufferedImage tank1Img;
    private BufferedImage tank2Img;
    private BufferedImage bulletImg;
    private BufferedImage explosionImg;

    public HashMap<String, BufferedImage> imageHashMap;

    public static void main(String[] argv){
        frame = new JFrame("Tank Game");
        game = new GameWorld();
        game.init();

        //screen.setFullScreenWindow(frame);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.setSize(new Dimension(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT));
        frame.getContentPane().add("Center", game);
        game.start();
    }

    private synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void init() {

        imageHashMap = new HashMap<>();
        this.setSize(this.width, this.height);
        this.loadImages();

        /* Load images from the hash map into their respective buffered images */

        tank1Img = game.imageHashMap.get("tank1");
        tank2Img = game.imageHashMap.get("tank2");
        bulletImg = game.imageHashMap.get("Missile");
        explosionImg = game.imageHashMap.get("Explosion");

        /* Class instances to create Map, Background and tank objects. Each is passed the game Images and game screen size*/
        map = new Map("mapLayout.txt", game);
        tank1 = new Tank(200, 200, 0, 0, 0, tank1Img, bulletImg, explosionImg);
        tank2 = new Tank(1000, 200, 0, 0, 180, tank2Img, bulletImg, explosionImg);


        TankControl tank1Control = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        TankControl tank2Control = new TankControl(tank2, KeyEvent.VK_E, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_F, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE);

        addKeyListener(tank1Control);
        addKeyListener(tank2Control);
    }

    private void loadImages() {
        imageHashMap.put("tank1", ImageLoader.loadImages("tank1.png"));
        imageHashMap.put("tank2", ImageLoader.loadImages("tank2.png"));
        imageHashMap.put("Background", ImageLoader.loadImages("Spacebg.jpg"));
        imageHashMap.put("Missile", ImageLoader.loadImages("bullet.png"));
        imageHashMap.put("BreakableWall", ImageLoader.loadImages("Wall1.gif"));
        imageHashMap.put("UnbreakableWall", ImageLoader.loadImages("Wall2.gif"));
        imageHashMap.put("PowerUp", ImageLoader.loadImages("powerup.png"));
        imageHashMap.put("Explosion", ImageLoader.loadImages("explosion1.png"));
    }

    @Override
    public void run() {
        while(running){
            update();
            repaint();
            requestFocus();
            try {
                Thread.sleep(1000/144);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        this.map.render(g2);
        this.tank1.render(g2);
        this.tank2.render(g2);
        g2.dispose();
    }

    private void checkCollidingWalls(Tank tank){
        ArrayList<BreakableWall> breakableWalls = map.getBreakableWalls();
        ArrayList<UnbreakableWall> unBreakableWalls = map.getUnbreakableWalls();
        ArrayList<PowerUp> powerUps = map.getPowerUps();
        ArrayList<Bullet> bullets = tank.getBullets();

        for (BreakableWall tempWall: breakableWalls) {
            tank.checkCollision(tempWall);
        }
        for (UnbreakableWall tempWall: unBreakableWalls) {
            tank.checkCollision(tempWall);
        }
        for (PowerUp powerUp: powerUps) {
            tank.checkCollision(powerUp);
        }
        for (Bullet bullet: bullets) {
            bullet.checkCollision(tank);
        }
    }

    private void update() {

        tank1.update();
        tank2.update();

        tank1.checkCollision(tank2);
        tank2.checkCollision(tank1);
        checkCollidingWalls(tank1);
        checkCollidingWalls(tank2);
    }
}
