package GameFiles;

import GameFiles.Tank;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class ImageLoader {
    public static BufferedImage loadImages(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(ImageLoader.class.getClassLoader().getResource(path)));
        } catch (IOException e) {
            System.exit(1);
            return null;
        }
    }
}
