package cz.cuni.mff.dancejova.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {

    private BufferedImage image;
    final static int IMAGESIZE = 32;

    public Images(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage get_character(int x, int y){
        return image.getSubimage(x,y,IMAGESIZE,IMAGESIZE);
    }
}
