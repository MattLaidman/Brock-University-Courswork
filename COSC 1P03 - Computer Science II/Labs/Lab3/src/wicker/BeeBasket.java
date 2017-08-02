package wicker; //Note that this is the same package as MelissophobiaException
/*
 *This class accepts a String and, when requested, tallies up the number of characters in it.
 *Why doesn't it just use .length()? Because of reasons.
 *
 *Also note that the BeeBasket is afraid of B's (and b's), so it will throw a MelissophobiaException
 *when asked to process either of those letters.
 */

public class BeeBasket {
    String text;
    public BeeBasket(String text) {
        this.text=text;
    }

    /*Note that, if this just *happened* to be an ADT, it'd make more sense to throw the exception
     *in the constructor; as that's when it should notice things like improper data.
     *However, for this lab exercise, it isn't, so it doesn't.								*/
    public int tally() {
        int count=0;
        char[] characters=text.toCharArray();
        for (char c:characters) {
            if (c=='b') throw new MelissophobiaException();
            if (c=='B') throw new MelissophobiaException("Remember this is just a demonstration.");
            count++;
        }
        return count;
    }
}
