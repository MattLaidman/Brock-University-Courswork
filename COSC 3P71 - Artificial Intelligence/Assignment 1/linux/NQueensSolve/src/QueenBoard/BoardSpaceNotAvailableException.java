package QueenBoard;

class BoardSpaceNotAvailableException extends RuntimeException {
    public BoardSpaceNotAvailableException ( ) {
        super("Space on board is already occupied by another queen.");
    }
}
