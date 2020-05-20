package cz.cuni.mff.dancejova.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Provides using images of characters from a source in .png format
 */
public class Images {

    private BufferedImage image;
    final static int IMAGESIZE = 32;

    /**
     * Reads image from a file.
     * @param path The source of images used in a game
     */
    public Images(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a subimage of a character icon we want to use
     * @param x The horizontal coordinate in pixels
     * @param y The vertical coordinate in pixels
     * @return The image of a particular character
     */
    public BufferedImage get_character(int x, int y){
        return image.getSubimage(x,y,IMAGESIZE,IMAGESIZE);
    }
}
