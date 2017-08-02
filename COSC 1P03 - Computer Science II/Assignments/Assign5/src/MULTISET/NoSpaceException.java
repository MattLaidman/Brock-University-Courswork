package MULTISET;

public class NoSpaceException extends RuntimeException {
    public NoSpaceException() {
    }
    public NoSpaceException(String text) {
        super(text);
    }
}