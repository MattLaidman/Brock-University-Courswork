package QueenBoard;

class Queen {

    private boolean isQueen;

    public Queen ( ) {
        isQueen = false;
    } // constructor

    public void setActive ( ) { // set the active queen flag to true
        if (isQueen)
            throw new QueenAlreadyActiveException();
        else
            isQueen = true;
    }

    public void setInactive ( ) { // set the active queen flag to false
        if (isQueen)
            isQueen = false;
        else
            throw new QueenNotActiveException();
    }

    public boolean isActive ( ) { // true if queen space is active
        return isQueen;
    }
}
