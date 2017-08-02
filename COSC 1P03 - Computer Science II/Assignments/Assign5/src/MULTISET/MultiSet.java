package MULTISET;

public interface MultiSet<E extends Keyed> extends Iterable<E> {

    // returns the number of elements in the collection
    public int cardinality();

    // returns the number of elements that match item from this
    public int multiplicity(E item);

    // Adds anItem to the collection; note this is a mutable operation
    public void add(E anItem);

    // returns true if this is empty
    public Boolean isEmpty();

    // returns a new MultiSet by taking the union of this and aSet,
    // the operation is immutable, neither this or aSet is modified
    public MultiSet<E> union(MultiSet<E> aSet);

    // returns true if this contains the same elements as aSet
    public Boolean equal(MultiSet<E> aSet);
    // returns a new MultiSet by taking the intersection of this and aSet,
    // the operation is immutable, neither this or aSet is modified
    // the operation can be views as min(|S|,|T|)
    public MultiSet<E> intersection (MultiSet<E> aSet);

    // Returns an interator over the collection this.
    public Iterator<E> iterator();
}
// MultiSet
