package MapFiles;

import GameFiles.GameObject;
import GameFiles.GameWorld;
import GameFiles.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map implements GameObject {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Tank> tanks = new ArrayList<Tank>();
    public ArrayList<BreakableWall> breakableWalls = new ArrayList<BreakableWall>();
    public ArrayList<UnbreakableWall> unbreakableWalls = new ArrayList<UnbreakableWall>();

    private int width = 0;
    private int height = 0;
    private String fileName;
    private BufferedReader fileBuffer;
    private GameWorld game;
    private Graphics2D graphics2D;

    // Buffered image objects
    private BufferedImage breakableWall;
    private BufferedImage unBreakableWall;

    public Map(String fileName, GameWorld game) {
        this.fileName = fileName;
        this.game = game;
        mapLoader(fileName);
    }

    private void mapLoader(String file){

        breakableWall = game.imageHashMap.get("BreakableWall");
        unBreakableWall = game.imageHashMap.get("UnbreakableWall");

        String s = null;
        fileName = file;
        char character;

        try {
            fileBuffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));

            //Loop to print put map layout text file one line at a time
            while ((s=fileBuffer.readLine())!=null)
            {
                //System.out.println(s);
                for (int i = 0; i < s.length(); i++) {
                    character = s.charAt(i);
                    System.out.println(character);
                    switch(character) {
                        case '1':
                            unbreakableWalls.add(new UnbreakableWall(unBreakableWall, i*32, height*32));
                            break;
                        case '2':
                            breakableWalls.add(new BreakableWall(breakableWall, i*32, height*32));
                            break;
                        case '3':
                            break;
                    }
                }
                this.height++;
            }
            this.fileBuffer.close();
        } catch (IOException e) {
            System.out.println("Image File not found!");
            e.printStackTrace();
        }
    }

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
        Graphics2D g2 = (Graphics2D) g;
        for (BreakableWall temporaryWall : this.breakableWalls){
            temporaryWall.render(g2);
        }
        for (UnbreakableWall tempWall : this.unbreakableWalls){
            tempWall.render(g2);
        }
    }
}
