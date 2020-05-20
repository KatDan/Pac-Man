package cz.cuni.mff.dancejova.game;

import java.awt.*;

/**
 * Creates and renders a solid block in the level map
 */
public class Block extends Rectangle {

    /**
     * Constructs a Block with size of Level.BLOCKSIZE
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     */
    public Block(int x, int y){
        setBounds(x,y,Level.BLOCKSIZE,Level.BLOCKSIZE);
    }

    final static Color BLOCKCOLOR = new Color(6,5,45);

    /**
     * Renders a Block
     * @param g
     */
    public void render(Graphics g){
        g.setColor(BLOCKCOLOR);
        g.fillRect(x,y,width,height);
    }
}
