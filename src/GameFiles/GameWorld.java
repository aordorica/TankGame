package GameFiles;

import MapFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private BackgroundLandscape background;
    private BufferedImage bg;
    private BufferedImage tank1Img;
    private BufferedImage tank2Img;

    public HashMap<String, BufferedImage> imageHashMap;

    public static void main(String[] argv){
        frame = new JFrame("Tank Game");
        game = new GameWorld();
        game.init();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        //screen.setFullScreenWindow(frame);
        frame.setVisible(true);

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

        bg = imageHashMap.get("Background");
        tank1Img = game.imageHashMap.get("tank1");
        tank2Img = game.imageHashMap.get("tank2");

        //Class instance that loads the map instances into the map
        map = new Map("mapLayout.txt", game);

        tank1 = new Tank(200, 200, 0, 0, 0, tank1Img);
        tank2 = new Tank(400, 200, 0, 0, 0, tank2Img);

        background = new BackgroundLandscape(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, bg);

        TankControl tank1Control = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        TankControl tank2Control = new TankControl(tank2, KeyEvent.VK_E, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_F, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);

        addKeyListener(tank1Control);
        addKeyListener(tank2Control);
    }

    public void loadImages() {
        imageHashMap.put("tank1", ImageLoader.loadImages("tank1.png"));
        imageHashMap.put("tank2", ImageLoader.loadImages("tank2.png"));
        imageHashMap.put("Background", ImageLoader.loadImages("Spacebg.jpg"));
        imageHashMap.put("Missile", ImageLoader.loadImages("tank1.png"));
        imageHashMap.put("BreakableWall", ImageLoader.loadImages("Wall1.gif"));
        imageHashMap.put("UnbreakableWall", ImageLoader.loadImages("Wall2.gif"));
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

    public static GameWorld getGame(){
        return game;
    }

    public void paint(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;

        this.background.render(g2);
        this.map.render(g2);
        this.tank1.render(g2);
        this.tank2.render(g2);
        g2.dispose();
    }

    private void update(){
        tank1.update();
        tank2.update();
    }
}
