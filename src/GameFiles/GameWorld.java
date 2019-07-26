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
    private BufferedImage temp = null;
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

        //assign tank image into Tank variable temp
        temp = ImageLoader.loadImages("tank1.png");

        tank1 = new Tank(200, 200, 0, 0, 0, temp);
        TankControl tc1 = new TankControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);

        this.addKeyListener(tc1);
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
        graphics.drawImage(gameScreen, 0, 0, null);
    }

    private void update(){
        tank1.update();
        tank2.update();
    }
}
