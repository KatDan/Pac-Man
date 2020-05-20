package cz.cuni.mff.dancejova.graph;

import java.util.ArrayList;
import java.util.List;


/**
 *This class defines a vertex in a graph.
 */
public class Vertex {
    public int x;
    public int y;
    public List<Vertex> neighbors;
    public Vertex p;
    public boolean discovered;

    /** Constructs a vertex
     *
     * @param xx The horizontal coordinate of vertex
     * @param yy The vertical coordinate of vertex
     * @see Graph
     * @see Node
     */
    public Vertex(int xx, int yy)
    {
        x = xx;
        y= yy;
        neighbors = new ArrayList<>();
        p = null;
        discovered = false;
    }
}


