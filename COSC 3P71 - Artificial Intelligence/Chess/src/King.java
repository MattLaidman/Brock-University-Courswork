class King extends Piece {

    private boolean firstMove;

    King ( Colours c ) {

        firstMove = true;
        setValue(200);
        setType(Pieces.King);
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

        if (isFirstMove()) {
            if (from.getRow() == to.getRow() && Math.abs(from.getColumn() - to.getColumn()) == 2) {
                    return true;
            }
        }
        return (Math.abs(from.getColumn() - to.getColumn()) == 1) && (Math.abs(from.getRow() - to.getRow()) == 1) ||
                (Math.abs(from.getColumn() - to.getColumn()) == 1) && (from.getRow() - to.getRow() == 0) ||
                (from.getColumn() - to.getColumn() == 0) && (Math.abs(from.getRow() - to.getRow()) == 1);
    }
}
