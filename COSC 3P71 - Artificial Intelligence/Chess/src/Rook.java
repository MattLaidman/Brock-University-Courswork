class Rook extends Piece {

    private boolean firstMove;

    Rook ( Colours c ) {

        firstMove = true;
        setValue(5);
        setType(Pieces.Rook);
        setColour(c);
    }

    @Override
    boolean isFirstMove ( ) {

        return firstMove;
    }

    @Override
    void firstMoveMade ( boolean twice,  int moves ) {

        firstMove = false;
    }

    @Override
    void setFirstMove ( ) {

        firstMove = true;
    }

    boolean validMove ( Coordinate from, Coordinate to ) {


        return from.getColumn() == to.getColumn() ||
                from.getRow() == to.getRow();
    }
}
