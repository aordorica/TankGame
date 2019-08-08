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
    private Rectangle hitBox;

    public BreakableWall(BufferedImage img, int x, int y) {
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

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public Boolean checkHealth() {
        if (this.health > 0) {
            return true;
        } else
            return false;
    }

    @Override
    public void takeHit() {
        health--;
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

    public void render(Graphics2D g2d) {
        checkHealth();
        g2d.drawImage(image, locateX, locateY, null);

        // Show hitbox
        if (this.showHitbox) {
            g2d.draw(this.hitBox);
        }
    }
}
