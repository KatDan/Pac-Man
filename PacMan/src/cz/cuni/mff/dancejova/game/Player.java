package cz.cuni.mff.dancejova.game;

import cz.cuni.mff.dancejova.graph.Vertex;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renders and validates Pac-Man's movement
 */
public class Player extends Rectangle {

    /**
     * Constructs Pac-Man
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     */
    public Player(int x, int y){
         setBounds(x,y,PACMANSIZE,PACMANSIZE);
        score = 0;
        lives = 3;
    }

    final static int PACMANSIZE = 22;


    /**
     * The player's current score
     */
    public static int score;

    /**
     * The player's current number of lives
     */
    public int lives;

    /**
     * The player's position in the map of squares
     */
    public Vertex position;

    static int head_x, head_y;
    static Rectangle image_edges;

    /**
     * Provides rendering the head looking in the right direction
     */
    public enum Direction {
        right, left, up, down, none
    };

    /**
     * The player's current direction
     */
    public Direction direction = Direction.none;

    private int speed = 2;

    /**
     * Determines whether the Pac-Man's mouth is open or closed
     */
    public boolean om_nom_nom_switch = false;

    BufferedImage pacman_image;

    /**
     * Moves Pac-Man to the new position, if possible and checks whether it crossed any Apple
     */
    public void tick(){
        head_y = 0;
        //zisti, kam sa ma pozerat pacman a nastavi podla toho obrazok
        switch(direction){
            case right:
                head_x = 0;
                if(can_move(x+speed + 1, y)) x = x + speed;
                break;
            case left:
                head_x = 2*Images.IMAGESIZE;
                if(can_move(x-speed - 1, y)) x = x - speed;
                break;
            case up:
                head_x = 3*Images.IMAGESIZE;
                if(can_move(x, y-speed - 1)) y = y - speed;
                break;
            case down:
                head_x = Images.IMAGESIZE;
                if(can_move(x, y+speed + 1)) y = y + speed;
                break;
            case none:
                head_x = 3*Images.IMAGESIZE;
                head_y = Images.IMAGESIZE;
                break;
        }
        find_position();

        //spravuje jedenie jablcok
        Level level = Game.level;
        if(level.apples.size() != 0){
            for(int i = 0; i < level.apples.size(); i++){
                if(this.intersects(level.apples.get(i))){
                    level.apples.remove(i);
                    score++;
                    break;
                }
            }
        }
        else{
            //zjedenie vsetkych jablcok, vyhra a koniec hry
            Game.main_label.setVisible(true);
            Game.main_label.setText("<html><div style='text-align: center;'>YOU WON! <br> press space to exit</div></html>");
            Game.isPaused = true;
            Game.isRunning = false;
        }
    }

    /**
     * Renders Pac-Man to the screen.
     * @param g
     */
    public void render(Graphics g){
        //otvorene alebo zatvorene usta
        if(om_nom_nom_switch) pacman_image = Game.characters.get_character(head_x, head_y);
        else pacman_image = Game.characters.get_character(3*Images.IMAGESIZE,Images.IMAGESIZE);

        image_edges = new Rectangle(x,y,PACMANSIZE,PACMANSIZE);
        g.drawImage(pacman_image,x,y,PACMANSIZE,PACMANSIZE,null);
    }

    /**
     * Checks whether Pac-Man can move to the position
     * @param xx The horizontal coordinate of a new position
     * @param yy The vertical coordinate of a new position
     * @return true if Pac-Man can move to [xx,yy], false otherwise
     */
    public boolean can_move(int xx, int yy){
        //zisti, ci sa moze pohnut na poziciu bez prerazenia steny
        Rectangle image_edges = new Rectangle(xx,yy,width,height);
        Level level = Game.level;
        for(int y = 0; y < level.blocks.length; y++){
            for(int x = 0; x < level.blocks[0].length; x++){
                if(level.blocks[y][x] != null){
                    if(image_edges.intersects(level.blocks[y][x])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Finds Pac-Man's position in the map of squares according to its actual position
     */
    public void find_position(){
        //urci poziciu na stvorcekovej sieti
        for(Vertex v : position.neighbors){
            if(v.x == x / Level.BLOCKSIZE && v.y == y / Level.BLOCKSIZE){
                position = v;
                break;
            }
        }
    }




}
