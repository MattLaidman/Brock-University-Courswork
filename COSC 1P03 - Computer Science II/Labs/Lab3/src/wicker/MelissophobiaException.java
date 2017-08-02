package wicker; //Note that this should be the same package as BeeBasket
/*
 *This class represents an error condition: B's are scary, and the program shall never
 *accept them!
 */

public class MelissophobiaException extends RuntimeException {
    //Note that I could have used constructor-chaining here, left this one out, or simply used a
    //	null message
    public MelissophobiaException() {
        super("Not the bees! ahhh! Not the bees!");
    }

    public MelissophobiaException(String msg) {
        super(msg);
    }
}
