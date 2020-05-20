package cz.cuni.mff.dancejova.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Constructs and renders a life icon seen in the upper right corner
 */
public class Life extends Rectangle {
    /**
     * Constructs a life icon
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     */
    public Life(int x, int y){
        setBounds(x,y,HEARTICONSIZE,HEARTICONSIZE);
    }
    final static int HEARTICONSIZE = 26;

    public BufferedImage heart = Game.characters.get_character(0,Images.IMAGESIZE * 2);

    /**
     * Renders a heart icon
     * @param g
     */
    public void render(Graphics g){
        g.drawImage(heart, x + (Level.BLOCKSIZE-HEARTICONSIZE)/2, y + (Level.BLOCKSIZE-HEARTICONSIZE)/2, HEARTICONSIZE, HEARTICONSIZE,null);
    }




}
