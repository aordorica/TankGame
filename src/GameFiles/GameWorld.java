package GameFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameWorld extends JComponent implements Runnable{
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;


    private Tank tank1;
    private Tank tank2;
    private Graphics g;
    private BufferedImage buffer;
    private Thread thread;
    private BufferedImage tank1Img = null;
    private BufferedImage tank2Img = null;
    private BufferedImage gameScreen = null;

    public static void main(String[] argv){

        JFrame frame = new JFrame("Tank Game");
        GameWorld game = new GameWorld();
        game.init();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        screen.setFullScreenWindow(frame);

        SCREEN_HEIGHT = screen.getFullScreenWindow().getSize().height;
        SCREEN_WIDTH = screen.getFullScreenWindow().getSize().width;

        frame.setSize(new Dimension(screen.getFullScreenWindow().getSize()));
        frame.getContentPane().add("Center", game);
        game.start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    private void init() {

        //assign tank image into Tank variable tank1Img
        tank1Img = ImageLoader.loadImages("tank1.png");
        tank2Img = ImageLoader.loadImages("plant.png");

        tank1 = new Tank(200, 200, 0, 0, 0, tank1Img);
        tank2 = new Tank(200, 200, 0, 0, 0, tank2Img);
        TankControl tank1Control = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        TankControl tank2Control = new TankControl(tank2, KeyEvent.VK_E, KeyEvent.VK_S, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);

        this.addKeyListener(tank1Control);
        this.addKeyListener(tank2Control);
    }


    @Override
    public void run() {
        while(true){
            update();
            repaint();
            requestFocus();
            try {
                thread.sleep(1000/144);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void paint(Graphics graphics){
        gameScreen = (BufferedImage) createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        tank1.drawImage(gameScreen.createGraphics());
        tank2.drawImage(gameScreen.createGraphics());
        graphics.drawImage(gameScreen, 0, 0, null);
    }

    private void update(){
        tank1.update();
//        tank2.update();
    }
}
