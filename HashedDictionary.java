//
// Name: Maranan, Austin
// Project: #4
// Due: 12/6/24
// Course: cs-2400-01
//
// Description: A hashed implementation of DictionaryInterface that supports all methods except remove
//
import java.util.Iterator;
import java.util.NoSuchElementException;


public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;
    private Entry<K, V>[] hashTable;
    protected final Entry<K, V> AVAILABLE = new Entry<>(null, null);
    private boolean integrityOK = false;
    
    public HashedDictionary() { // default constructor
        this(DEFAULT_CAPACITY);
    }

    public HashedDictionary(int initialCapacity) { // constructor with desired size
        checkCapacity(initialCapacity);
        numberOfEntries = 0;
        @SuppressWarnings("unchecked")
        Entry<K, V>[] temp = (Entry<K, V>[])new Entry[initialCapacity];
        hashTable = temp;
        integrityOK = true;
    }

    @Override
    public V add(K key, V value) {
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException();
        }
        else {
            V oldValue;
            int index = getHashIndex(key);
            index = linearProbe(index, key);

            if (hashTable[index] == null) {
                hashTable[index] = new Entry<>(key, value);
                numberOfEntries++;
                oldValue = null;
            }
            else {
                oldValue = hashTable[index].value;
                hashTable[index].value = value;
            }
            return oldValue;
        }
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("remove(K key) is not supported.");
    }

    @Override
    public V getValue(K key) {
        checkIntegrity();
        V result = null;
        int index = getHashIndex(key);
        index = linearProbe(index, key);

        if (hashTable[index] != null) {
            result = hashTable[index].value;
        }
        return result;
    }

    @Override
    public boolean contains(K key) {
        checkIntegrity();
        int index = getHashIndex(key);
        index = linearProbe(index, key);

        return (hashTable[index] != null && hashTable[index] != AVAILABLE && hashTable[index].key.equals(key));
    }

    @Override
    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    }

    @Override
    public Iterator<V> getValueIterator() {
        return new ValueIterator();
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public int getSize() {
        return numberOfEntries;
    }

    @Override
    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        numberOfEntries = 0;
    }

    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % hashTable.length;
        if (hashIndex < 0)
            hashIndex = hashIndex + hashTable.length;
        return hashIndex;
    }

    private int linearProbe(int index, K key) {
        boolean found = false;
        while (!found && hashTable[index] != null) {
            if (key.equals(hashTable[index].key)) {
                found = true;
            }
            else {
                index = (index + 1) % hashTable.length;
            }
        }
        return index;
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("HashedDictionary is corrupt.");
        }
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Max capacity exceeded.");
    }

    private class KeyIterator implements Iterator<K> {
        private int currentIndex;
        private int numberLeft;
        
        private KeyIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
        }

        public boolean hasNext() {
            return numberLeft > 0;
        }

        public K next() {
            K result = null;
            if (hasNext()) {
                while ((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE) {
                    currentIndex++;
                }
                result = hashTable[currentIndex].key;
                numberLeft--;
                currentIndex++;
            }
            else {
                throw new NoSuchElementException();
            }
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ValueIterator implements Iterator<V> {
        private int currentIndex;
        private int numberLeft;

        private ValueIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
        }

        public boolean hasNext() {
            return numberLeft > 0;
        }

        public V next() {
            V result = null;
            if (hasNext()) {
                while ((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE) {
                    currentIndex++;
                }
                result = hashTable[currentIndex].value;
                numberLeft--;
                currentIndex++;
            }
            else {
                throw new NoSuchElementException();
            }
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("hiding")
    private class Entry<K, V> {
        private K key;
        private V value;

        private Entry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
        }
    }
}
