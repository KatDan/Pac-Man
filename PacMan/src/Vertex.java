import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public int x;
    public int y;
    public List<Vertex> neighbors;
    public Vertex p;
    public boolean discovered;

    public Vertex(int xx, int yy)
    {
        x = xx;
        y= yy;
        neighbors = new ArrayList<>();
        p = null;
        discovered = false;
    }
}


