import java.awt.*;
import java.awt.image.BufferedImage;

public class Life extends Rectangle {
    public Life(int x, int y){
        setBounds(x,y,HEARTICONSIZE,HEARTICONSIZE);

    }
    final static int HEARTICONSIZE = 26;

    public BufferedImage heart = Game.characters.get_character(0,Images.IMAGESIZE * 2);

    public void render(Graphics g){
        g.drawImage(heart, x + (Level.BLOCKSIZE-HEARTICONSIZE)/2, y + (Level.BLOCKSIZE-HEARTICONSIZE)/2, HEARTICONSIZE, HEARTICONSIZE,null);
    }




}
