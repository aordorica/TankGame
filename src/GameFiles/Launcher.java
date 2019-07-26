package GameFiles;

import javax.swing.*;


public class Launcher extends JPanel {


    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    public static void main(String[] args){

        Game game = new Game("Tank Wars!!", SCREEN_WIDTH, SCREEN_HEIGHT);
        game.start();
    }

}