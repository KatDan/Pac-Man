import java.util.ArrayList;
import java.util.List;

public class Vrchol {
    public int x;
    public int y;
    //public int h;
    public List<Vrchol> susedia;
    public Vrchol p;
    public boolean najdeny;

    public Vrchol(int xx, int yy)
    {
        x = xx;
        y= yy;
        //h = 1000000;
        susedia = new ArrayList<>();
        p = null;
        najdeny = false;
    }


}


