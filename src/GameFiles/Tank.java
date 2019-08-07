package GameFiles;

import MapFiles.BreakableWall;
import MapFiles.UnbreakableWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank implements Collidable, GameObject{

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int health;
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


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImg, BufferedImage bullet, BufferedImage explosionImg) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankImg = tankImg;
        this.bulletImg = bullet;
        this.explosionImg = explosionImg;
        this.health = Collidable.health;
        this.lives = Collidable.lives;
    }

    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}

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

    public ArrayList<Bullet> getBullets() {
        return bullets;
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

    public void explosion(Graphics2D g2d) {
        final int explosionWidth = explosionImg.getWidth() / 13;
        final int explosionHeight = 196;
        int explCol = 13;

        sheet = new SpriteSheet(explosionImg);
        for (int i = 0; i < explCol; i++) {
            g2d.drawImage(sheet.crop(i * explosionWidth, explosionHeight, explosionWidth, explosionHeight),
                    this.x,
                    this.y,
                    null);
        }
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        if (this.getHitBox() != null) {
            if (this.hitBox.intersects(enemy.getHitBox())) {
                collision(enemy);
                return true;
            }
        }
        return false;
    }

    @Override
    public void collision(Collidable enemy) {
        Rectangle rect = enemy.getHitBox().intersection(this.hitBox);
        if (enemy instanceof Bullet) {
            System.out.println("Collided with a Bullet");
            this.health--;
            enemy.checkCollision(this);
            //explosion(graphics2D);
            checkHealth();
        } else if (enemy instanceof Tank) {
            bounceBack(enemy, rect);
        } else if (enemy instanceof BreakableWall) {
            bounceBack(enemy, rect);
        } else if (enemy instanceof UnbreakableWall) {
            bounceBack(enemy, rect);
        } else if (enemy instanceof PowerUp) {
            //powerup code here
        }
    }

    public void bounceBack(Collidable enemy, Rectangle intersection) {

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
        if (this.health <= 0) {
            this.setLives(false);
        } else
            this.setLives(true);
        return true;
    }

    @Override
    public void takeHit() {

    }

    public void setLives(Boolean state) {
        if (!state) {
            this.lives--;
        }

    }

    @Override
    public Rectangle getHitBox() {
        hitBox = new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
        return this.hitBox;
    }

    private void setGraphics(Graphics2D g2d) {
        this.graphics2D = g2d;
    }

    @Override
    public void render(Graphics2D g2d) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImg.getWidth() / 2.0, this.tankImg.getHeight() / 2.0);
        g2d.drawImage(this.tankImg, rotation, null);
        g2d.draw(this.hitBox);
        g2d.setColor(Color.red);
        g2d.fillOval(this.x, this.y, 10, 10);

        for (Bullet tempBullet : this.bullets){
            if(readyToFire)
                tempBullet.render(g2d);
        }

        g2d.setColor(Color.CYAN);
        g2d.drawRect(this.x, this.y+this.getHeight(), this.getWidth(), 5);
        g2d.setColor(Color.red);
        g2d.fillRect(this.x, this.y + this.getWidth(), this.getWidth()-8, 5);

        g2d.setColor(Color.red);
        for (int i = 0; i < this.health; i++) {
            g2d.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 + (i*50), GameConstants.GAME_SCREEN_HEIGHT + 50, 28, 38);
        }
        setGraphics(g2d);
    }
}