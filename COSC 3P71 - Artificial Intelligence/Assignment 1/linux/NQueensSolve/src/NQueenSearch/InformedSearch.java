package NQueenSearch;

import QueenBoard.QueenBoard;
import java.util.Random;

/**
 * InformedSearch class performs a search  on an empty QueenBoard object by
 * by placing n queens into n separate columns, each into a random row based
 * on a given seed. If no seed is given, 34567 is used.
 *
 * The search then checks the possible places, states,  the queen could
 * possibly move to in it's column. Each state is ranked based on the
 * amount of conflicts between queens that would exist if the queen was
 * moved there. The queen is then moved to the best spot in it's column.
 * This is repeated for all n queens.
 *
 * This is repeated until there are no conflicts between queens on the
 * board, ie a solution is found.
 */

public class InformedSearch {

    private int configsTested;
    private Coord[] coords;

    public InformedSearch ( ) {
        configsTested = 0;
    }

    // default constructor calls self with default seed 34567.
    public void doInformedSearch (QueenBoard board) {
        doInformedSearch(board, 34567);
    }

    //initializes the board by placing the n queens into their own
    // columns in a random row. then calls the search function.
    public void doInformedSearch (QueenBoard board, long seed) {
        int randRow;
        Random rnd = new Random(seed);
        int size = board.getBoardSize();
        coords = new Coord[size];


        board.resetBoard();
        // place n queens each into separate columns in a random row
        for (int i = 0 ; i < size ; i++) {
            randRow = Math.abs(rnd.nextInt()%size);
            board.placeQueen(randRow, i);
            coords[i] = new Coord(randRow, i); // keep track of location
        }
        configsTested++;
        InfSearch(board);
        System.out.println("Total number of configurations tested: " + configsTested);
        System.out.println("Seed used for random number generator: " + seed);
        configsTested = 0;
    }

    // Tail recursive informed search.
    // Moves each queen to the best spot in their column then
    // loops. Prints out first solution and exits.
    private void InfSearch (QueenBoard board) {

        int size = board.getBoardSize();
        int startRow, j, s;

        while (getNumConflicts(board) != 0) { //if no conflicts, then solution!
            for (int i = 0 ; i < size ; i++) { // for each column
                State[] states = new State[size - 1]; // init potential states
                for (int k = 0 ; k < states.length ; k++) {
                    states[k] = new State(-1,-1,-1);
                }
                s = 0;
                startRow = coords[i].x;
                j = (startRow+1)%size;
                while (j != startRow) { // for each potential move in column
                    board.moveQueen(coords[i].x, coords[i].y, j, coords[i].y); // "peek at move"
                    states[s].numConflicts = getNumConflicts(board); // keep track of coords and rank
                    states[s].coord.x = j;
                    states[s].coord.y = coords[i].y;
                    s++;
                    board.moveQueen(j, coords[i].y, coords[i].x, coords[i].y); // un-"peek"
                    j = (j+1)%size;
                }
                stateSort(states); // sort states, move to best
                board.moveQueen(coords[i].x, coords[i].y, states[0].coord.x, states[0].coord.y);
                coords[i] = new Coord(states[0].coord.x, states[0].coord.y);
                configsTested++;
            }
        }
        board.printBoard();
    }

    //Insertion sort... n is small (8ish) so A-OK!
    public static State[] stateSort (State[] states){

        State temp;
        for (int i = 0; i < states.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(states[j].numConflicts < states[j-1].numConflicts){
                    temp = states[j];
                    states[j] = states[j-1];
                    states[j-1] = temp;
                }
            }
        }
        return states;
    }

    // Returns the number of queens that are in a conflict
    private int getNumConflicts (QueenBoard board) {

        int size = board.getBoardSize();
        int numConflicts = 0;

        for (int i = 0 ; i < size; i++) { // for each queen check if can attack  any others
            for (int j = 0; j < size; j++) {
                if (board.hasQueen(i, j)) {
                    if (board.canAttack(i, j)) {
                        numConflicts++;
                    }
                }
            }
        }
        return numConflicts;
    }
}

class Coord { // Simple x,y coordinates class
    public int x = 0, y = 0;
    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
// "state", represented by a coordinate and # conflicts
class State { // used to rank potential moves for a queen
    public int numConflicts;
    public Coord coord;
    public State (int numConflicts, int x, int y) {
        this.numConflicts = numConflicts;
        coord = new Coord(x, y);
    }
}

