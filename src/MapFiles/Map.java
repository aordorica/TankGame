package MapFiles;

import GameFiles.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map implements GameObject, Collidable {
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<BreakableWall> breakableWalls = new ArrayList<>();
    private ArrayList<UnbreakableWall> unbreakableWalls = new ArrayList<>();

    private int width = 0;
    private int height = 0;
    private int x;
    private int y;
    private String fileName;
    private BufferedReader fileBuffer;
    private GameWorld game;
    private BackgroundLandscape background;
    private Rectangle hitBox;

    public Map(String fileName, GameWorld game) {
        this.fileName = fileName;
        this.game = game;
        this.x = 0;
        this.y = 0;
        mapLoader(fileName);
    }

    private void mapLoader(String file){

        // Buffered image objects
        BufferedImage breakableWall = game.imageHashMap.get("BreakableWall");
        BufferedImage unBreakableWall = game.imageHashMap.get("UnbreakableWall");
        BufferedImage powerUpImg = game.imageHashMap.get("PowerUp");
        BufferedImage bg = game.imageHashMap.get("Background");
        background = new BackgroundLandscape(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, bg);

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
        hitBox = new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
        return this.hitBox;
    }

    @Override
    public Boolean checkCollision(Collidable enemy) {
        return null;
    }

    @Override
    public void collision(Collidable enemy) {

    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public ArrayList<BreakableWall> getBreakableWalls() {
        return breakableWalls;
    }

    public ArrayList<UnbreakableWall> getUnbreakableWalls() {
        return unbreakableWalls;
    }

    @Override
    public void render(Graphics2D g2d) {

        this.background.render(g2d);

        for (BreakableWall temporaryWall : this.breakableWalls){
            temporaryWall.render(g2d, null);
        }
        for (UnbreakableWall tempWall : this.unbreakableWalls){
            tempWall.render(g2d);
        }
        for (PowerUp powerUp : this.powerUps){
            powerUp.render(g2d);
        }
    }

}
