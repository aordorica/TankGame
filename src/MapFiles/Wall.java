package MapFiles;

import GameFiles.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall implements GameObject {

    Boolean isbreakable;
    private BufferedImage image;
    private int width;
    private int height;
    private int locateX = 0;
    private int locateY = 0;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private void setLocation(int x, int y){
        this.locateX = x;
        this.locateY = y;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, locateX, locateY, width, height, null);
    }

    public void collision(){
        if(isbreakable){
            //getting shot code herd
        }
    }


}
