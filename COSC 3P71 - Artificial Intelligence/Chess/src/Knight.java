class Knight extends Piece {

    Knight ( Colours c ) {

        setValue(3);
        setType(Pieces.Knight);
        setColour(c);
    }

    boolean validMove ( Coordinate from, Coordinate to ) {

        return (Math.abs(from.getColumn() - to.getColumn()) == 2 && Math.abs(from.getRow() - to.getRow()) == 1) ||
                (Math.abs(from.getColumn() - to.getColumn()) == 1 && Math.abs(from.getRow() - to.getRow()) == 2);
    }
}
