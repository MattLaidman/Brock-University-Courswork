class Coordinate {

    private int column, row;

    Coordinate ( ) {

    }

    Coordinate ( int c, int r ) {

        column = c;
        row = r;
    }

    void setColumn ( int c ) {

        column = c;
    }

    void setRow ( int r ) {

        row = r;
    }

    int getColumn ( ) {

        return column;
    }

    int getRow ( ) {

        return row;
    }
}