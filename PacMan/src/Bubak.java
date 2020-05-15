import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Bubak extends Rectangle {

    public Bubak(int x, int y, int offset_x,int offset_y, Vrchol v){
        setBounds(x,y,32,32);
        ksicht = Hlavna.postavy.dostanPostavu(offset_x,offset_y);
        pozicia = v;
        checkpoint = v;
        domov = v;
        rychlost = 1;

    }

    public BufferedImage ksicht;
    public Random r = new Random();
    public Vrchol pozicia;
    public Vrchol checkpoint;
    public int rychlost;
    public Vrchol domov;

    public void render(Graphics g){
        //g.fillRect(x,y,32,32);
        g.drawImage(ksicht,x,y,32,32,null);
    }

    public synchronized void tick(){

        //TO DO: zbubakovat;
        pohyb();

        //Hlavna.bludisko.bfs(pozicia,Hlavna.hrac.pozicia);
    }

    public void pohyb(){
        najdiPoziciu();
        //if(Hlavna.hrac.pozicia.x == pozicia.x && Hlavna.hrac.pozicia.y == pozicia.y) {
        if(Hlavna.hrac.okraje_obrazka.intersects(x,y,32,32)){
            //zivot dole
            if(Hlavna.hrac.pocet_zivotov > 1) {
                Hlavna.dolezity_label.setVisible(true);

                Hlavna.dolezity_label.setText("<html><div style='text-align: center;'>YOU LOST 1 LIFE <br> press space to try again </div></html>");
                Hlavna.hrac.pocet_zivotov--;
                Hlavna.level.zivoty.remove(Hlavna.level.zivoty.size()-1);
                Hlavna.isPaused = true;
                Hlavna.nova_hra();
            }
            else{
                //koniec hry
                Hlavna.dolezity_label.setVisible(true);
                Hlavna.dolezity_label.setText("<html><div style='text-align: center;'> GAME OVER <br> press space to exit game </div></html>");
                Hlavna.isRunning = false;
            }
            Hlavna.dolezity_label.setVisible(true);

            return;
        }

        Vrchol[] mozne_ciele = new Vrchol[1+Hlavna.hrac.pozicia.susedia.size()];
        mozne_ciele[0] = Hlavna.hrac.pozicia;
        for(int i = 0; i < Hlavna.hrac.pozicia.susedia.size(); i++){
            mozne_ciele[1 + i] = Hlavna.hrac.pozicia.susedia.get(i);
        }
        if((checkpoint.x * 32 == x && checkpoint.y * 32 == y)) checkpoint = Hlavna.bludisko.bfs(checkpoint,mozne_ciele[r.nextInt(mozne_ciele.length)]);
        else{
            if(checkpoint.x*32 >= x && checkpoint.y * 32 >= y) {
                x += rychlost;
                y += rychlost;
            }
            else if(checkpoint.x*32 >= x && checkpoint.y * 32 <= y) {
                x += rychlost;
                y -= rychlost;
            }
            else if(checkpoint.x*32 <= x && checkpoint.y * 32 >= y) {
                x -= rychlost;
                y += rychlost;
            }
            else if(checkpoint.x*32 <= x && checkpoint.y * 32 <= y) {
                x -= rychlost;
                y -= rychlost;
            }
        }
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
