//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: An interface for GraphADT.
//
//
/** An interface of methods providing basic operations for directed
 and undirected graphs that are either weighted or unweighted */
public interface GraphInterface<T> {
    /** Adds a given vertex to this graph.
     @param vertexLabel An object that labels the new vertex and is
        distinct from the labels of current vertices.
     @return True of the vertex is added, or false if not. */
    public boolean addVertex(T vertexLabel);

    /** Adds a weighted edge between two given distinct vertices that
     are currently in this graph. The desired edge must not already
     be in the graph. In a directed graph, the edge points towards
     the second vertex given.
     @param begin An object that labels the origin vertex of the edge.
     @param end An object, distinct from begin, that labels the end
        vertex of the edge.
     @param edgeWeight The real value of the edge's weight.
     @return True if the edge is added, or false if not. */
    public boolean addEdge(T begin, T end, double edgeWeight);

    /** Adds an unweighted edge between two given distinct vertices
     that are currently in this graph. The desired edge must not
     already be in the graph. In a directed graph, the edge points
     toward the second vertex given.
     @param begin An object that labels the origin vertex of the edge.
     @param end An object, distinct from begin, that labels the end
        vertex of the edge.
     @return True if the edge is added, or false if not. */
    public boolean addEdge(T begin, T end);

    /** Sees whether an edge exists between two given vertices.
     @param begin An object that labels the origin vertex of the edge.
     @param end An object that labels the end vertex of the edge.
     @return True if an edge exists. */
    public boolean hasEdge(T begin, T end);

    /** Sees wherter this graph is empty.
     @return True if the graph is empty. */
    public boolean isEmpty();

    /** Gets the number of vertices in this graph.
     @return The number of vertices in the graph. */
    public int getNumberOfVertices();

    /** Gets the number of edges in this graph.
     @return the number of edges in the graph */
    public int getNumberOfEdges();

    /** Removes all vertices and edges from this graph
        resulting in an empty graph. */
    public void clear();

    /** Traverses through all verticies, once all adjacent are visitied,
     then their adjacent are traveled
     @param origin the start of the traversal.
     @return a queue of the traversal. */
    public QueueInterface<T> getBreadthFirstTraversal(T origin);

    /** Algorithm to get the shortest path in a graph.
     @param begin starting vertex.
     @param end ending vertex.
     @param path a stack of the path it takes. */
    public int getShortestPath(T begin, T end, StackInterface<T> path);

    /** Algorithm to get the cheapest path in a graph,
     takes in account of the weight of the edges.
     @param begin starting vertex.
     @param end ending vertex.
     @param path a stack of the path it takes. */
    public int getCheapestPath(T begin, T end, StackInterface<T> path);
} // end BasicGraphInterface
