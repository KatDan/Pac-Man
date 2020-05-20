package cz.cuni.mff.dancejova.game;

import cz.cuni.mff.dancejova.graph.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Renders and controls what is happening in the level's map
 */
public class Level {
    final static int BLOCKSIZE = 32;
    final static String MAPSOURCE = "/mapa.png";
    final static int BLACK = 0xff000000;
    final static int WHITE = 0xffffffff;
    final static int YELLOW = 0xffffff00;
    final static int RED = 0xffff0000;
    final static int GREEN = 0xff00ff00;
    final static int BLUE = 0xff0000ff;


    int width;
    int height;

    /**
     * A list of solid Blocks in a map
     */
    public Block[][] blocks;

    /**
     * A list of apples in a map
     */
    public List<Apple> apples;

    /**
     * A list of ghosts
     */
    public List<Ghost> ghosts;

    /**
     * A list of life icons
     */
    public List<Life> lives;

    /**
     * Constructs a Level from a MAPSOURCE image, creates a map, vertices, ghosts, apples and life icons
     */
    public Level(){
        apples = new ArrayList<>();
        ghosts = new ArrayList<>();
        lives = new ArrayList<>();
        try {
            //vygenerovanie mapy a postav z obrazka
            BufferedImage map = ImageIO.read(getClass().getResource(MAPSOURCE));
            this.width = map.getWidth();
            this.height = map.getHeight();

            blocks = new Block[width][height];
            int[] pixels = new int[width * height];
            map.getRGB(0,0, width, height, pixels, 0, width);

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    int value = pixels[x + (y* width)];
                    Vertex v;
                    switch (value){
                        //biela
                        case WHITE:
                            apples.add(new Apple(x*BLOCKSIZE,y*BLOCKSIZE));
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            break;
                        //cierna
                        case BLACK:
                            blocks[x][y] = new Block(x*BLOCKSIZE,y*BLOCKSIZE);
                            break;
                        //zlta
                        case YELLOW:
                            Game.player.x = x*BLOCKSIZE;
                            Game.player.y = y*BLOCKSIZE;
                            v = new Vertex(x,y);
                            Game.player.position = v;
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            break;
                        //cervena
                        case RED:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*BLOCKSIZE,y*BLOCKSIZE,0,Images.IMAGESIZE,v));
                            break;
                        //modra
                        case BLUE:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*BLOCKSIZE,y*BLOCKSIZE,Images.IMAGESIZE,Images.IMAGESIZE,v));
                            break;
                        //zelena
                        case GREEN:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*BLOCKSIZE,y*BLOCKSIZE,2*Images.IMAGESIZE,Images.IMAGESIZE,v));
                            break;
                        default:
                            //bloky okolo bubakov, kde nechceme jablcka
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            break;
                    }
                }
            }


            //nastavenie zivotov
            for(int i = 0; i < Game.player.lives; i++){
                lives.add(new Life(Game.WIDTH - 3*BLOCKSIZE - BLOCKSIZE*i, 3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the ghosts
     */
    public void tick(){
        for(int i = 0; i < ghosts.size(); i++){
            ghosts.get(i).tick();
        }
    }

    /**
     * Renders the map, ghosts and apples, updates the score and player's lives
     * @param g
     */
    public void render(Graphics g){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(blocks[x][y] != null) blocks[x][y].render(g);
            }
        }

        for(int i = 0; i < apples.size(); i++){
            apples.get(i).render(g);
        }

        for(int i = 0; i < ghosts.size(); i++){
            ghosts.get(i).render(g);
        }

        for(int i = 0; i < Game.player.lives; i++){
            lives.get(i).render(g);
        }
        Game.score_label.update(g);
    }


    void find_neighbors(Vertex w){
        int pom_x = w.x;
        int pom_y = w.y;
        for(Vertex v : Game.maze.graph){
            if((v.x == pom_x - 1 && v.y == pom_y)
                || (v.x == pom_x + 1 && v.y == pom_y)
                || (v.x == pom_x && v.y == pom_y - 1)
                || (v.x == pom_x && v.y == pom_y + 1)){
                w.neighbors.add(v);
                if(!v.neighbors.contains(w)) v.neighbors.add(w);
            }
        }
    }
}
