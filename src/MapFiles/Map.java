package MapFiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map {
    ArrayList<Wall> walls = new ArrayList<Wall>();

    public void add(Wall piece){
        walls.add(piece);
    }
}
