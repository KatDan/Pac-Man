import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Zivot extends Rectangle {
    public Zivot(int x, int y){
        setBounds(x,y,26,26);

    }
    public BufferedImage srdiecko = Hlavna.postavy.dostanPostavu(0,64);

    public void render(Graphics g){
        g.drawImage(srdiecko, x + 4, y + 4, 26, 26,null);
    }




}
