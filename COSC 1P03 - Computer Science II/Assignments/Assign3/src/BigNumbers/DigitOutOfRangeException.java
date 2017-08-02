package BigNumbers;

public class DigitOutOfRangeException extends RuntimeException {
    public DigitOutOfRangeException() {
    }
    public DigitOutOfRangeException(String text) {
        super(text);
    }
}