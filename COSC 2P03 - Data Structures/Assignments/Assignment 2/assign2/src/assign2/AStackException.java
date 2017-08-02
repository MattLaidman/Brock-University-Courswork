package assign2;

/**
 * AStackException class is thrown if stack overflow or underflow occurs in the AStack class.
 *
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 1, 2014)
 */

public class AStackException extends RuntimeException {
    public AStackException (String message) {
        super(message);
    }
}