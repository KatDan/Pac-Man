import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Rectangle {
    public Player(int x, int y){
         setBounds(x,y,22,22);
        score = 0;
        lives = 3;
    }

    public static int score;
    public int lives;
    public Vertex position;
    public static int head_x, head_y;
    public static Rectangle image_edges;

    public enum Direction {
        right, left, up, down, none
    };
    public Direction direction = Direction.none;

    private int speed = 2;
    public int i = 0;
    public boolean om_nom_nom_switch = false;
    public BufferedImage pacman_image;

    public void tick(){
        head_y = 0;
        //zisti, kam sa ma pozerat pacman a nastavi podla toho obrazok
        switch(direction){
            case right:
                head_x = 0;
                if(can_move(x+speed + 1, y)) x = x + speed;
                break;
            case left:
                head_x = 64;
                if(can_move(x-speed - 1, y)) x = x - speed;
                break;
            case up:
                head_x = 96;
                if(can_move(x, y-speed - 1)) y = y - speed;
                break;
            case down:
                head_x = 32;
                if(can_move(x, y+speed + 1)) y = y + speed;
                break;
            case none:
                head_x = 96;
                head_y = 32;
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

    public void render(Graphics g){
        //otvorene alebo zatvorene usta
        if(om_nom_nom_switch) pacman_image = Game.characters.get_character(head_x, head_y);
        else pacman_image = Game.characters.get_character(96,32);

        image_edges = new Rectangle(x,y,22,22);
        g.drawImage(pacman_image,x,y,22,22,null);
    }

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

    public void find_position(){
        //urci poziciu na stvorcekovej sieti
        for(Vertex v : position.neighbors){
            if(v.x == x / 32 && v.y == y / 32){
                position = v;
                break;
            }
        }
    }




}
