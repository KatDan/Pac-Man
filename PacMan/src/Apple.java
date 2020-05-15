import java.awt.*;

public class Apple extends Rectangle {

    public Apple(int x, int y){
        setBounds(x,y,32,32);
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(x + 12,y + 12,8,8);
    }
}
