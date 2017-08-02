abstract class Piece {

    private Pieces type;
    private int value;
    private Colours colour;

    Piece setType ( Pieces p ) {

        type = p;
        return this;
    }

    Pieces getType ( ) {

        return type;
    }

    Piece setValue ( int v ) {

        value = v;
        return this;
    }

    int getValue ( ) {

        return value;
    }

    Piece setColour ( Colours c ) {

        colour = c;
        return this;
    }

    Colours getColour ( ) {

        return colour;
    }

    abstract boolean validMove ( Coordinate from, Coordinate to );

    void firstMoveMade ( boolean twice, int moves ) {}

    boolean isFirstMove ( ) { // King, Rook, and Pawn Override. Does not matter for others.

        return false;
    };

    boolean canEnPassant ( ) { // Pawn overrides, rest dont matter.

        return false;
    }

    void flipEnPassant ( ) {}

    int getEnPassantMove ( ) { //Pawn Overrides, rest dont matter.

        return 0;
    }

    void setFirstMove ( ) {}
}