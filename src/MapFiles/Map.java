package MapFiles;

import GameFiles.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map implements GameObject, Collidable {
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();

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
        //background = new BackgroundLandscape(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, bg);

        String s;
        fileName = file;
        char character;

        try {
            fileBuffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));
            this.width = fileBuffer.readLine().length();
            //Loop to print put map layout text file one line at a time
            while ((s=fileBuffer.readLine())!=null)
            {
                //System.out.println(s);
                for (int i = 0; i < s.length(); i++) {
                    character = s.charAt(i);
                    //System.out.println(character);
                    switch(character) {
                        case '1':
                            walls.add(new UnbreakableWall(unBreakableWall, i*32, height*32));
                            break;
                        case '2':
                            walls.add(new BreakableWall(breakableWall, i*32, height*32));
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
        background = new BackgroundLandscape(width * 32, height * 32, bg);
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

        Tank tank = (Tank ) enemy;

        for (int i = 0; i < walls.size(); i++) {
            tank.checkCollision((Collidable) walls.get(i));
            if (!((Collidable) walls.get(i)).checkHealth()) {
                walls.remove(i);
            }
        }

        for (int i = 0; i < powerUps.size(); i++) {
            tank.checkCollision(powerUps.get(i));
        }
        return null;
    }

    @Override
    public void collision(Collidable enemy) {

    }

    @Override
    public void render(Graphics2D g2d) {

        for (Wall wall : this.walls){
            wall.render(g2d);
        }
        for (PowerUp powerUp : this.powerUps){
            powerUp.render(g2d);
        }
    }

    public void renderBackground(Graphics2D graphics2D) {
        background.render(graphics2D);
    }

}
