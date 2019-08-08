package GameFiles;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet implements GameObject, Collidable {

    private static final int B = 4;
    private int bx;
    private int by;
    private float angle;
    private BufferedImage bulletImg;
    private BufferedImage explosionImg;
    private Rectangle hitBox;
    private Boolean visible;
    private Boolean explode;
    private SpriteSheet sheet;

    public Bullet(BufferedImage bulletImg, BufferedImage explosionImg, int x, int y, float angle) {
        this.bx = x;
        this.by = y;
        this.bulletImg = bulletImg;
        this.angle = angle;
        this.visible = true;
        this.explode = false;
        this.explosionImg = explosionImg;
        hitBox = new Rectangle(this.bx, this.by, this.getWidth(), this.getHeight());
    }

    @Override
    public int getWidth() {
        return this.bulletImg.getWidth();
    }

    @Override
    public int getHeight() {
        return this.bulletImg.getHeight();
    }

    private void update(){
        bx += Math.cos(Math.toRadians(angle)) * B;
        by += Math.sin(Math.toRadians(angle)) * B;
        hitBox.setLocation(this.bx, this.by);
    }

    @Override
    public String toString() {
        return "bx=" + bx + ", by=" + by + ", angle=" + angle;
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
        return this.hitBox;
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        if (this.hitBox != null && enemy.getHitBox() != null) {
            if (this.hitBox.intersects(enemy.getHitBox())){
                this.collision(enemy);
                this.visible = true;
                this.explode = true;
            }
        } return false;
    }

    @Override
    public void collision(Collidable enemy) {
       System.out.println("Collided with a Bullet");
    }

    @Override
    public void render(Graphics2D g2d) {
        if (visible) {
            update();
            AffineTransform rotation = AffineTransform.getTranslateInstance(bx, by);
            rotation.rotate(Math.toRadians(angle), this.bulletImg.getWidth(), this.bulletImg.getHeight());
            g2d.drawImage(bulletImg, rotation, null);
            g2d.setColor(Color.cyan);

            // Show hitbox
            if (this.showHitbox) {
                g2d.draw(this.hitBox);
            }
        }
    }
}
