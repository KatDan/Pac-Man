import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObrazkyPostav {

    private BufferedImage obrazok;

    public ObrazkyPostav(String path){
        try {
            obrazok = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage dostanPostavu(int x, int y){
        return obrazok.getSubimage(x,y,32,32);
    }

}
