package cz.cuni.mff.dancejova.game;

import java.awt.*;

public class Apple extends Rectangle {

    final static int APPLESIZE = 8;

    public Apple(int x, int y){
        setBounds(x,y,Level.BLOCKSIZE,Level.BLOCKSIZE);
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(x + (Level.BLOCKSIZE-APPLESIZE)/2,y + (Level.BLOCKSIZE-APPLESIZE)/2,APPLESIZE,APPLESIZE);
    }
}
