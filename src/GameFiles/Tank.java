package GameFiles;

import MapFiles.BreakableWall;
import MapFiles.UnbreakableWall;
import MapFiles.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank implements Collidable, GameObject{

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private JFrame frame;

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int health;
    private int fullHealth;
    private int lives;
    private float angle;
    private Rectangle hitBox;
    private SpriteSheet sheet;
    private Graphics2D graphics2D;

    private final int R = 2;
    private final float ROTATIONSPEED = 4.0f;
    private BufferedImage bulletImg;
    private BufferedImage tankImg;
    private BufferedImage explosionImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean readyToFire;
    private boolean shot;


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImg, BufferedImage bullet, BufferedImage explosionImg, JFrame frame) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankImg = tankImg;
        this.bulletImg = bullet;
        this.explosionImg = explosionImg;
        this.health = Collidable.health;
        this.fullHealth = Collidable.health;
        this.lives = Collidable.lives;
        this.frame = frame;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        //System.out.println(this);
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        //System.out.println(this);
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public int getWidth() {
        return this.tankImg.getWidth();
    }

    @Override
    public int getHeight() {
        return this.tankImg.getHeight();
    }


    public void shoot(){
        if(bullets.isEmpty()) {
            readyToFire = true;
        }
        if(readyToFire) {
            bullets.add(new Bullet(bulletImg, explosionImg, x + tankImg.getWidth() + 10, y + tankImg.getHeight()/2, this.angle));
            shot = true;
        }
    }


    @Override
    public Boolean checkCollision(Collidable enemy) {
        if (this.getHitBox() != null) {
            if (this.hitBox.intersects(enemy.getHitBox())) {
                collision(enemy);
            }
            if (!this.bullets.isEmpty()) {
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).getHitBox().intersects(enemy.getHitBox())) {
                        enemy.takeHit();
                        bullets.remove(i);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void collision(Collidable enemy) {
        Rectangle rect = enemy.getHitBox().intersection(this.hitBox);

        if (enemy instanceof Tank) {
            bounceBack(rect);
            //Take damage if two tanks hit each other
            this.health--;
        } else if (enemy instanceof Wall) {
            bounceBack(rect);
        } else if (enemy instanceof PowerUp) {
            this.health = fullHealth;
        }
    }

    public void bounceBack(Rectangle intersection) {

        // Hit from the LEFT
        if (this.x < intersection.x) {
            if (intersection.width < intersection.height) {
                this.x = this.x - intersection.width;
                System.out.println("Collided from the LEFT");
            }
        }
        // Hit from the RIGHT
        if (this.x >= intersection.x) {
            if (intersection.width < intersection.height) {
                this.x = this.x + intersection.width;
                System.out.println("Collided from the RIGHT");
            }
        }

        // Hit from the TOP
        if (this.y < intersection.y) {
            if (intersection.width > intersection.height) {
                this.y = this.y - intersection.height;
                System.out.println("Collided from the TOP");
            }
        }

        // Hit from BOTTOM
        if (this.y >= intersection.y) {
            if (intersection.width > intersection.height) {
                this.y = this.y + intersection.height;
                System.out.println("Collided from the BOTTOM");
            }
        }

    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public Boolean checkHealth() {
        this.health = fullHealth;
        return null;
    }

    @Override
    public void takeHit() {
        this.health--;
    }

    @Override
    public Rectangle getHitBox() {
        hitBox = new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
        return this.hitBox;
    }

    @Override
    public void render(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImg.getWidth() / 2.0, this.tankImg.getHeight() / 2.0);
        g2d.drawImage(this.tankImg, rotation, null);

        //Show hitbox if GameConstants method is TRUE
        if (this.showHitbox) {
            g2d.draw(this.hitBox);
        }

        g2d.setColor(Color.red);
        g2d.fillOval(this.x, this.y, 10, 10);

        for (Bullet tempBullet : this.bullets){
            if(readyToFire)
                tempBullet.render(g2d);
        }

        g2d.setColor(Color.CYAN);
        g2d.drawRect(this.x, this.y+this.getHeight(), this.getWidth(), 7);

        //Draw Lives


        //Draw Health Bar
        int barLength = this.getWidth() / fullHealth;
        int bar = barLength * this.health;
        if (this.health >= this.fullHealth) {
            g2d.setColor(Color.GREEN);
        } else if (this.health > fullHealth / 4) {
            g2d.setColor(Color.YELLOW);
        } else
            g2d.setColor(Color.red);
        g2d.fillRect(this.x, this.y+this.getHeight(), bar, 7);

    }
}