package Testing;

import Collections.*;
/*
 *This is a *very* minimal testing file.
 *It is NOT complete; rather, it's simply enough to verify that you can compile
 *and run with your implementation.
 *If you have time, you should expand this to make it far more complete.
 *If you have lots of extra time, you should probably intentionally test the
 *exceptions.
 */

public class TestSortedArray {
    private static void testChars(SortedArray<KeyedChar> sa,String chars) {
        for (char c:chars.toCharArray()) sa.add(new KeyedChar(c));
        for (KeyedChar kc:sa) System.out.print("\t"+kc.theChar);
        System.out.println();
    }

    //This (and its corresponding line below) won't make sense until you've created the KeyedInt type!
	/*private static void testInts(SortedArray<KeyedInt> sa,int[] data) {
		for (int i:data) sa.add(new KeyedInt(i));
		for (KeyedInt ki:sa) System.out.print("\t"+ki.theInt);
		System.out.println();
	}*/

    public static void main(String[] args) {
        int[] data={3,5,1,2,6};
        SortedArray<KeyedChar> sa=new SortedArray<KeyedChar>();
        SortedArray<KeyedChar> sb=new SortedArray<KeyedChar>();
        //SortedArray<KeyedInt> sc=new SortedArray<KeyedInt>();
        testChars(sa,"fegdf");
        testChars(sb,"xyzadc");
        sa=sa.merge(sb);
        for (KeyedChar kc:sa) System.out.print("\t"+kc.theChar);
        System.out.println();

        //testInts(sc,data);
        //sa=sa.merge(sc); //Of course, this is not legal!
    }
}