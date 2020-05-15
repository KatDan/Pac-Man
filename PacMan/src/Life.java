import java.awt.*;
import java.awt.image.BufferedImage;

public class Life extends Rectangle {
    public Life(int x, int y){
        setBounds(x,y,26,26);

    }
    public BufferedImage heart = Game.characters.get_character(0,64);

    public void render(Graphics g){
        g.drawImage(heart, x + 4, y + 4, 26, 26,null);
    }




}
