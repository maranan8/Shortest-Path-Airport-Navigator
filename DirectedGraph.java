//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: DirectedGraph that is implemented using a HashedDictionary,
//              the generic type as a key and a vertex of the generic type as the value.
//              Implements all methods from GraphInterface except getBreadthFirstTraversal.
//              getCheapestPath uses Dijkstra’s algorithm with a priorityQueue
//
import java.util.Iterator;


/** A class that implements the ADT directed graph. */
public class DirectedGraph<T> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new HashedDictionary<>(1003);
        edgeCount = 0;
    } // end default constructor

    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null; // Was addition to dictionary successful? Make sure each vertex is unique
    } // end addVertex

    @Override
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    } // end addEdge

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex, edgeWeight);
        if (result)
            edgeCount++;
        return result;
    } // end addEdge

    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if
        return found;
    } // end hasEdge

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    } // end isEmpty

    @Override
    public int getNumberOfVertices() {
        return vertices.getSize();
    } // end getNumberOfVertices

    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    } // end getNumberOfEdges

    @Override
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    } // end clear

    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        throw new UnsupportedOperationException("getBreadthFirstTraversal is not supported.");

    } // end getBreadthFirstTraversal

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();
        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
                if (nextNeighbor.equals(endVertex))
                    done = true;
            }
        }
        int pathLength = (int)endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathLength;
    }    // end getShortestPath

    @Override
    public int getCheapestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();

        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        priorityQueue.add(new EntryPQ(originVertex, 0, null));

        while (!done && !priorityQueue.isEmpty()) {
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex();

            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getWeight());
                frontVertex.setPredecessor(frontEntry.getPredecessor());

                if (frontVertex.equals(endVertex)) {
                    done = true;
                }
                else {
                    Iterator<VertexInterface<T>> neighborIterator = frontVertex.getNeighborIterator();
                    Iterator<Double> weightIterator = frontVertex.getWeightIterator();
                    while (neighborIterator.hasNext() && weightIterator.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighborIterator.next();
                        double weightOfEdgeToNeighbor = weightIterator.next();

                        if (!nextNeighbor.isVisited()) {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor, (int) nextCost, frontVertex));
                        }
                    }
                }
            }
        }
        if (done) {
            double pathCost = endVertex.getCost();
            path.push(endVertex.getLabel());
            VertexInterface<T> vertex = endVertex;
            while (vertex.hasPredecessor()) {
                vertex = vertex.getPredecessor();
                path.push(vertex.getLabel());
            }
            return (int) pathCost;
        }
        return -1;
    }

    protected void resetVertices() { // reset before finding the cheapest path
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    private class EntryPQ implements Comparable<EntryPQ>{
        private VertexInterface<T> vertex;
        private int weight;
        private VertexInterface<T> predecessor;

        EntryPQ(VertexInterface<T> vertex, int weight, VertexInterface<T> predecessor) {
            this.vertex = vertex;
            this.weight = weight;
            this.predecessor = predecessor;
        }

        private VertexInterface<T> getVertex() {
            return vertex;
        }

        private int getWeight() {
            return weight;
        }

        private VertexInterface<T> getPredecessor() {
            return predecessor;
        }

        @Override
        public int compareTo(EntryPQ rhs) {
            return (int) Math.signum((rhs.getWeight() - weight));
        }
    }
} // end DirectedGraph
