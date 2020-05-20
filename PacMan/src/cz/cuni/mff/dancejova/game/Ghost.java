package cz.cuni.mff.dancejova.game;

import cz.cuni.mff.dancejova.graph.Vertex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Constructs and renders the ghosts
 */

public class Ghost extends Rectangle {

    /**
     * Constructs a ghost
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     * @param offset_x The horizontal coordinate in pixels of an image from Images class used for this ghost
     * @param offset_y The vertical coordinate in pixels of an image
     * @param v Starting Vertex of the ghost
     */
    public Ghost(int x, int y, int offset_x, int offset_y, Vertex v){
        setBounds(x,y,GHOSTSIZE,GHOSTSIZE);
        body = Game.characters.get_character(offset_x,offset_y);
        position = v;
        checkpoint = v;
        home = v;
        speed = 1;
    }

    final static int GHOSTSIZE = 32;

    BufferedImage body;
    Random r = new Random();

    /**
     * Vertex the ghost is currently in
     */
    public Vertex position;

    /**
     * Vertex that ghost goes to
     */
    public Vertex checkpoint;

    /**
     * the ghost's speed
     */
    public int speed;

    /**
     * The initial Vertex of the ghost
     */
    public Vertex home;

    /**
     * Renders a ghost
     * @param g
     */
    public void render(Graphics g){
        g.drawImage(body,x,y,GHOSTSIZE,GHOSTSIZE,null);
    }

    /**
     * Makes a ghost's move
     */
    public void tick(){
        move();
    }

    void move(){
        find_position();
        if(Game.player.image_edges.intersects(x,y,GHOSTSIZE,GHOSTSIZE)){
            //zivot dole
            if(Game.player.lives > 1) {
                Game.main_label.setVisible(true);
                Game.main_label.setText("<html><div style='text-align: center;'>YOU LOST 1 LIFE <br> press space to try again </div></html>");
                Game.player.lives--;
                Game.level.lives.remove(Game.level.lives.size()-1);
                Game.isPaused = true;
                Game.new_game();
            }
            else{
                //koniec hry
                Game.main_label.setVisible(true);
                Game.main_label.setText("<html><div style='text-align: center;'> GAME OVER <br> press space to exit game </div></html>");
                Game.isRunning = false;
            }
            Game.main_label.setVisible(true);
            return;
        }

        //vrcholy, ktore pre bubaka mozu byt cielove
        Vertex[] possible_targets = new Vertex[1+ Game.player.position.neighbors.size()];
        possible_targets[0] = Game.player.position;
        for(int i = 0; i < Game.player.position.neighbors.size(); i++){
            possible_targets[1 + i] = Game.player.position.neighbors.get(i);
        }
        //najdenie suradnic najblizsieho bloku na ceste do ciela
        if((checkpoint.x * Level.BLOCKSIZE == x && checkpoint.y * Level.BLOCKSIZE == y)) checkpoint = Game.maze.bfs(checkpoint,possible_targets[r.nextInt(possible_targets.length)]);
        else{
            if(checkpoint.x*Level.BLOCKSIZE >= x && checkpoint.y * Level.BLOCKSIZE >= y) {
                x += speed;
                y += speed;
            }
            else if(checkpoint.x*Level.BLOCKSIZE >= x && checkpoint.y * Level.BLOCKSIZE <= y) {
                x += speed;
                y -= speed;
            }
            else if(checkpoint.x*Level.BLOCKSIZE <= x && checkpoint.y * Level.BLOCKSIZE >= y) {
                x -= speed;
                y += speed;
            }
            else if(checkpoint.x*Level.BLOCKSIZE <= x && checkpoint.y * Level.BLOCKSIZE <= y) {
                x -= speed;
                y -= speed;
            }
        }
    }

    /**
     * finds a Vertex the ghost is currently in according to its actual position
     */
    public void find_position(){
        //najdenie aktualneho bloku na stvorcekovej sieti
        for(Vertex v : position.neighbors){
            if(v.x == x / Level.BLOCKSIZE && v.y == y / Level.BLOCKSIZE){
                position = v;
                break;
            }
        }
    }
}
