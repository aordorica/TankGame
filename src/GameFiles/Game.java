package GameFiles;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import static javax.imageio.ImageIO.read;


public class Game implements Runnable {


    public final String title;
    private int width, height;
    private boolean running = false;
    private Thread gameThread;
    private Display display;
    private Tank tank1;
    private Graphics graphic;
    private Graphics2D buffer;
    private BufferedImage temp = null;
    private SpriteSheet sheet;


    private BufferStrategy bufferStrategy;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init(String t, int w, int h) {
        display = new Display(title, w, h);
        temp = ImageLoader.loadImages("/resources/Test.png");
        sheet = new SpriteSheet(temp);

        tank1 = new Tank(200, 200, 0, 0, 0, temp);

    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        gameThread = new Thread(this); // Sets THIS game object as the gameThread
        gameThread.start(); // Starts gameThread for game object
    }


    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init(this.title, width, height);

        //Game loop to update graphics and paint to canvas
        while(running){
            render();
            System.out.println("Calling render()");
            //update();
        }

        stop();
    }

    private void render() {
//        bufferStrategy = display.getCanvas().getBufferStrategy();
//        graphic = bufferStrategy.getDrawGraphics();
//        graphic.clearRect(0,0,width,height);
//        graphic.drawImage(temp,50, 50, Color.BLUE, null);
//
//        bufferStrategy.show();
//        graphic.dispose();

        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphic = bufferStrategy.getDrawGraphics();
        //Clear Screen
        graphic.clearRect(0, 0, width, height);
        //Draw Here!

        graphic.drawImage(sheet.crop(32, 0, 32, 32), 5, 5, null);

        //End Drawing!
        bufferStrategy.show();
        graphic.dispose();
    }

    private void update() {

    }

}
