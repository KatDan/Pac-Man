import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Hrac extends Rectangle {
    public Hrac(int x, int y){
        //this.x = x;
        //this.y = y;
        setBounds(x,y,22,22);
        skore = 0;
        pocet_zivotov = 3;
    }

    public static int skore;
    public int pocet_zivotov;
    public Vrchol pozicia;
    public static int stav_x,stav_y;
    public static Rectangle okraje_obrazka;
    //public Level level = Hlavna.level;

    public enum Smer{
        right, left, up, down, none
    };

    public Smer smer = Smer.none;

    private int speed = 2;


    public void tick(){
        stav_y = 0;
        switch(smer){
            case right:
                stav_x = 0;
                if(vieSaPohnut(x+speed + 1, y)) x = x + speed;
                break;
            case left:
                stav_x = 64;
                if(vieSaPohnut(x-speed - 1, y)) x = x - speed;
                break;
            case up:
                stav_x = 96;
                if(vieSaPohnut(x, y-speed - 1)) y = y - speed;
                break;
            case down:
                stav_x = 32;
                if(vieSaPohnut(x, y+speed + 1)) y = y + speed;
                break;
            case none:
                stav_x = 96;
                stav_y = 32;
                break;
        }
        najdiPoziciu();


        Level level = Hlavna.level;
        if(level.apples.size() != 0){
            for(int i = 0; i < level.apples.size(); i++){
                if(this.intersects(level.apples.get(i))){
                    level.apples.remove(i);
                    skore++;
                    break;
                }
            }
        }
        else{
            Hlavna.dolezity_label.setVisible(true);
            Hlavna.dolezity_label.setText("<html><div style='text-align: center;'>YOU WON! <br> press space to exit</div></html>");
            Hlavna.isPaused = true;
            Hlavna.isRunning = false;
        }



    }

    public int i = 0;
    public boolean spinac = false;
    public BufferedImage postava;

    public void render(Graphics g){
        g.setColor(Color.yellow);
        //g.fillRect(x,y,width,height);
        if(spinac) postava = Hlavna.postavy.dostanPostavu(stav_x,stav_y);
        else postava = Hlavna.postavy.dostanPostavu(96,32);
        okraje_obrazka = new Rectangle(x,y,22,22);
        g.drawImage(postava,x,y,22,22,null);
    }

    public boolean vieSaPohnut(int xx, int yy){
        Rectangle hranice = new Rectangle(xx,yy,width,height);
        Level level = Hlavna.level;
        for(int y = 0; y < level.bloky.length; y++){
            for(int x = 0; x < level.bloky[0].length; x++){
                if(level.bloky[y][x] != null){
                    if(hranice.intersects(level.bloky[y][x])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void najdiPoziciu(){
        for(Vrchol v : pozicia.susedia){
            if(v.x == x / 32 && v.y == y / 32){
                pozicia = v;
                break;
            }
        }
    }




}
