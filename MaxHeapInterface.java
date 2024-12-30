//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: An interface for maxheapADT.
//
//
/** An interface for the ADT maxheap */
public interface MaxHeapInterface<T extends Comparable<? super T>> {
    /** Adds a new entry to this heap. 
    @param newEntry An object to be added. */
    public void add(T newEntry);

    /** Removes and returns the largest item in this heap.
    @return Either largest item in the heap or,
        if the heap is empty before the operation, null. */
    public T removeMax();

    /** Retreives teh largest item in this heap.
    @return Either largest item in the heap or,
        if the heap is empty, null. */
    public T getMax();

    /** Detects whether this heap is empty.
    @return True if heap is empty, or false otherwise. */
    public boolean isEmpty();

    /** Gets the size of this heap.
    @return The number of entries currently in the heap. */
    public int getSize();

    /** Removes all entries from this heap. */
    public void clear();
} // end MaxHeapInterface