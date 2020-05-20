package cz.cuni.mff.dancejova.graph;

/**
 * A class that provides stack and queue in a graph
 */
public class Node{
    public Vertex vertex;
    public Node next;

    public Node(Vertex c)
    {
        vertex = c;
        next = null;
    }

    public Node()
    {
        vertex = null;
        next = null;
    }

}
