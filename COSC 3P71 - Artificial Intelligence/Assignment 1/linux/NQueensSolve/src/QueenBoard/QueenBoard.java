package QueenBoard;

public class QueenBoard {

    private Queen[][] board;
    private int numQueens;
    private int boardSize;

    public QueenBoard (int size) {
        boardSize = size;
        board = new Queen[boardSize][boardSize];
        numQueens = 0;

        for (int i = 0 ; i < boardSize ; i++) {
            for (int j = 0 ; j < boardSize ; j++) {
                board[i][j] = new Queen();
            }
        }
    } // constructor

    public void placeQueen (int x, int y) { // place queen at space x,y on board
        if (board[x][y].isActive()) {
            throw new BoardSpaceNotAvailableException();
        } else {
            numQueens++;
            board[x][y].setActive();
        }
    }

    public void removeQueen (int x, int y) { // remove queen at space x,y from board
        if (board[x][y].isActive()) {
            numQueens--;
            board[x][y].setInactive();
        } else {
            throw new BoardSpaceUnusedException();
        }
    }

    public void moveQueen (int x1, int y1, int x2, int y2) { // move queen from x1,y1 to x2,y2
        if (!board[x1][y1].isActive()) {
            throw new BoardSpaceUnusedException();
        } else if (board[x2][y2].isActive()) {
            throw new BoardSpaceNotAvailableException();
        } else {
            board[x1][y1].setInactive();
            board[x2][y2].setActive();
        }
    }

    public void resetBoard ( ) { // clear all queens on board
        for (int i = 0 ; i < boardSize ; i++) {
            for (int j = 0 ; j < boardSize ; j++) {
                if (board[i][j].isActive()) {
                    board[i][j].setInactive();
                }
            }
        }
    }

    public int getBoardSize ( ) { // the size of the board object
        return boardSize;
    }

    public int getNumQueens ( ) { // return num queens currently on board
        return numQueens;
    }

    public boolean hasQueen (int x, int y) { //true if space is occupied by queen
        return board[x][y].isActive();
    }

    public boolean canAttack (int x, int y) { // returns true if queen at x,y could attack any other queen.

        int k, l;

        if (!board[x][y].isActive())
            throw new BoardSpaceUnusedException();
        else {

            // Else check all 8 directions... Maybe a better way to do
            // this on the diagonals?

            k = x + 1; // check vertically
            l = y;
            while (k != x) {
                if (board[k%boardSize][l].isActive()) {
                    return true;
                }
                k = (k+1) % boardSize;
            }
            k = x; // check horizontally
            l = y + 1;
            while (l != y) {
                if (board[k][l%boardSize].isActive()) {
                    return true;
                }
                l = (l+1) % boardSize;
            }
            k = x - 1; // check 4 diagonals
            l = y - 1;
            while (k >= 0 && l >= 0) {
                if (board[k][l].isActive()) {
                    return true;
                }
                k--;
                l--;
            }
            k = x + 1;
            l = y - 1;
            while (k < boardSize && l >= 0) {
                if (board[k][l].isActive()) {
                    return true;
                }
                k++;
                l--;
            }
            k = x + 1;
            l = y + 1;
            while (k < boardSize && l < boardSize) {
                if (board[k][l].isActive()) {
                    return true;
                }
                k++;
                l++;
            }
            k = x - 1;
            l = y + 1;
            while (k >= 0 && l < boardSize) {
                if (board[k][l].isActive()) {
                    return true;
                }
                k--;
                l++;
            }
        }
        return false;
    }

    public void printBoard ( ) { // Prints out the queens on the board to console
        System.out.println();
        System.out.print("  Y 0");
        for (int i = 1 ; i < boardSize ; i++) {
            System.out.print("   " + i);
        }
        System.out.println();
        System.out.print("X  ");
        for (int i = 0 ; i < boardSize*4-1 ; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0 ; i < boardSize ; i++) {
            System.out.print(i + " ");
            for (int j = 0 ; j < boardSize ; j++) {
                System.out.print("|");
                if (board[i][j].isActive()) {
                    System.out.print(" Q ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println("|");
            System.out.print("   ");
            for (int j = 0 ; j < boardSize*4-1 ; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }
}
