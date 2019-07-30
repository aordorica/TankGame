package MapFiles;

import java.awt.*;

public class UnbreakableWall extends Wall{
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(Graphics g) {

    }
}
