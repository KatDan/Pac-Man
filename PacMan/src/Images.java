import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {

    private BufferedImage image;

    public Images(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage get_character(int x, int y){
        return image.getSubimage(x,y,32,32);
    }
}
