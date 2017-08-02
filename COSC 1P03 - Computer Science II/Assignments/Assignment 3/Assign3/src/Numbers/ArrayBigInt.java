package Numbers;


/** This class handles the BigInt data type and creats the necessary
  * methods for it.
  * 
  * @author Matt
  *
  * @version 1.0 (February 2013)			                        */

public class ArrayBigInt implements BigInt {


	private final int[] bInt = new int [100];

	private int dCount = 0;
	
	// Creates the inital value of BigInt
	
	public ArrayBigInt ( ) {
		
			this(0); // ?
				
	} // constructor
	
	// handles int data
	
	public ArrayBigInt ( int i ) {
		
		if (i <= 0) {
			throw new InvalidBigIntException ();
		} // if
		toArray(i);
		
	} // constructor
	
	// handles long data
	
	public ArrayBigInt ( long i ) {
		
		if (i <= 0) {
			throw new InvalidBigIntException ();
		} // if
		toArray(i);
		
	} // constructor
	
	
	// converts int data types to int arrays
	
	private void toArray (int i) {
		
		int c = 0;
		String temp = String.valueOf(i);
		
		while (i > Math.pow(10, c)) {
			bInt[c] = (int) temp.charAt(c);
			c++;
		}

	}
	
	// converts long data types to int arrays
	
	private void toArray (long i) {
		
		int c = 0;
		String temp = String.valueOf(i);
		
		while (i > Math.pow(10, c)) {
			bInt[c] = (int) temp.charAt(c);
			c++;
		}
		
	}
	
	// converts int array to String data type
	
	public String toString ( ) {
		
		String sVal = "";
		int c = 0;
		
		for (int j = c-1; j >= 0 ; j--) {
			sVal = sVal + bInt[j];
		} // for
		return sVal;
		
	} // toString
	
	// returns the number of digits in the BigInt variable
	
	public int getNumDigits ( ) {
		
		int c = 0;
		
		while (c > Math.pow(10, c)) {
			c++;
		}
		
		return c;
			
	} // getNumDigits
	
	// returns the digit at the index location i, or zero if no 
	// digit exists (leading zeroes)
	
	public int getDigit ( int i ) {
		
		int digit = 0;
		
		if (i == 0) {
			return digit;
		} else {
			digit = (int) this.toString().charAt(i);
			return digit;
		} // else
		
	} // getDigit
	
	// returns true if the two BigInts being compared are equal, or
	// false if they are not.
	
	public boolean equals ( BigInt val ) {
		
		if (this.toString().equals(val.toString())) {
			return true;
		} else {		
			return false;
		} // else
		
	} // equals
	
	// Compares two BigInt variables. If this < val, -1 is returned. If this == val,
	// 0 is returned, and if this > val, 1 is returned
	
	public int compareTo ( BigInt val ) {
		
		int i = dCount-1;
		int vCount = val.getNumDigits()-1;
		
		if (this.toString().equals(val.toString())) {
			return 0;
		} else {
			if (dCount < vCount) {
			return -1;
			} else {
				if (dCount > vCount) {
					return 1;
				} else {
					if (dCount == vCount) {
						while (i >= 0 | vCount >= 0) { // Reverse this. Low-order storage in array, idiot
							if (bInt[i] < val.getDigit(i)) {
								return -1;
							} // if
							if (bInt[i] > val.getDigit(i)) {
								return 1;
							} else {
								i--;
								vCount--;
							} // else
						} // while
					} // if
				} // else
			} // else
		} // else
		return 0;
	} // compareTo

	
	// Adds two big int values together and returns the total

	public BigInt add(BigInt val) {
		
		int i = 0;
		int carry = 0;
		int temp;

		while (i <= (dCount-1) | i <= (val.getNumDigits()-1)) {
			temp = bInt[i] + val.getDigit(i);
			if (temp > 9) {
				carry = 1;
				temp = temp - 10;
			}
			
			i++;
		} // while
		
		return val;
	} // add


	public BigInt sub(BigInt val) {
		return val;
	} // sub


	public BigInt mul(BigInt val) {
		return val;
	} // mul

	public BigInt div(BigInt val) {
		return val;
	} // div
	
}
	
	







