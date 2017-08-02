package Numbers;


/** This interface describes an abstract data type BigInt which represents
  * non-negative integers with an arbitrary number of digits (up to a possible
  * limit specified by the implementation class). BigInt objects are immutable.
  * 
  * @author  D. Hughes
  * 
  * @version 1.0 (Feb. 2013)                                                     */

public interface BigInt {
    
    
    /** This method returns the number of digits in the number. This count does
      * not include leading zeros, except for the value 0 which is considered to
      * have 1 digit.                                                            */
    
    public int getNumDigits ( );
    
    
    /** This method returns the digit at ordinal position i. The ordinal position
      * in the number is the power of ten representing that digit position. That
      * is the digit at ordinal position i is the digit in the 10^i position.
      * Ordinal position 0 is the ones (10^0) position, ordinal position 1 is the
      * tens (10^1) position, etc. If i>=this.getNumDigits() the method returns
      * zero (i.e. returns leading zeros).
      * 
      * @param  i  the ordinal position for the digit                            */
    
    public int getDigit ( int i );
    
    
    /** This method returns true if this and val are arithmetically equal.
      * 
      * @param  val  the comparator
      * 
      * @return  boolean  this == val                                            */
    
    public boolean equals ( BigInt val );
    
    
    /** This method returns a negative value if this < val, 0 if this == val, and
      * a positive value if this > val.
      * 
      * @param  val  the comparator
      * 
      * @return  int  negative (this<val), 0 (this==val), positive (this>val)    */
    
    public int compareTo ( BigInt val );
    
    
    /** This method returns the sum of this and val.
      * 
      * @param  val  the addend
      * 
      * @return  BigInt  this + val                                              */
    
    public BigInt add ( BigInt val );
    
    
    /** This method returns the difference of this and val.
      * 
      * @param  val  the subtrahend
      * 
      * @return  BigInt  this - val                                              */
    
   
    public BigInt sub ( BigInt val );
    
    
    /** This method returns the product of this and val.
      * 
      * @param  val  the multiplier
      * 
      * @return  BigInt  this * val                                              */
    
    public BigInt mul ( BigInt val );
    
    
    /** This method returns the integral quotient of this and val.
      * 
      * @param  val  the divisor
      * 
      * @return  BigInt  this / val                                              */
    
    public BigInt div ( BigInt val );
    
    
}  // BigInt