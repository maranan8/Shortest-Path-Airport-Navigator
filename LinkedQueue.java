//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: An implementation of QueueInterface using Nodes.
//
//
import java.util.NoSuchElementException;


public class LinkedQueue<T> implements QueueInterface<T> {
    private Node frontNode;
    private Node backNode;

    public LinkedQueue() {
        frontNode = null;
        backNode = null;
    }
    @Override
    public void enqueue(T anEntry) {
        Node node = new Node(anEntry);
        if (frontNode == null) {
            frontNode = node;
            backNode = node;
        }
        else {
            backNode.next = node;
            backNode = node;
        }
    }

    @Override
    public T dequeue() {
        if (frontNode == null) {
            throw new NoSuchElementException();
        }
        T data = frontNode.data;
        if (frontNode == backNode){
            frontNode = null;
            backNode = null;
        }
        else {
            frontNode = frontNode.next;
        }
        return data;
    }

    @Override
    public T getFront() {
        if (frontNode == null) {
            throw new NoSuchElementException();
        }
        return frontNode.data;
    }

    @Override
    public boolean isEmpty() {
        return frontNode == null;
    }

    @Override
    public void clear() {
        while (!isEmpty())
            dequeue();
    }

    private class Node {
        private T data; // Entry to queue
        private Node next; // Link to next node

        private Node(T data) {
            this.data = data;
            this.next = null;
        } // end constuctor

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        } // end constuctor
    } // end node
}
