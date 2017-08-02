class Square {

    private Piece p;

    Square (  ) {

        p = null;
    }

    // Returns true if a piece is occupying the square, false otherwise.
    boolean hasPiece ( ) {

        return p != null;
    }

    // Returns the piece that is occupying the square if it exists, null otherwise.
    Piece getPiece ( ) {

        if (hasPiece()) {
            return p;
        } else {
            return null;
        }
    }

    Piece takePiece ( ) {

        Piece temp;
        if (hasPiece()) {
            temp = p;
            p = null;
            return temp;
        } else {
            return null;
        }
    }

    // Occupies the square with the passed piece if previously unoccupied.
    void setPiece (Piece p) {

        if (!hasPiece()) {
            this.p = p;
        }
    }
}
