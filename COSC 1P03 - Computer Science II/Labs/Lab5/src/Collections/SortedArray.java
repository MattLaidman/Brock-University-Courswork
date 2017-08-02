package Collections;

import java.util.*;
/*
 *This represents one of the simplest possible ADTs. It's little more than an array;
 *it simply assumes that all insertions should be sorted.
 *Note that no mechanism for removing is included. If you have time, feel free
 *to add one (it'd be incredibly easy to do).
 */

public class SortedArray<E extends Keyed> implements Iterable<E> {
    private E[] theArray;
    private int count;

    //You should recognize Constructor Chaining by now
    public SortedArray() {
        this(100);
    }

    @SuppressWarnings("unchecked")
    public SortedArray(int capacity) {
        theArray = (E[]) new Keyed[capacity];
        count = 0;
    }

    @SuppressWarnings("unchecked")
    private SortedArray(E[] data) {
        theArray = (E[]) new Keyed[2*data.length];
        for (int i = 0 ; i < data.length ; i++) {
            theArray[i] = data[i];
        }
        count = data.length;
    }

    /*Addition is simple: If there's no room, throw an exception.
     *If there *is* room, find where the new element should go, and push everything at (and after)
     *that point back by one space. Then, place the element in the space you just made!
     */
    public void add(E item) {
        int t, c;
        if (count >= this.getSize()) {
            throw new NoSpaceException();
        }

        //We need to find *where* in the array this goes!
        //Once you've found where to put it, you'll have to shift everything at that point and onwards back one

        if (count == 0) {
            theArray[0] = item;
            count++;
        } else {
            c = count-1;
            while (c > 0 && item.getKey().compareTo(theArray[c].getKey()) > 0) {
                c--;
            }

            for (int i = count-1 ; i >= c ; i--) {
                theArray[i+1] = theArray[i];
            }
            theArray[c] = item;
            count++;

        }

        for (int i = 0 ; i < count ; i++) {
            System.out.println(theArray[i].getKey());
        }
        System.out.println();
    }

    //Of course we need one of these
    public int getSize() {
        return theArray.length;
    }
	
	/*The easiest way to merge is to simply create a 'right-sized array' with all of the items from
	 *BOTH structures (don't worry about staying sorted yet), and then offloading to a private
	 *constructor that can handle allocating the appropriate amount of space, and redoing the
	 *sorted inserts.
	 *Of course, if you're creating another array, that's another compiler warning to suppress...
	 */

    public SortedArray<E> merge(SortedArray<E> other) {
        //Code goes here and stuff
        return null; //(just a placeholder so it can compile)
    }

    //Simply create a new iterator.
    //Make sure you provide a reference to this class's data *somehow*
    //Also, make sure it knows the correct number of elements!
    public Iterator<E> iterator() {
        return null; //This is just another stub
    }

}