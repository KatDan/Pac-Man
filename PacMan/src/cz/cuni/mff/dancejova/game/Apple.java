package cz.cuni.mff.dancejova.game;

import java.awt.*;

/**
 * Constructs and renders an apple that Pac-Man eats to get score.
 */
public class Apple extends Rectangle {

    final static int APPLESIZE = 8;

    /**
     * Creates an Apple on coordinates [x,y] with size of Level.BLOCKSIZE
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     */
    public Apple(int x, int y){
        setBounds(x,y,Level.BLOCKSIZE,Level.BLOCKSIZE);
    }

    /**
     * Renders an apple
     * @param g
     */
    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(x + (Level.BLOCKSIZE-APPLESIZE)/2,y + (Level.BLOCKSIZE-APPLESIZE)/2,APPLESIZE,APPLESIZE);
    }
}
