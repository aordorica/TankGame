package MapFiles;

import GameFiles.Bullet;
import GameFiles.Collidable;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall implements Collidable{
        private BufferedImage image;
        private int locateX;
        private int locateY;
        private Rectangle hitBox;

    public UnbreakableWall(BufferedImage img, int x, int y) {
        this.isbreakable = false;
        this.locateX = x;
        this.locateY = y;
        this.image = img;
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
        return null;
    }

    @Override
    public void takeHit() {

    }

    @Override
    public Rectangle getHitBox() {
        hitBox = new Rectangle(locateX, locateY, image.getWidth(), image.getHeight());
        return this.hitBox;
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        if (this.hitBox != null && enemy.getHitBox() != null) {
            if (this.hitBox.intersects(enemy.getHitBox())){
                this.collision(enemy);
            }
        }
        return false;
    }

    @Override
    public void collision(Collidable enemy) {
        System.out.println("Collided with UnBreakable Wall");
    }

    public void bounceBack(Collidable enemy, Rectangle intersection) {
        // Hit from the LEFT
        if (this.locateX < intersection.x) {
            if (this.getWidth() < intersection.width) {
                this.locateX = this.locateX - intersection.width;
            }
        }
        // Hit from the RIGHT
        if (this.locateX < intersection.y) {
            if (this.getWidth() < intersection.width) {
                this.locateX = this.locateX + intersection.width;
            }
        }

        // Hit from the TOP
        if (this.locateY < intersection.y) {
            if (this.getWidth() > intersection.width) {
                this.locateY = this.locateY - intersection.height;
            }
        }

        // Hit from BOTTOM
        if (intersection.height > intersection.width) {
            if (this.locateY > intersection.y) {
                this.locateY = this.locateY + intersection.height;
            }
        }
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(image, locateX, locateY, null);
        g2d.draw(this.hitBox);
    }
}
