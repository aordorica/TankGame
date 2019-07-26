package GameFiles;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author anthony-pc
 */
public class TankControl implements KeyListener {

    private Tank t1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    private final int esc;

    public TankControl(Tank t1, int up, int down, int left, int right, int shoot, int esc) {
        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
        this.esc = esc;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("This key is pressed: " + ke.getKeyCode());
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.t1.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.t1.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.t1.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.t1.toggleRightPressed();
        }
        if (keyPressed == esc) {
            System.exit(0);
        }


    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.t1.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.t1.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.t1.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.t1.unToggleRightPressed();
        }

    }
}
