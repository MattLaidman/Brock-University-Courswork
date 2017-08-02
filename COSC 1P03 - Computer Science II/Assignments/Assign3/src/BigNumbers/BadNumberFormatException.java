package BigNumbers;

public class BadNumberFormatException extends RuntimeException {
    public BadNumberFormatException() {
    }
    public BadNumberFormatException(String text) {
        super(text);
    }
}