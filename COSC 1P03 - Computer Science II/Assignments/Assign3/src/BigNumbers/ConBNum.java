package BigNumbers;

/**
 * This class is an implementation of the BNum interface using a contiguous array.
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */

public class ConBNum implements BNum {

    private int[] aNum;


    public ConBNum ( ) { // default constructor creates ConBNum object with value 0
        this(0);
    }


    public ConBNum (long n) { // converts long to int array
        toArray(String.valueOf(n));
    }


    public ConBNum (String n) { // converts string to int array
        try {
            toArray(n);
        } catch (Exception E) {
            throw new BadNumberFormatException("Invalid number format");
        }
    }


    public void toArray (String n) { // converts given string to int array
        n = checkSign(n);
        aNum = new int[n.length()];
        for (int i = 0 ; i < n.length() ; i++) {
            aNum[i] = Integer.parseInt(String.valueOf(n.charAt(i)));
        }
    }


    public String toString ( ) { // returns string representation of 'this'
        String t = "";
        for (int i : aNum) {
            t = t + i;
        }
        return t;
    }


    public BNum clone ( ) { // returns a clone of 'this'
        if (aNum[0] == 0) {
            return new ConBNum(this.toString());
        } else {
            return new ConBNum('-' + this.toString().substring(1));
        }
    }


    public boolean equals (BNum n) { // returns 'this' == n
        return this.toString().equals(n.toString());
    }


    public boolean lessThan (BNum n) { // returns 'this' < n
        if (aNum[0] ==  0 & n.getSign() == 0) { // if both positive
            if (aNum.length != n.toString().length()) { // if different lengths
                return aNum.length < n.toString().length(); // true if 'this' shorter than n
            } else { // if same length
                for (int i = 1 ; i < aNum.length ; i++) {
                    if (aNum[i] != n.getDigit(i)) { // if digits from left to right aren't equal
                        return aNum[i] < n.getDigit(i); // true if 'this' digit less than n digit
                    }
                }
                return false;
            }
        } else if (aNum[0] == 1 & n.getSign() == 1) { // if both negative
            if (aNum.length != n.toString().length()) { // if different lengths
                return aNum.length > n.toString().length(); // true if 'this' longer than n
            } else { // if same length
                for (int i = 1 ; i < aNum.length ; i++) {
                    if (aNum[i] != n.getDigit(i)) { // if digits from left to right aren't equal
                        return aNum[i] > n.getDigit(i); // true if 'this' digit greater than n digit
                    }
                }
                return false;
            }
        } else { // if different signs
            return aNum[0] > n.getSign(); // true if 'this' negative and n positive
        }
    }


    public BNum add (BNum n){ // returns 'this' + n
        if (aNum[0] == n.getSign()) { // if same sign
            if (aNum[0] == 0) { // both are positive
                return new ConBNum(doAdd(this, n));
            } else { // both are negative
                return new ConBNum('-' + doAdd(this, n));
            }
        } else { // if different signs
            if (new ConBNum(this.toString().substring(1)).lessThan(new ConBNum(n.toString().substring(1)))) { // |this| < |n|
                if (n.getDigit(0) == 1) { // if n is negative
                    return new ConBNum('-' + doSub(n, this));
                } else { // if n is positive
                    return new ConBNum(doSub(n, this));
                }
            } else { // |n| < |this|
                if (aNum[0] == 1) { // if 'this' is negative
                    return new ConBNum('-' + doSub(this, n));
                } else { // if 'this' is positive
                    return new ConBNum(doSub(this, n));
                }
            }
        }
    }


    private String doAdd (BNum t, BNum n) { // adds the two given BNums
        int a = t.toString().length()-1;
        int b = n.toString().length()-1;
        int carry = 0;
        int sum;
        String total = "";
        while (a > 0 | b > 0) { // while both have digits
            if (a <= 0) { // if t has no more digits
                sum = n.getDigit(b) + carry;
            } else if (b <= 0) { // if n has no more digits
                sum = t.getDigit(a) + carry;
            } else { // if both still have digits
                sum = t.getDigit(a) + n.getDigit(b) + carry;
            }
            if (sum > 9) {
                sum = sum % 10;
                carry = 1;
            } else {
                carry = 0;
            }
            total = total + sum;
            a--;
            b--;
        }
        if (carry == 1) {
            return 1 +  reverse(total);
        } else {
            return reverse(total);

        }
    }


    public BNum sub (BNum n){ // returns 'this' - n
        if (aNum[0] == n.getSign()) { // same sign
            if (!new ConBNum(this.toString().substring(1)).lessThan(new ConBNum(n.toString().substring(1)))) { // |this| > |n|
                if (aNum[0] == 0) { // both are positive
                    return new ConBNum(doSub(this, n));
                } else { // both are negative
                    return new ConBNum('-' + doSub(this, n));
                }
            } else { // |this| < |n|
                if (aNum[0] == 0) { // both are positive
                    return new ConBNum('-' + doSub(n, this));
                } else { // both are negative
                    return new ConBNum(doSub(n, this));
                }
            }
        } else { // different signs
            if (this.lessThan(n)) { // negative sub positive
                return new ConBNum('-' + doAdd(this, n));
            } else { // positive sub negative
                return new ConBNum(doAdd(this, n));
            }
        }
    }


    private String doSub (BNum t, BNum n) { // subtracts the two given BNums
        int a = t.toString().length()-1;
        int b = n.toString().length()-1;
        int carry = 0;
        int diff;
        String total = "";
        while (a > 0 | b > 0) { // while both have digits
            if (a <= 0) { // if t has no more digits
                diff = n.getDigit(b) - carry;
            } else if ( b <= 0) { // if n has no more digits
                diff = t.getDigit(a) - carry;
            } else { // if both have digits
                diff = t.getDigit(a) - n.getDigit(b) - carry;
            }
            if (diff < 0) {
                diff = 10 + diff;
                carry = 1;
            } else {
                carry = 0;
            }
            total = total + diff;
            a--;
            b--;
        }
        return reverse(total);
    }


    public int getSign() { // returns sign of BNum
        return aNum[0];
    }


    public int getDigit(int i) { // returns digit at index i, 0 is LSD
        try {
            return aNum[i];
        } catch (Exception E) {
            throw new DigitOutOfRangeException("Index out of range");
        }
    }


    private String reverse (String n) { // returns the reverse of the passed string
        String rTotal = "";
        for (int i = n.length()-1 ; i >= 0 ; i--) {
            rTotal = rTotal + n.charAt(i);
        }
        return rTotal;
    }


    private String checkSign (String n) { // checks the given string and applies appropriate LSD if necessary
        if (n.charAt(0) == '-') {
            return 1 + n.substring(1);
        } else if (n.charAt(0) == '0') {
            return n;
        } else {
            return 0 + n;
        }
    }
}