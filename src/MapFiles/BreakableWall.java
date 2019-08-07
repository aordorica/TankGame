package MapFiles;

import GameFiles.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BreakableWall extends Wall implements Collidable {
    private BufferedImage image;
    private int locateX;
    private int locateY;
    private int health = 10;
    private int spawnTime = 100;
    private boolean visible;
    private Rectangle hitBox;

    public BreakableWall(BufferedImage img, int x, int y) {
        this.visible = true;
        this.isbreakable = true;
        this.locateX = x;
        this.locateY = y;
        this.image = img;
        this.health = 1;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    private void setLocation(int x, int y){
        this.locateX = x;
        this.locateY = y;
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public Boolean checkHealth() {
        if (this.health > 0) {
            this.visible = true;
            return true;
        } else
            this.visible = false;
            return false;
    }

    @Override
    public void takeHit() {
        // Code to decrease health when hit
    }

    @Override
    public Rectangle getHitBox() {
        hitBox = new Rectangle(this.locateX, this.locateY, this.getWidth(), this.getHeight());
        return this.hitBox;
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        if (this.hitBox != null && enemy.getHitBox() != null) {
            if (this.hitBox.intersects(enemy.getHitBox())){
                this.collision(enemy);
            }
        } return false;
    }

    @Override
    public void collision(Collidable enemy) {
        System.out.println("Collided with Breakable Wall");
    }

    public void render(Graphics2D g2d, ImageObserver observer) {
        checkHealth();
        if (visible) {
            g2d.drawImage(image, locateX, locateY, observer);
            g2d.draw(this.hitBox);
        }
    }
}
