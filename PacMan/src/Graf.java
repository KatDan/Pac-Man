import java.util.ArrayList;
import java.util.List;

public class Graf {
    public Graf(){
        fronta = new Node();
        f_aktual = fronta;
        vysledok = new Node();
        graf = new ArrayList<>();
    }

    public Node fronta;
    public Node f_aktual;
    public Node vysledok;
    public List<Vrchol> graf;
    public int i;
    public Node domov;

    public Vrchol bfs(Vrchol zaciatok, Vrchol koniec)
    {
        //if(zaciatok == koniec) return koniec;
        vysledok = new Node();
        //pridaj v0 do fronty
        zaciatok.najdeny = true;
        pridaj_do_fronty(zaciatok);

        while(fronta.next != null)
        {
            Vrchol v = odober_z_fronty();
            if (v == koniec) pridaj_do_zasobnika(v);
            for(Vrchol w : v.susedia)
            {
                if (w.najdeny == false)
                {
                    w.najdeny = true;
                    w.p = v;
                    pridaj_do_fronty(w);
                }
            }
        }
        spracuj_vysledok(zaciatok, koniec);
        odober_zo_zasobnika();
        for(Vrchol v : graf) v.najdeny = false;
        return odober_zo_zasobnika();

    }


    void pridaj_do_fronty(Vrchol v)
    {
        Node pom = new Node(v);
        f_aktual.next = pom;
        f_aktual = pom;
    }

    Vrchol odober_z_fronty()
    {
        Node pom = fronta.next;
        fronta.next = fronta.next.next;
        if (fronta.next == null) f_aktual = fronta;
        return pom.vrchol;

    }

    void pridaj_do_zasobnika(Vrchol vrchol)
    {
        Node pom = new Node(vrchol);
        pom.next = vysledok.next;
        vysledok.next = pom;
    }

    public Vrchol odober_zo_zasobnika(){
        Vrchol pom = vysledok.vrchol;
        vysledok = vysledok.next;
        return pom;
    }


    public void spracuj_vysledok(Vrchol zac, Vrchol kon){
        Vrchol v = kon;
        while(v != zac && v.p != null){
            //System.out.println(v.x+" "+v.y+"->");
            pridaj_do_zasobnika(v);
            v = v.p;
        }
        //System.out.println("______________________-");
    }

}
