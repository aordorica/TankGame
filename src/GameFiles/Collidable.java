package GameFiles;

import java.awt.*;

public interface Collidable {
    int health = 5;
    int lives = 3;
    Boolean visible = true;
    Rectangle hitBox = new Rectangle(0, 0, 0, 0);

    double getHealth();

    Boolean checkHealth();

    void takeHit();

    Rectangle getHitBox();

    Boolean checkCollision(Collidable enemy);


    void collision(Collidable enemy);

}
