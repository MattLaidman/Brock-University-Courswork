package MULTISET;
public class Iterator<E extends Keyed> implements java.util.Iterator<E> {
    int size;
    E[] arr;
    int count;
    public Iterator(E[] arr, int count) {
        this.arr = arr;
        this.size = count;
        count = 0;
    }
    @SuppressWarnings("unchecked")
    public Iterator(Node<E> set, int count) {
        Keyed[] temp = new KeyedChar[count];
        int i = 0;
        Node<E> p = set;
        while (p != null) {
            temp[i] = new KeyedChar(p.item.getKey().charAt(0));
            p = p.next;
            i++;
        }
        this.arr = (E[])temp;
        this.size = count;
        count = 0;
    }
    public boolean hasNext() {
        return count < size;
    }
    public E next() {
        if (!hasNext()) throw new NoItemException();
        return arr[count++];
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}