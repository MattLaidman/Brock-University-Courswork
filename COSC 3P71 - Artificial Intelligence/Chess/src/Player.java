import java.util.LinkedList;
import java.util.List;

// Player class is the AI for the chess game.

// getMove will analyze the board and determine the best move to make based on
// Minimax algorithm that is implemented with alpha-beta pruning.

// The evaluation function used to determine the moves to make checks the material value
// of all the pieces on a given board board configuration so that moves leading to taking
// or protecting better pieces are taken. The evaluation function also takes into account
// the number of moves available to both the player and the AI, this mobility factor
// increases as the game progresses

class Player {

    private Colours colour = null;
    Players type = null;

    private boolean inCheck;
    private int moveNum = 0;
    private int startDepth = 4;
    Move theMove;

    Player ( Colours c ) {

        type = Players.Computer;
        colour = c;
    }

    // Calls the Max function of the minimax algorithm to get the best move

    Move getMove ( Board ob, boolean inCheck ) {

        Board b = cloneBoard(ob);
        this.inCheck = inCheck;
        Max(b, Integer.MIN_VALUE, Integer.MAX_VALUE, startDepth);
        return theMove;
    }

    // Max function of minimax (alpha-beta)
    private int Max ( Board b, int alpha, int beta, int depth ) {

        Piece p;
        Pieces op, np;
        boolean wasFirstMove;
        boolean wasEnPassant;
        boolean wasPromotion;

        if (depth == 0) { //if at max ply
            return evalBoard(b);
        }
        List<Move> moves = getAllMoves(b, colour == Colours.White ? Colours.White : Colours.Black, inCheck);
        if (moves != null) {
            while(moves.size() > 0) {
                Move m = moves.remove(0); // get move
                wasFirstMove = b.isFirstMove(m.getFrom());
                wasEnPassant = (b.checkType(m.getFrom()) == Pieces.Pawn && m.getFrom().getColumn() != m.getTo().getColumn() &&
                        !b.hasPiece(m.getTo()));
                op = b.checkType(m.getFrom());
                p = b.makeMove(m, moveNum); // make move
                np = b.checkType(m.getTo());
                wasPromotion = op != np;
                moveNum++;
                int value = Min(b, alpha, beta, depth - 1); //call min
                if (value >= beta) { // beta cutoff
                    b.undoMove(m, p, wasFirstMove, wasEnPassant, wasPromotion);
                    moveNum--;
                    return beta;
                }
                if (value > alpha) {
                    if (depth == startDepth) {
                        theMove = m;
                    }
                    alpha = value;
                }
                b.undoMove(m, p, wasFirstMove, wasEnPassant, wasPromotion); //undo move
                moveNum--;
            }
        }
        return alpha;
    }

    private int Min( Board b, int alpha, int beta, int depth ) {

        Piece p;
        Pieces op, np;
        boolean wasFirstMove;
        boolean wasEnPassant;
        boolean wasPromotion = false;

        if (depth == 0) {
            return evalBoard(b);
        }
        List<Move> moves = getAllMoves(b, colour == Colours.White ? Colours.Black : Colours.White, false);
        if (moves != null) {
            while(moves.size() > 0) {
                Move m = moves.remove(0); // get move
                wasFirstMove = b.isFirstMove(m.getFrom());
                wasEnPassant = (b.checkType(m.getFrom()) == Pieces.Pawn && m.getFrom().getColumn() != m.getTo().getColumn() &&
                        !b.hasPiece(m.getTo()));
                op = b.checkType(m.getFrom());
                p = b.makeMove(m, moveNum); // make move
                np = b.checkType(m.getTo());
                if (op != np) {
                    wasPromotion = true;
                }
                moveNum++;
                int value = Max(b, alpha, beta, depth - 1); // call max
                if (value <= alpha) { // alpha cutoff
                    b.undoMove(m, p, wasFirstMove, wasEnPassant, wasPromotion); // undo
                    moveNum--;
                    return alpha;
                }
                if (value < beta) {
                    if (depth == startDepth) {
                        theMove = m;
                    }
                    beta = value;
                }
                b.undoMove(m, p, wasFirstMove, wasEnPassant, wasPromotion); // undo
                moveNum--;
            }
        }
        return beta;
    }

    // returns all moves for the player
    private List<Move> getAllMoves ( Board b, Colours colour, boolean inCheck ) {

        Coordinate c;
        List<Move> allMoves = new LinkedList<>();

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                c = new Coordinate(i, j);
                if (b.hasPiece(c) && b.checkColour(c) == colour) {
                    allMoves.addAll(combineLists(c, b.getMoves(colour, c, inCheck)));
                }
            }
        }
        return allMoves;
    }

    private List<Move> combineLists ( Coordinate c, List<Coordinate> to ) {

        List<Move> newList = new LinkedList<>();

        for (Coordinate t : to) {
            newList.add(new Move(c, t));
        }
        return newList;
    }

    // creates a private copy of theboard

    Board cloneBoard (Board b) {

        Board nb = new Board();
        nb.initEmptyBoard();
        Coordinate c;
        Piece p = null;

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                c = new Coordinate(i, j);
                if (b.hasPiece(c)) {
                    switch (b.checkType(c)) {
                        case Pawn:
                            p = new Pawn(b.checkColour(c));
                            break;
                        case Rook:
                            p = new Rook(b.checkColour(c));
                            break;
                        case Knight:
                            p = new Knight(b.checkColour(c));
                            break;
                        case Bishop:
                            p = new Bishop(b.checkColour(c));
                            break;
                        case King:
                            p = new King(b.checkColour(c));
                            break;
                        case Queen:
                            p = new Queen(b.checkColour(c));
                            break;
                    }
                    nb.setPiece(c, p);
                }
            }
        }
        return nb;
    }

    Player setPly ( int ply ) {

        startDepth = ply;
        return this;
    }


    // returns the evaluation of the board
    int evalBoard ( Board b ) {

        return getMaterial(b) + getMobility(b);
    }


    // returns the number of pieces on the board
    int getTotalNumPieces ( Board b ) {

        int c = 0;

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if (b.hasPiece(new Coordinate(i, j))) {
                    c++;
                }
            }
        }
        return c;
    }

    // returns the material valuation of the board (the sum of
    // the piece values)
    int getMaterial ( Board b ) {

        Coordinate c;
        int v = 0;

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                c = new Coordinate(i, j);
                if(b.hasPiece(c)) {
                    if (b.checkColour(c) == colour) {
                        v+=b.checkValue(c);
                    } else {
                        v-=b.checkValue(c);
                    }
                }
            }
        }
        return v;
    }

    // returns the number of moves available. Value becomes more heavily weeighted
    // as the game progresses
    int getMobility ( Board b ) {

        Coordinate c;
        double v = 0;

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                c = new Coordinate(i, j);
                if(b.hasPiece(c)) {
                    if (b.checkColour(c) == colour) {
                        v+=b.getMoves(colour, c, inCheck).toArray().length;
                    } else {
                        v-=b.getMoves(colour, c, inCheck).toArray().length;
                    }
                }
            }
        }
        return (int)Math.round(v*((getTotalNumPieces(b)/32) + 0.1));
    }

    Player setType ( Players type ) {
        this.type = type;
        return this;
    }

    public Players getType ( ) {

        return type;
    }

    Colours getColour ( ) {

        return colour;
    }
}
