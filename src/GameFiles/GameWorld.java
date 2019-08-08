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
    private Camera cam1;
    private Camera cam2;
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
        tank1 = new Tank(200, 200, 0, 0, 0, tank1Img, bulletImg, explosionImg, frame);
        tank2 = new Tank(1000, 200, 0, 0, 180, tank2Img, bulletImg, explosionImg, frame);

        cam1 = new Camera(tank1, map);
        cam2 = new Camera(tank2, map);

        TankControl tank1Control = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        TankControl tank2Control = new TankControl(tank2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE);

        addKeyListener(tank1Control);
        addKeyListener(tank2Control);
    }

    private void loadImages() {
        imageHashMap.put("tank1", ImageLoader.loadImages("StarFighter1.png"));
        imageHashMap.put("tank2", ImageLoader.loadImages("StarFighter2.png"));
        imageHashMap.put("Background", ImageLoader.loadImages("Spacebg.jpg"));
        imageHashMap.put("Missile", ImageLoader.loadImages("bullet.png"));
        imageHashMap.put("BreakableWall", ImageLoader.loadImages("Wall1.gif"));
        imageHashMap.put("UnbreakableWall", ImageLoader.loadImages("Wall2.gif"));
        imageHashMap.put("PowerUp", ImageLoader.loadImages("powerup.png"));
        imageHashMap.put("Explosion", ImageLoader.loadImages("explosion2.gif"));
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

    private void update() {

        tank1.update();
        tank2.update();

        //checkCollisions(tank1, tank2);
        tank1.checkCollision(tank2);
        tank2.checkCollision(tank1);

        map.checkCollision(tank1);
        map.checkCollision(tank2);
    }
}
