import java.util.LinkedList;
import java.util.List;

class Board {

    private Square[][] board;

    Board ( ) {

        board = new Square[8][8];
    }

    void initEmptyBoard ( ) {

        for (int i = 0 ; i < 8 ; i ++) {
            for (int j = 0 ; j < 8 ; j++) {
                board[i][j] = new Square();
            }
        }
    }

    // initializes the board
    void initBoard ( ) {

        for (int i = 0 ; i < 8 ; i ++) {
            for (int j = 0 ; j < 8 ; j++) {
                board[i][j] = new Square();
            }
        }
        board[0][0].setPiece(new Rook(Colours.Black));
        board[1][0].setPiece(new Knight(Colours.Black));
        board[2][0].setPiece(new Bishop(Colours.Black));
        board[3][0].setPiece(new Queen(Colours.Black));
        board[4][0].setPiece(new King(Colours.Black));
        board[5][0].setPiece(new Bishop(Colours.Black));
        board[6][0].setPiece(new Knight(Colours.Black));
        board[7][0].setPiece(new Rook(Colours.Black));
        board[0][1].setPiece(new Pawn(Colours.Black));
        board[1][1].setPiece(new Pawn(Colours.Black));
        board[2][1].setPiece(new Pawn(Colours.Black));
        board[3][1].setPiece(new Pawn(Colours.Black));
        board[4][1].setPiece(new Pawn(Colours.Black));
        board[5][1].setPiece(new Pawn(Colours.Black));
        board[6][1].setPiece(new Pawn(Colours.Black));
        board[7][1].setPiece(new Pawn(Colours.Black));
        board[0][6].setPiece(new Pawn(Colours.White));
        board[1][6].setPiece(new Pawn(Colours.White));
        board[2][6].setPiece(new Pawn(Colours.White));
        board[3][6].setPiece(new Pawn(Colours.White));
        board[4][6].setPiece(new Pawn(Colours.White));
        board[5][6].setPiece(new Pawn(Colours.White));
        board[6][6].setPiece(new Pawn(Colours.White));
        board[7][6].setPiece(new Pawn(Colours.White));
        board[0][7].setPiece(new Rook(Colours.White));
        board[1][7].setPiece(new Knight(Colours.White));
        board[2][7].setPiece(new Bishop(Colours.White));
        board[3][7].setPiece(new Queen(Colours.White));
        board[4][7].setPiece(new King(Colours.White));
        board[5][7].setPiece(new Bishop(Colours.White));
        board[6][7].setPiece(new Knight(Colours.White));
        board[7][7].setPiece(new Rook(Colours.White));
    }

    //makes the passed move and handles any special moves that occurred.
    Piece makeMove ( Move m, int moves ) {

        Coordinate from = m.getFrom();
        Coordinate to = m.getTo();
        Square fromSquare = board[from.getColumn()][from.getRow()];
        Square toSquare = board[to.getColumn()][to.getRow()];
        Piece fromPiece = fromSquare.getPiece();
        Piece toPiece = null;

        if (!hasPiece(from)) {
            return null;
        }

        switch (fromPiece.getType()) {
            case Pawn:
                if (fromPiece.isFirstMove()) {
                    if (Math.abs(from.getRow() - to.getRow()) == 2) {
                        fromPiece.firstMoveMade(true, moves);
                    } else {
                        fromPiece.firstMoveMade(false, moves);
                    }
                }
                break;
            case Rook:
            case King:
                if (fromPiece.isFirstMove()) {
                    fromPiece.firstMoveMade(false, moves);
                }
                break;
        }
        if (toSquare.hasPiece()) {
            toPiece = toSquare.takePiece();
        }
        toSquare.setPiece(fromSquare.takePiece());

        //Castling
        if (checkType(m.getTo()) == Pieces.King && Math.abs(m.getTo().getColumn()-m.getFrom().getColumn()) == 2) {
            if (m.getTo().getColumn() == 6) {
                movePiece(new Coordinate(7, m.getTo().getRow()),
                        new Coordinate(5, m.getTo().getRow()));
            } else if (m.getTo().getColumn() == 2) {
                movePiece(new Coordinate(0, m.getTo().getRow()),
                        new Coordinate(3, m.getTo().getRow()));
            }
        }

        // En passant
        if (checkType(m.getTo()) == Pieces.Pawn &&
                m.getTo().getRow() != m.getFrom().getRow() &&
                m.getTo().getColumn() != m.getFrom().getColumn()) { // pawn that attacked
            switch (checkColour(m.getTo())) {
                case White:
                    if (m.getTo().getRow() == 2 &&
                            canEnPassant(new Coordinate(m.getTo().getColumn(), m.getFrom().getRow()))) {
                        takePiece(new Coordinate(m.getTo().getColumn(), m.getFrom().getRow()));
                    }
                    break;
                case Black:
                    if (m.getTo().getRow() == 5 &&
                            canEnPassant(new Coordinate(m.getTo().getColumn(), m.getFrom().getRow()))) {
                        takePiece(new Coordinate(m.getTo().getColumn(), m.getFrom().getRow()));
                    }
                    break;
            }
        }
        // remove enpassant if not taken
        noEnPassant(moves);

        return toPiece;
    }

    // undoes the passed move and any special moves
    void undoMove ( Move m, Piece p, boolean wasFirst, boolean wasEnPassant, boolean wasPromotion ) {

        movePiece(m.getTo(), m.getFrom());
        if (p != null) {
            setPiece(m.getTo(), p);
        }
        if (wasFirst && hasPiece(m.getFrom())) {
            board[m.getFrom().getColumn()][m.getFrom().getRow()].getPiece().setFirstMove();
            board[m.getFrom().getColumn()][m.getFrom().getRow()].getPiece().flipEnPassant();
        }

        // move castle back if castling
        if (checkType(m.getFrom()) == Pieces.King &&
                board[m.getFrom().getColumn()][m.getFrom().getRow()].getPiece().isFirstMove() &&
                m.getFrom().getColumn() == 4) { // put castle back
            if (m.getFrom().getColumn() - m.getTo().getColumn() == -2 &&
                    hasPiece(new Coordinate(m.getTo().getColumn() - 1, m.getTo().getRow())) &&
                    checkType(new Coordinate(m.getTo().getColumn() - 1, m.getTo().getRow())) == Pieces.Rook &&
                    board[m.getTo().getColumn() - 1][m.getFrom().getRow()].getPiece().isFirstMove()) {
                setPiece(new Coordinate(m.getTo().getColumn() + 1, m.getTo().getRow()),
                        takePiece(new Coordinate(m.getTo().getColumn() - 1, m.getTo().getRow())));
            } else if (m.getFrom().getColumn() - m.getTo().getColumn() == 2 &&
                    hasPiece(new Coordinate(m.getTo().getColumn() + 1, m.getTo().getRow())) &&
                    checkType(new Coordinate(m.getTo().getColumn() + 1, m.getTo().getRow())) == Pieces.Rook &&
                    board[m.getTo().getColumn() + 1][m.getFrom().getRow()].getPiece().isFirstMove()) {
                setPiece(new Coordinate(m.getTo().getColumn() - 2, m.getTo().getRow()),
                        takePiece(new Coordinate(m.getTo().getColumn() + 1, m.getTo().getRow())));
            }
        }

        //Replace pawn if En Passant
        if (wasEnPassant) {
            setPiece(new Coordinate(m.getTo().getColumn(), m.getFrom().getRow()),
                    new Pawn(board[m.getFrom().getColumn()][m.getFrom().getRow()].getPiece().getColour()));
        }

        // replace pawn if promo
        if (wasPromotion) {
            swapPiece(m.getFrom(), Pieces.Pawn);
        }
    }

    // checks the move against the piece on that square
    boolean validMove ( Coordinate from, Coordinate to) {

        return board[from.getColumn()][from.getRow()].hasPiece() && board[from.getColumn()][from.getRow()].getPiece().validMove(from, to);
    }

    // check the move against the other pieces on the board
    boolean checkPath ( Coordinate from, Coordinate to, Pieces piece ) {

        int fromColumn = from.getColumn(), fromRow = from.getRow();
        int toColumn = to.getColumn(), toRow = to.getRow();

        switch (piece) {

            case Pawn:
                if (fromColumn == toColumn) {
                    if (fromRow - toRow == -2) { // First move, moving 2 down
                        return (!board[fromColumn][fromRow + 1].hasPiece() && !board[toColumn][toRow].hasPiece());
                    } else if (fromRow - toRow == 2) { // first move, moving 2 up
                        return (!board[fromColumn][fromRow - 1].hasPiece() && !board[toColumn][toRow].hasPiece());
                    } else if (fromRow - toRow == -1 || fromRow - toRow == 1) { // moving 1 up or down
                        return !board[toColumn][toRow].hasPiece(); // ensure no piece
                    }
                } else if (Math.abs(fromColumn - toColumn) == 1 && Math.abs(fromRow - toRow) == 1) { // attempting to take
                    return board[toColumn][toRow].hasPiece() ||
                            board[toColumn][fromRow].hasPiece() && board[toColumn][fromRow].getPiece().canEnPassant();
                }
                return false;
            case Queen: // Queen Moves are combination of rook and bishop paths
            case Bishop:
                if (fromColumn < toColumn && fromRow < toRow) { // moving down, right
                    for (int i = fromColumn + 1, j = fromRow + 1 ; i < toColumn && j < toRow ; i++, j++) {
                        if (board[i][j].hasPiece()) return false;
                    }
                } else if (fromColumn < toColumn && fromRow > toRow) { // moving up, right
                    for (int i = fromColumn + 1, j = fromRow - 1 ; i < toColumn && j > toRow ; i++, j--) {
                        if (board[i][j].hasPiece()) return false;
                    }
                } else if (fromColumn > toColumn && fromRow < toRow) { // moving down, left
                    for (int i = fromColumn - 1, j = fromRow + 1 ; i > toColumn && j < toRow ; i--, j++) {
                        if (board[i][j].hasPiece()) return false;
                    }
                } else if (fromColumn > toColumn && fromRow > toRow) { // moving up, left
                    for (int i = fromColumn - 1, j = fromRow - 1 ; i > toColumn && j > toRow ; i--, j--) {
                        if (board[i][j].hasPiece()) return false;
                    }
                }
                if (piece != Pieces.Queen) { // If Bishop, no other possible moves
                    return true;
                }
            case Rook:
                if (fromRow != toRow && fromColumn == toColumn) { // Rook moving vertically (or Queen)
                    if (fromRow < toRow) { // moving down
                        for (int i = fromRow + 1; i < toRow ; i++) {
                            if (board[fromColumn][i].hasPiece()) {
                                return false;
                            }
                        }
                    } else { // moving up
                        for (int i = fromRow - 1 ; i > toRow ; i--) {
                            if (board[fromColumn][i].hasPiece()) {
                                return false;
                            }
                        }
                    }
                } else if (fromRow == toRow && fromColumn != toColumn) { // Rook moving horizontally (or Queen)
                    if (fromColumn < toColumn) { // moving left
                        for (int i = fromColumn + 1; i < toColumn ; i++) {
                            if (board[i][fromRow].hasPiece()) {
                                return false;
                            }
                        }
                    } else { // moving right
                        for (int i = fromColumn - 1 ; i > toColumn ; i--) {
                            if (board[i][fromRow].hasPiece()) {
                                return false;
                            }
                        }
                    }
                }
                return true; // no other moves for rook or queen
            case Knight:
                return true; // Knight can jump over pieces
            case King:
                if (board[fromColumn][fromRow].getPiece().isFirstMove()) { // if kings first move
                    if (fromColumn == 4 && fromColumn - toColumn == -2 && // if castling
                            !board[fromColumn - 1][fromRow].hasPiece() &&
                            !board[fromColumn - 2][fromRow].hasPiece() &&
                            !board[fromColumn - 3][fromRow].hasPiece()) {
                        return board[fromColumn - 4][fromRow].hasPiece() &&
                                board[fromColumn - 4][fromRow].getPiece().isFirstMove() &&
                                board[fromColumn - 4][fromRow].getPiece().getType() == Pieces.Rook;
                    } else if (fromColumn == 4 && fromColumn - toColumn == 2 &&
                            !board[fromColumn + 1][fromRow].hasPiece() &&
                            !board[fromColumn + 2][fromRow].hasPiece()) {
                        return board[fromColumn + 3][fromRow].hasPiece() &&
                                board[fromColumn + 3][fromRow].getPiece().isFirstMove() &&
                                board[fromColumn + 3][fromRow].getPiece().getType() == Pieces.Rook;
                    }
                }
                return (Math.abs(fromColumn - toColumn) == 1 && Math.abs(fromRow - toRow) == 0) ||
                        (Math.abs(fromColumn - toColumn) == 0 && Math.abs(fromRow - toRow) == 1) ||
                        (Math.abs(fromColumn - toColumn) == 1 && Math.abs(fromRow - toRow) == 1); // King can only move one square if not first move
        }
        return false;
    }


    //removes the en passant flag from pawns
    void noEnPassant ( int moves ) {

        for (int i = 0 ; i < 7 ; i++) {
            if (board[i][3].hasPiece() && board[i][3].getPiece().canEnPassant() && board[i][3].getPiece().getEnPassantMove() != moves) {
                board[i][3].getPiece().flipEnPassant();
            }
        }
        for (int i = 0 ; i < 7 ; i++) {
            if (board[i][4].hasPiece() && board[i][4].getPiece().canEnPassant() && board[i][4].getPiece().getEnPassantMove() != moves) {
                board[i][4].getPiece().flipEnPassant();
            }
        }
    }

    boolean canEnPassant ( Coordinate s ) {

        return board[s.getColumn()][s.getRow()].hasPiece() && board[s.getColumn()][s.getRow()].getPiece().canEnPassant();
    }

    Pieces checkType ( Coordinate s ) {

        if (board[s.getColumn()][s.getRow()].hasPiece()) {
            return board[s.getColumn()][s.getRow()].getPiece().getType();
        }
        return null;
    }

    Colours checkColour ( Coordinate s ) {

        return board[s.getColumn()][s.getRow()].getPiece().getColour();
    }

    int checkValue ( Coordinate s ) {

        return board[s.getColumn()][s.getRow()].getPiece().getValue();
    }

    boolean isFirstMove ( Coordinate s ) {

        return board[s.getColumn()][s.getRow()].hasPiece() && board[s.getColumn()][s.getRow()].getPiece().isFirstMove();
    }

    // change the piece on a given square with a new piece
    void swapPiece ( Coordinate s, Pieces newPiece ) {

        if (board[s.getColumn()][s.getRow()].hasPiece()) {
            switch (newPiece) {
                case Rook:
                    board[s.getColumn()][s.getRow()].setPiece(new Rook(board[s.getColumn()][s.getRow()].takePiece().getColour()));
                    break;
                case Knight:
                    board[s.getColumn()][s.getRow()].setPiece(new Knight(board[s.getColumn()][s.getRow()].takePiece().getColour()));
                    break;
                case Bishop:
                    board[s.getColumn()][s.getRow()].setPiece(new Bishop(board[s.getColumn()][s.getRow()].takePiece().getColour()));
                    break;
                case Queen:
                    board[s.getColumn()][s.getRow()].setPiece(new Queen(board[s.getColumn()][s.getRow()].takePiece().getColour()));
                    break;
                case Pawn:
                    board[s.getColumn()][s.getRow()].setPiece(new Pawn(board[s.getColumn()][s.getRow()].takePiece().getColour()));
                    break;
            }
        }
    }

    void movePiece ( Coordinate f, Coordinate t ) {

        board[t.getColumn()][t.getRow()].setPiece(board[f.getColumn()][f.getRow()].takePiece());
    }

    Piece takePiece ( Coordinate s ) {

            return board[s.getColumn()][s.getRow()].takePiece();
    }

    boolean setPiece ( Coordinate s, Piece p ) {

        if (!board[s.getColumn()][s.getRow()].hasPiece()) {
             board[s.getColumn()][s.getRow()].setPiece(p);
            return true;
        }
        return false;
    }

    boolean hasPiece ( Coordinate s ) {

        return board[s.getColumn()][s.getRow()].hasPiece();
    }

    // old console text board
    void printBoard ( ) {
        for (int i = 0 ; i < 8 ; i++) {
            System.out.print("\t\t" + i + "\t\t");
        }
        System.out.println();
        System.out.println();
        for (int i = 0 ; i < 8 ; i++) {
            System.out.print(i);
            for (int j = 0 ; j < 8 ; j++) {
                System.out.print(board[j][i].hasPiece() ?
                        "\t" + board[j][i].getPiece().getColour() + board[j][i].getPiece().getType() + "\t" :
                        "\t\t\t\t");
            }
            System.out.println(i);
            System.out.println();
        }
        for (int i = 0 ; i < 8 ; i++) {
            System.out.print("\t\t" + i + "\t\t");
        }
        System.out.println();
        System.out.println();
    }

    // returns the move for a given piece
    List<Coordinate> getMoves (Colours currentTurn, Coordinate p, boolean inCheck ) {

        List<Coordinate> allMoves = new LinkedList<>();

        if (hasPiece(p) && currentTurn == checkColour(p)) {
            for (int i = 0 ; i < 8 ; i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    if (validMove(p, new Coordinate(i, j)) &&
                            checkPath(p, new Coordinate(i, j), checkType(p)) &&
                            (!hasPiece(new Coordinate(i, j)) || checkColour(new Coordinate(i, j)) != currentTurn)) {
                        if (!anyCouldAttackKing(p, new Coordinate(i, j))) {
                            allMoves.add(new Coordinate(i, j));
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    // retusn true if any pieces can attack the king
    boolean anyCanAttackKing ( Colours currentTurn ) {

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if (hasPiece(new Coordinate(i, j)) && checkColour(new Coordinate(i, j)) == currentTurn) {
                    if (canAttackKing(new Coordinate(i, j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // true if the passedpiece can attack the king
    boolean canAttackKing ( Coordinate s ) {

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if (hasPiece(new Coordinate(i, j)) && checkType(new Coordinate(i, j)) == Pieces.King && checkColour(new Coordinate(i, j)) != checkColour(s)) {
                    if (validMove(s, new Coordinate(i, j)) && checkPath(s, new Coordinate(i, j), checkType(s))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //true if any piece could attack the king if the move was made
    boolean anyCouldAttackKing ( Coordinate from, Coordinate to ) {

        Piece p, q = null, c = null;
        boolean could;

        p = takePiece(from);
        if (hasPiece(to)) {
            if (checkColour(to) != p.getColour()) {
                q = takePiece(to);
            } else {
                return false;
            }
        }
        setPiece(to, p);
        if (p.getType() == Pieces.King && p.isFirstMove()) { // if castling
            if (from.getColumn() - to.getColumn() == -2 && from.getColumn() == 4 &&
                    hasPiece(new Coordinate(to.getColumn() + 1, to.getRow())) &&
                    checkType(new Coordinate(to.getColumn() + 1, to.getRow())) == Pieces.Rook) {
                if ((c = takePiece(new Coordinate(to.getColumn() + 1, to.getRow()))).isFirstMove()) {
                    setPiece(new Coordinate(to.getColumn() - 1, to.getRow()), c);
                } else {
                    setPiece(new Coordinate(to.getColumn() + 1, to.getRow()), c);
                }
            } else if (from.getColumn() - to.getColumn() == 2 && from.getColumn() == 4 &&
                    hasPiece(new Coordinate(to.getColumn() - 2, to.getRow())) &&
                    checkType(new Coordinate(to.getColumn() - 2, to.getRow())) == Pieces.Rook) {
                if ((c = takePiece(new Coordinate(to.getColumn() - 2, to.getRow()))).isFirstMove()) {
                    setPiece(new Coordinate(to.getColumn() + 1, to.getRow()), c);
                } else {
                    setPiece(new Coordinate(to.getColumn() - 2, to.getRow()), c);
                }
            }
        }
        if (checkColour(to) == Colours.White) {
            could = anyCanAttackKing(Colours.Black);
        } else {
            could = anyCanAttackKing(Colours.White);
        }
        p = takePiece(to);
        if (p.getType() == Pieces.King && p.isFirstMove()) { // put castle back
            if (from.getColumn() - to.getColumn() == -2 && c != null && c.isFirstMove()) {
                setPiece(new Coordinate(to.getColumn() + 1, to.getRow()), takePiece(new Coordinate(to.getColumn() - 1, to.getRow())));
            } else if (from.getColumn() - to.getColumn() == 2 && c != null && c.isFirstMove()) {
                setPiece(new Coordinate(to.getColumn() - 2, to.getRow()), takePiece(new Coordinate(to.getColumn() + 1, to.getRow())));
            }
        }
        if (q != null) {
            setPiece(to, q);
        }
        setPiece(from, p);
        return could;
    }
}
