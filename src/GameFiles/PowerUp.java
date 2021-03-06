package GameFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp implements GameObject, Collidable {
    private BufferedImage image;
    private int width;
    private int height;
    private int locateX;
    private int locateY;
    private Rectangle hitBox;

    public PowerUp(BufferedImage image, int locateX, int locateY) {
        this.image = image;
        this.locateX = locateX;
        this.locateY = locateY;
    }

    @Override
    public int getWidth() {
        return this.image.getWidth();
    }

    @Override
    public int getHeight() {
        return this.image.getHeight();
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.showHitbox) {
            g2d.setColor(Color.GREEN);
            g2d.draw(this.hitBox);
        }
        g2d.drawImage(image, locateX, locateY, null);
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public Boolean checkHealth() {
        return true;
    }

    @Override
    public void takeHit() {
        //Do nothing
    }

    @Override
    public Rectangle getHitBox() {
        hitBox = new Rectangle(this.locateX, this.locateY, this.getWidth(), this.getHeight());
        return this.hitBox;
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        Tank tank = (Tank) enemy;
        return null;
    }

    @Override
    public void collision(Collidable enemy) {

    }
}
