//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: Implementation of PriorityQueueInterface using a maxheap.
//
//
/** A class that implements ADT PriorityQueue by using a maxheap */
public class HeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {
    MaxHeapInterface<T> heap = new MaxHeap<>();

    @Override
    public void add(T newEntry) {
        heap.add(newEntry);
    }

    @Override
    public T remove() {
        return heap.removeMax();
    }

    @Override
    public T peek() {
        return heap.getMax();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int getSize() {
        return heap.getSize();
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
