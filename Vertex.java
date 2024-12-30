//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: Implementation of VertexInterface that uses ArrayList to hold the edges that connect the vertcies.
//
//
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/** A class of vertices for a graph. */
public class Vertex<T> implements VertexInterface<T> {
    private T label;
    private List<Edge> edgeList; // Edges to neighbors
    private boolean visited; // True if visited
    private VertexInterface<T> previousVertex; // On path to this vertex
    private double cost; // Of path to this vertex

    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new ArrayList<>(); // can use arraylist from java
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor

    @Override
    public T getLabel() {
        return label;
    } // end getLabel

    @Override
    public void visit() {
        visited = true;
    } // end visit

    @Override
    public void unvisit() {
        visited = false;
    } // end unvisit

    @Override
    public boolean isVisited() {
        return visited;
    } // end isVisited

    @Override
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;

        if (!this.equals(endVertex)) {
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;

            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            } // end while

            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if
        return result;
    } // end connect

    @Override
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    } // end connect

    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    } // end getNeighborIterator

    @Override
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }

    @Override
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    } // end hasNeighbor

    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while ( neighbors.hasNext() && (result == null)) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while

        return result;
    } // end getUnvisitedNeighbor

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {
        cost = newCost;
    } // end setCost

    @Override
    public double getCost() {
        return cost;
    } // end getCost

    @Override
    public boolean equals(Object other) {
        boolean result;
        if ((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {  // The cast is safe within this else clause
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        } // end if
        return result;
    } // end equals

    private class NeighborIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;

        private NeighborIterator() {
            edges = edgeList.iterator();
        } // end default constructor

        public boolean hasNext() {
            return edges.hasNext();
        } // end hasNext

        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();

            return nextNeighbor;
        } // end next
    }

    private class WeightIterator implements Iterator<Double> {
        private Iterator<Edge> edges;

        private WeightIterator() {
            edges = edgeList.iterator();
        }

        @Override
        public boolean hasNext() {
            return edges.hasNext();
        }

        @Override
        public Double next() {
            if (edges.hasNext()) {
                return edges.next().getWeight();
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }

    protected class Edge {
        private VertexInterface<T> vertex; // Vertex at end of edge
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor

        protected Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
            weight = 0;
        } // end constructor

        protected VertexInterface<T> getEndVertex() {
            return vertex;
        } // end getEndVertex

        protected double getWeight() {
            return weight;
        } // end getWeight
    } // end Edge
} // end Vertex
