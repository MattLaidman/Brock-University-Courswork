package Collections;


/** This interface defines the requirements for items that are to be placed on a
 * list. The items must have a key which is a String. Key comparison is used to
 * determine a match in List.find.
 *
 * @see  List
 *
 * @author  D. Hughes
 *
 * @version 1.0  (Mar. 2011)
 *
 * new concepts: interface for specification of bounded type parameter.        */

public interface Keyed  {


    /** This method returns the key of the item.
     *
     * @return  String  the key of the item.                                      */

    public String getKey ( );


}  // Keyed