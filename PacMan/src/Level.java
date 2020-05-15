import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Level {
    public int sirka;
    public int vyska;
    public JLabel skore_label;
    public JLabel you_won_label;

    public Blok[][] bloky;

    public List<Apple> apples;
    public boolean running = true;
    public List<Bubak> bubaci;
    public List<Zivot> zivoty;

    public Level(){
        apples = new ArrayList<>();
        bubaci = new ArrayList<>();
        zivoty = new ArrayList<>();

        skore_label = new JLabel("Your score: 0", JLabel.CENTER);
        skore_label.setOpaque(true);
        try {
            BufferedImage mapa = ImageIO.read(getClass().getResource("/mapa.png"));
            this.sirka = mapa.getWidth();
            this.vyska = mapa.getHeight();

            bloky = new Blok[sirka][vyska];

            int[] pixely = new int[sirka*vyska];
            mapa.getRGB(0,0,sirka,vyska, pixely, 0,sirka);
            for(int y = 0; y < vyska; y++){
                for(int x = 0; x < sirka; x++){
                    int hodnota = pixely[x + (y*sirka)];
                    Vrchol v;
                    switch (hodnota){
                        //biela
                        case 0xffffffff:
                            apples.add(new Apple(x*32,y*32));
                            v = new Vrchol(x,y);
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);
                            break;
                        //cierna
                        case 0xff000000:
                            bloky[x][y] = new Blok(x*32,y*32);
                            break;
                        //zlta
                        case 0xffffff00:
                            Hlavna.hrac.x = x*32;
                            Hlavna.hrac.y = y*32;
                            v = new Vrchol(x,y);
                            Hlavna.hrac.pozicia = v;
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);

                            break;
                        //cervena
                        case 0xffff0000:

                            v = new Vrchol(x,y);
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);
                            bubaci.add(new Bubak(x*32,y*32,0,32,v));
                            break;
                        //modra
                        case 0xff0000ff:

                            v = new Vrchol(x,y);
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);
                            //Hlavna.bludisko.domov = new Node(new Vrchol(x,y));
                            bubaci.add(new Bubak(x*32,y*32,32,32,v));
                            break;
                        //zelena
                        case 0xff00ff00:

                            v = new Vrchol(x,y);
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);
                            bubaci.add(new Bubak(x*32,y*32,64,32,v));
                            break;
                        default:
                            v = new Vrchol(x,y);
                            najdi_susedov(v);
                            Hlavna.bludisko.graf.add(v);
                            break;
                    }
                }
            }
            skore_label.setBounds(0,0,150,32);
            skore_label.setForeground(Color.white);
            skore_label.setBackground(new Color(6,5,45));


            for(int i = 0; i < Hlavna.hrac.pocet_zivotov; i++){
                zivoty.add(new Zivot(Hlavna.WIDTH - 96 - 32*i, 3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        for(int i = 0; i < bubaci.size(); i++){
            bubaci.get(i).tick();
        }
        for(int i = 0; i < bubaci.size(); i++){
            if(Hlavna.hrac.intersects(bubaci.get(i))){
                //Game Over
                running = false;
                break;
            }
        }
    }


    public void render(Graphics g){
        for(int y = 0; y < vyska; y++){
            for(int x = 0; x < sirka; x++){
                if(bloky[x][y] != null) bloky[x][y].render(g);
            }
        }
        for(int i = 0; i < apples.size(); i++){
            apples.get(i).render(g);
        }
        for(int i = 0; i < bubaci.size(); i++){
            bubaci.get(i).render(g);
        }
        for(int i = 0; i < Hlavna.hrac.pocet_zivotov; i++){
            zivoty.get(i).render(g);
        }


    }

    public void najdi_susedov(Vrchol w){
        int pom_x = w.x;
        int pom_y = w.y;
        for(Vrchol v : Hlavna.bludisko.graf){
            if((v.x == pom_x - 1 && v.y == pom_y)
                || (v.x == pom_x + 1 && v.y == pom_y)
                || (v.x == pom_x && v.y == pom_y - 1)
                || (v.x == pom_x && v.y == pom_y + 1)){
                w.susedia.add(v);
                if(!v.susedia.contains(w)) v.susedia.add(w);
            }
        }
    }




}
