package QueenBoard;

class QueenNotActiveException extends RuntimeException {
    public QueenNotActiveException ( ) {
        super("QueenBoard.Queen is not active.");
    }
}