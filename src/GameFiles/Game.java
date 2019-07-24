package GameFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;


public class Game implements Runnable {


    public final String title;
    private int width, height;
    private boolean running = false;
    private Thread thread;
    private Display display;
    private Tank tank1;
    private Graphics graphic;
    private Graphics2D buffer;
    BufferedImage temp = null;


    private BufferStrategy bufferStrategy;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init(String t, int w, int h) {
        display = new Display(title, w, h);
        temp = ImageLoader.loadImages("plant.png");

        tank1 = new Tank(200, 200, 0, 0, 0, temp);

    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this); // Sets THIS game object as the thread
        thread.start(); // Starts thread for game object
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

    @Override
    public void run() {
        init(this.title, width, height);

        //Game loop to update graphics and paint to canvas
        while(running){
            render();
            //update();
        }

        stop();
    }

    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        graphic = bufferStrategy.getDrawGraphics();
        graphic.clearRect(0,0,width,height);
        graphic.drawImage(temp,50, 50, Color.BLUE, null);

        bufferStrategy.show();
        graphic.dispose();

    }

    private void update() {

    }

}
