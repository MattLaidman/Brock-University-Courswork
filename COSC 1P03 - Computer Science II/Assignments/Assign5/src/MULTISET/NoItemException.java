package MULTISET;
public class NoItemException extends RuntimeException {
    public NoItemException() {
    }
    public NoItemException(String text) {
        super(text);
    }
}