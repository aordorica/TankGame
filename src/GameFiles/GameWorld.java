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
    private BufferedImage screenImage;
    private BufferedImage miniMap;
    private BufferedImage bg;

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
        bg = imageHashMap.get("Background");

        /* Class instances to create Map, Background and tank objects. Each is passed the game Images and game screen size*/
        map = new Map("map1.txt", game);
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
        imageHashMap.put("Background", ImageLoader.loadImages("StarWarsBG.png"));
        imageHashMap.put("Missile", ImageLoader.loadImages("bullet.png"));
        imageHashMap.put("BreakableWall", ImageLoader.loadImages("rock.png"));
        imageHashMap.put("UnbreakableWall", ImageLoader.loadImages("asteroid.png"));
        imageHashMap.put("PowerUp", ImageLoader.loadImages("powerup.png"));
        imageHashMap.put("Explosion", ImageLoader.loadImages("explosion2.gif"));
        imageHashMap.put("GameOver", ImageLoader.loadImages("gameOver.png"));
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

        float minWidth = map.getWidth() * 32;
        float minHeight = map.getHeight() * 32;

        screenImage = (BufferedImage) createImage(map.getWidth() * 32, map.getHeight() * 32);
        miniMap = screenImage.getSubimage(width /2 - 180, height /2, width / 2, height/2);

        Graphics2D side1 = screenImage.getSubimage(0, 0, width /2, height).createGraphics();
        Graphics2D side2 = screenImage.getSubimage(width / 2, 0, width/2, height).createGraphics();


        //this.map.render(g2);
        this.cam1.render(side1, tank2);
        this.cam2.render(side2, tank1);

        //Create Mini Map
        Graphics2D minMap = miniMap.createGraphics();

        minMap.scale(400/minWidth, 400/minHeight);
        minMap.drawImage(bg, 0,0,map.getWidth() * 32, map.getHeight() * 32, null);
        map.render(minMap);
        tank1.render(minMap);
        tank2.render(minMap);
        minMap.setColor(Color.cyan);
        minMap.drawRect(0,0,map.getWidth() * 32,map.getHeight() * 32);
        g2.drawImage(screenImage, 0,0,null);

    }

    private void gameOver() {
        Graphics2D endGraphics = (Graphics2D) getGraphics();
        endGraphics.drawImage(imageHashMap.get("GameOver"), 0, 0, null);
    }

    private void update() {

        if (tank1.getHealth() == 0 || tank2.getHealth() == 0) {
            this.stop();
            gameOver();
        }
        tank1.update();
        tank2.update();
        cam1.update();
        cam2.update();

        //checkCollisions(tank1, tank2);
        tank1.checkCollision(tank2);
        tank2.checkCollision(tank1);

        map.checkCollision(tank1);
        map.checkCollision(tank2);
    }
}
