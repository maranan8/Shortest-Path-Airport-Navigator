//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: Implementation of MaxHeapInterface that extends comparable and uses arrays.
//
//
/** A class that implements the ADT maxheap by using an array */
public class MaxHeap<T extends Comparable<? super T>> implements MaxHeapInterface<T> {
    private T[] heap; // Array of heap entries; ignore heap[0]
    private int lastIndex; // index of last entry and number of entries
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    public MaxHeap() {
        this(DEFAULT_CAPACITY); // Call next constructor
    } // end default constructor

    public MaxHeap(int initialCapacity) {
        // Is initial capacity too small?
        if (initialCapacity < DEFAULT_CAPACITY)
                initialCapacity = DEFAULT_CAPACITY;
        else // Is initial too big?
            checkCapacity(initialCapacity);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        integrityOK = true;
    } // end constructor

    public MaxHeap(T[] entries) {
        this(entries.length); // Call other constructor
        lastIndex = entries.length;
        // Assertion: integrityOK is true

        // Copy given array to data field
        for (int index = 0; index < entries.length; index++)
            heap[index + 1] = entries[index];

        // create heap
        for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
            reheap(rootIndex);
    } // end constructor

    @Override
    public void add(T newEntry) {
        checkIntegrity(); // Ensure initialization of data fields
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        } // end while

        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    } // end add

    @Override
    public T removeMax() {
        checkIntegrity(); // Ensure initialization of data fields
        T root = null;

        if(!isEmpty()) {
            root = heap[1]; // Return value
            heap[1] = heap[lastIndex]; // Form a semi heap
            lastIndex--; // Decrease size
            reheap(1); // Transform to a heap
        } // end if

        return root;
    } // end remove max

    @Override
    public T getMax() {
        checkIntegrity();
        T root = null;
        if (!isEmpty())
            root = heap[1];
        return root;
    } // end getMax

    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    } // end isEmpty

    @Override
    public int getSize() {
        return lastIndex;
    } // end getSize

    @Override
    public void clear() {
        checkIntegrity();
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        } // end while
        lastIndex = 0;
    } // end clear

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;

        while (!done && (leftChildIndex <= lastIndex)) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;

            if ((rightChildIndex <= lastIndex) &&
            heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            } // end if

            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            }
            else
                done = true;
        } // end while
        heap[rootIndex] = orphan;
    } // end reheap

    private void checkIntegrity() {
        if (!integrityOK)
            throw new SecurityException("MaxHeap is corrupt.");
    } // end checkIntegrity

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempted to create a maxheap whose capacity exceeds allowed maximum " + MAX_CAPACITY + ".");
    }

    private void ensureCapacity() {
        if (lastIndex > heap.length)
            throw new IllegalStateException("Attempted to add to a maxheap whose capacity exceeds heap length.");
    }
    
}
