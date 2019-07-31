package GameFiles;

import MapFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GameWorld extends JComponent implements Runnable{
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private static GameWorld game;

    private int width = SCREEN_WIDTH;
    private int height = SCREEN_HEIGHT;

    private Thread thread;
    private Boolean running;
    private static JFrame frame;

    //GameWorld Objects
    private MapLoader gameMap;
    private Tank tank1;
    private Tank tank2;
    private BackgroundLandscape background;
    private BreakableWall breakWall;
    private UnbreakableWall normalWall;
    private BufferedImage bg;
    public HashMap<String, BufferedImage> imageHashMap;

    public static void main(String[] argv){
        frame = new JFrame("Tank Game");

        game = new GameWorld();
        game.init();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        screen.setFullScreenWindow(frame);

        frame.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
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

        BufferedImage tank1Img = imageHashMap.get("tank1");
        BufferedImage tank2Img = imageHashMap.get("tank2");
        bg = imageHashMap.get("Background");

        gameMap = new MapLoader("mapLayout.txt");

        tank1 = new Tank(200, 200, 0, 0, 0, tank1Img);
        tank2 = new Tank(400, 200, 0, 0, 0, tank2Img);
        background = new BackgroundLandscape(SCREEN_WIDTH, SCREEN_HEIGHT, bg);

        TankControl tank1Control = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        TankControl tank2Control = new TankControl(tank2, KeyEvent.VK_E, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_F, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);

        addKeyListener(tank1Control);
        addKeyListener(tank2Control);
    }

    public void loadImages() {
        imageHashMap.put("tank1", ImageLoader.loadImages("tank1.png"));
        imageHashMap.put("tank2", ImageLoader.loadImages("plant.png"));
        imageHashMap.put("Background", ImageLoader.loadImages("Background.bmp"));
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
        this.tank1.render(g2);
        this.tank2.render(g2);
        g2.dispose();
    }

    private void update(){
        tank1.update();
        tank2.update();
    }

    private void loadBreakableWall(BreakableWall breakWall){
        this.breakWall = breakWall;
    }

    private void loadUnBreakableWall(UnbreakableWall UnbreakWall){
        this.normalWall = UnbreakWall;
    }
}
