package GameFiles;

import GameFiles.Tank;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class ImageLoader {
    public static BufferedImage loadImages(String path){
        try {
            return ImageIO.read(getClass.class.getResource(path));
        } catch (IOException e) {
            System.exit(1);
        }
        return null;
    }
}
