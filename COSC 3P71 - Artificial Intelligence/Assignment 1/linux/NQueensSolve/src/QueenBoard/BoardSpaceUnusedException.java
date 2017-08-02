package QueenBoard;

class BoardSpaceUnusedException extends RuntimeException {
    public BoardSpaceUnusedException ( ) {
        super("No queen to remove from space on board.");
    }
}
