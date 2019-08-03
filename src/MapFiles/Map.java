package MapFiles;

import GameFiles.GameObject;
import GameFiles.GameWorld;
import GameFiles.PowerUp;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map implements GameObject {
    public ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    public ArrayList<BreakableWall> breakableWalls = new ArrayList<BreakableWall>();
    public ArrayList<UnbreakableWall> unbreakableWalls = new ArrayList<UnbreakableWall>();

    private int width = 0;
    private int height = 0;
    private String fileName;
    private BufferedReader fileBuffer;
    private GameWorld game;
    private Graphics2D graphics2D;

    public Map(String fileName, GameWorld game) {
        this.fileName = fileName;
        this.game = game;
        mapLoader(fileName);
    }

    private void mapLoader(String file){

        // Buffered image objects
        BufferedImage breakableWall = game.imageHashMap.get("BreakableWall");
        BufferedImage unBreakableWall = game.imageHashMap.get("UnbreakableWall");
        BufferedImage powerUpImg = game.imageHashMap.get("PowerUp");

        String s;
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
                    //System.out.println(character);
                    switch(character) {
                        case '1':
                            unbreakableWalls.add(new UnbreakableWall(unBreakableWall, i*32, height*32));
                            break;
                        case '2':
                            breakableWalls.add(new BreakableWall(breakableWall, i*32, height*32));
                            break;
                        case '3':
                            powerUps.add(new PowerUp(powerUpImg, i*32, height*32));
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
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void render(Graphics2D g2d) {
        for (BreakableWall temporaryWall : this.breakableWalls){
            temporaryWall.render(g2d);
        }
        for (UnbreakableWall tempWall : this.unbreakableWalls){
            tempWall.render(g2d);
        }
        for (PowerUp powerUp : this.powerUps){
            powerUp.render(g2d);
        }
    }
}
