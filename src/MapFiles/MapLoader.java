package MapFiles;

import GameFiles.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MapLoader implements GameObject {

    private int width;
    private int height;
    private String fileName;
    private BufferedReader fileBuffer;

    String s = null;

    public MapLoader(String file){
        this.fileName = file;

        try {
            fileBuffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));
            this.width = this.fileBuffer.readLine().length();
            //Loop to print put map layout text file one line at a time
            while ((s=fileBuffer.readLine())!=null)
            {
                System.out.println(s);
                for (char character: s.toCharArray()) {
                    if (character == 1){

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void render(Graphics g) {
    }
}
