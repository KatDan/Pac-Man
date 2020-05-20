package cz.cuni.mff.dancejova.graph;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides BFS algorithm for ghosts to find the shortest path to Pac-Man
 */
public class Graph {
    public Graph(){
        queue = new Node();
        q_current = queue;
        result = new Node();
        graph = new ArrayList<>();
    }

    public Node queue;
    public Node q_current;
    public Node result;
    public List<Vertex> graph;


    /**
     * Finds the shortest path from start to end.
     *
     * @param start Vertex that is our starting position
     * @param end Vertex we want to get to
     * @return Vertex that is neighbor of start and leads to end
     */
    public Vertex bfs(Vertex start, Vertex end)
    {
        result = new Node();

        //pridaj v0 do fronty
        start.discovered = true;
        add_to_queue(start);

        while(queue.next != null)
        {
            Vertex v = remove_from_queue();
            if (v == end) add_to_stack(v);
            for(Vertex w : v.neighbors)
            {
                if (w.discovered == false)
                {
                    w.discovered = true;
                    w.p = v;
                    add_to_queue(w);
                }
            }
        }
        process_result(start, end);
        remove_from_stack();
        for(Vertex v : graph) v.discovered = false;
        return remove_from_stack();
    }


    void add_to_queue(Vertex v)
    {
        Node pom = new Node(v);
        q_current.next = pom;
        q_current = pom;
    }

    Vertex remove_from_queue()
    {
        Node pom = queue.next;
        queue.next = queue.next.next;
        if (queue.next == null) q_current = queue;
        return pom.vertex;
    }

    void add_to_stack(Vertex vertex)
    {
        Node pom = new Node(vertex);
        pom.next = result.next;
        result.next = pom;
    }

    Vertex remove_from_stack(){
        Vertex pom = result.vertex;
        result = result.next;
        return pom;
    }


    void process_result(Vertex start, Vertex end){
        Vertex v = end;
        while(v != start && v.p != null){
            add_to_stack(v);
            v = v.p;
        }
    }
}
