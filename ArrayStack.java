//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: An implementation of StackInterface using arrays.
//
//
import java.util.Arrays;
import java.util.EmptyStackException;


public class ArrayStack<T> implements StackInterface<T> {
    private T[] stack;
    private int tos;
    private boolean integrityOK;
    private static final int DEFAULT_SIZE = 50;
    private static final int MAX_CAPACITY = 10000;


    public ArrayStack() {
        this(DEFAULT_SIZE);
        tos = -1;
    }

    public ArrayStack(int desiredSize) {
        integrityOK = false;
        checkCapacity(desiredSize);
        @SuppressWarnings("unchecked")
        T[] tempStack = (T[]) new Object[desiredSize];
        stack = tempStack;
        tos = -1;
        integrityOK = true;
    }

    @Override
    public void push(T newEntry) {
        checkIntegrity();
        ensureCapacity();
        stack[++tos] = newEntry;
    }

    @Override
    public T pop() {
        checkIntegrity();
        if (tos >= 0) {
            T result = stack[tos];
            stack[tos] = null;
            tos--;
            return result;
        }
        else {
            throw new EmptyStackException();
        }
    }

    @Override
    public T peek() {
        checkIntegrity();
        if (tos >= 0)
            return stack[tos];
        else
            throw new EmptyStackException();
    }

    @Override
    public boolean isEmpty() {
        return tos == -1;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("ArrayStack is corrupt.");
        }
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Max capacity exceeded.");
        }
    }

    private void ensureCapacity() {
        if (tos >= stack.length - 1) {
            int newLength = 2 * stack.length;
            checkCapacity(newLength);
            stack = Arrays.copyOf(stack, newLength);
        }
    }
}
