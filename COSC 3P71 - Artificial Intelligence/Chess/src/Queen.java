class Queen extends Piece {

    Queen ( Colours c ) {

        setValue(9);
        setType(Pieces.Queen);
        setColour(c);
    }


    boolean validMove ( Coordinate from, Coordinate to ) {

        return from.getColumn() - to.getColumn() == 0 ||
                from.getRow() - to.getRow() == 0 ||
                Math.abs(from.getColumn() - to.getColumn()) == Math.abs(from.getRow() - to.getRow());
    }
}
