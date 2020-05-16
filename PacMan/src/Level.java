import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.imageio.ImageIO.read;

public class Level {
    public int width;
    public int height;
    //public JLabel score_label;

    public Block[][] blocks;

    public List<Apple> apples;
    public List<Ghost> ghosts;
    public List<Life> lives;

    public Level(){
        apples = new ArrayList<>();
        ghosts = new ArrayList<>();
        lives = new ArrayList<>();


        try {
            //vygenerovanie mapy a postav z obrazka
            BufferedImage map = ImageIO.read(getClass().getResource("/mapa.png"));
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
                        case 0xffffffff:
                            apples.add(new Apple(x*32,y*32));
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            break;
                        //cierna
                        case 0xff000000:
                            blocks[x][y] = new Block(x*32,y*32);
                            break;
                        //zlta
                        case 0xffffff00:
                            Game.player.x = x*32;
                            Game.player.y = y*32;
                            v = new Vertex(x,y);
                            Game.player.position = v;
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            break;
                        //cervena
                        case 0xffff0000:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*32,y*32,0,32,v));
                            break;
                        //modra
                        case 0xff0000ff:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*32,y*32,32,32,v));
                            break;
                        //zelena
                        case 0xff00ff00:
                            v = new Vertex(x,y);
                            find_neighbors(v);
                            Game.maze.graph.add(v);
                            ghosts.add(new Ghost(x*32,y*32,64,32,v));
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
                lives.add(new Life(Game.WIDTH - 96 - 32*i, 3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        for(int i = 0; i < ghosts.size(); i++){
            ghosts.get(i).tick();
        }
    }

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

    public void find_neighbors(Vertex w){
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
