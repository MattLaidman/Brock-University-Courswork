class Move {

    private Coordinate from, to;

    Move ( Coordinate f, Coordinate t ) {

        from = f;
        to = t;
    }

    Coordinate getFrom ( ) {

        return from;
    }

    Coordinate getTo ( ) {

        return to;
    }
}
