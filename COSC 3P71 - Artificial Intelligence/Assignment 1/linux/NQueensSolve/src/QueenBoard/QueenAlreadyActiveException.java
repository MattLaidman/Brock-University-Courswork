package QueenBoard;

class QueenAlreadyActiveException extends RuntimeException {
    public QueenAlreadyActiveException ( ) {
        super("QueenBoard.Queen is already active.");
    }
}
