package GameFiles;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet implements GameObject {
    public int x;
    public int y;
    private int vx;
    private int vy;
    private float angle;
    private BufferedImage img;
    public boolean collided;
    private AffineTransform rotateBullet;

    public Bullet(int x, int y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void update(){
        this.x++;
        this.y++;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void render(Graphics2D g2d) {
        this.update();
        System.out.println("Bullet:\n" + this);
        g2d.drawImage(img, this.x, this.y, null);
    }
}
