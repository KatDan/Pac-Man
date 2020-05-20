package cz.cuni.mff.dancejova.game;

import java.awt.*;

public class Block extends Rectangle {

    public Block(int x, int y){
        setBounds(x,y,Level.BLOCKSIZE,Level.BLOCKSIZE);
    }

    final static Color BLOCKCOLOR = new Color(6,5,45);

    public void render(Graphics g){
        g.setColor(BLOCKCOLOR);
        g.fillRect(x,y,width,height);
    }
}
