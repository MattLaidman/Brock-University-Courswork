package Collections;
import java.util.Iterator;

/**
 * This program...
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */
public class SortedArrayIterator<E extends Keyed> implements Iterator<E> {
    E[] arr;
    int size;
    int count;

    public SortedArrayIterator(E[] arr, int count) {
        this.arr = arr;
        this.size = count;
        count = 0;
    }


    @Override
    public boolean hasNext() {
        return count < size;
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoItemException();
        return arr[count++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}