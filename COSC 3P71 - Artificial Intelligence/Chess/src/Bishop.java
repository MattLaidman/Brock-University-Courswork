class Bishop extends Piece {

    Bishop ( Colours c ) {

        setValue(3);
        setType(Pieces.Bishop);
        setColour(c);
    }

    boolean validMove ( Coordinate from, Coordinate to ) {

        return Math.abs(from.getColumn() - to.getColumn()) == Math.abs(from.getRow() - to.getRow());
    }
}
