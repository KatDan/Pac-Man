import java.awt.*;

public class Blok extends Rectangle {

    public Blok(int x, int y){
        setBounds(x,y,32,32);
    }

    public void render(Graphics g){
        g.setColor(new Color(6,5,45));
        g.fillRect(x,y,width,height);
    }
}
