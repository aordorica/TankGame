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
                this.visible = false;
                this.explode = true;
            }
        } return false;
    }

    @Override
    public void collision(Collidable enemy) {
       System.out.println("Collided with a Bullet");
    }

    private void explosion(Graphics2D g2d) {
        final int explosionWidth = 196;
        final int explosionHeight = 196;
        int explCol = 13;

        sheet = new SpriteSheet(explosionImg);

//        g2d.drawImage(sheet.crop(0, 0, explosionWidth, explosionHeight),
//                this.bx,
//                this.by,
//                null);
//        for (int i = 0; i < explCol; i++) {
//            g2d.drawImage(sheet.crop(i * explosionWidth, explosionHeight, explosionWidth, explosionHeight),
//                    this.bx,
//                    this.by,
//                    null);
//        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (visible) {
            update();
            AffineTransform rotation = AffineTransform.getTranslateInstance(bx, by);
            rotation.rotate(Math.toRadians(angle), this.bulletImg.getWidth(), this.bulletImg.getHeight());
            g2d.drawImage(bulletImg, rotation, null);
            g2d.setColor(Color.cyan);
            g2d.draw(hitBox);
        }

        if (explode) {
            //g2d.drawImage(explosionImg.getSubimage(0,0,196,196), this.bx, this.by, null);
            //explosion(g2d);
        }
    }
}
