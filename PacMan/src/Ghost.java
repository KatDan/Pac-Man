import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Ghost extends Rectangle {

    public Ghost(int x, int y, int offset_x, int offset_y, Vertex v){
        setBounds(x,y,GHOSTSIZE,GHOSTSIZE);
        body = Game.characters.get_character(offset_x,offset_y);
        position = v;
        checkpoint = v;
        home = v;
        speed = 1;
    }

    final static int GHOSTSIZE = 32;

    public BufferedImage body;
    public Random r = new Random();
    public Vertex position;
    public Vertex checkpoint;
    public int speed;
    public Vertex home;

    public void render(Graphics g){
        g.drawImage(body,x,y,GHOSTSIZE,GHOSTSIZE,null);
    }

    public void tick(){
        move();
    }

    public void move(){
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
