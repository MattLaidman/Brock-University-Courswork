import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class Interface extends JPanel implements ActionListener {

    JFrame window;
    static JFrame newGame;
    JPanel board;
    JPanel options;
    static JPanel nGPanel, plyPanel, modePanel;

    private ImageIcon   whitePawnIcon, whiteRookIcon, whiteKnightIcon, whiteBishopIcon, whiteQueenIcon, whiteKingIcon,
                        blackPawnIcon, blackRookIcon, blackKnightIcon, blackBishopIcon, blackQueenIcon, blackKingIcon,
                        blankPiece;

    private JButton[][] buttonBoard;

    JLabel currentTurn, moveNum, check;

    static private JButton start;
    static private JButton quit;

    static private ButtonGroup mode, ply;

    Game game;

    private Player wp, bp;
    private Player currentPlayer;
    private int moves = 1;
    private boolean inCheck = false;
    private Coordinate from = null, to;
    private boolean getPiece = true;

    public Interface ( ) {

        buttonBoard = new JButton[8][8];

        whitePawnIcon = createImageIcon("/ChessPieces/WhitePawn.png");
        whiteRookIcon = createImageIcon("/ChessPieces/WhiteRook.png");
        whiteKnightIcon = createImageIcon("/ChessPieces/WhiteKnight.png");
        whiteBishopIcon = createImageIcon("/ChessPieces/WhiteBishop.png");
        whiteQueenIcon = createImageIcon("/ChessPieces/WhiteQueen.png");
        whiteKingIcon = createImageIcon("/ChessPieces/WhiteKing.png");
        blackPawnIcon = createImageIcon("/ChessPieces/BlackPawn.png");
        blackRookIcon = createImageIcon("/ChessPieces/BlackRook.png");
        blackKnightIcon = createImageIcon("/ChessPieces/BlackKnight.png");
        blackBishopIcon = createImageIcon("/ChessPieces/BlackBishop.png");
        blackQueenIcon = createImageIcon("/ChessPieces/BlackQueen.png");
        blackKingIcon = createImageIcon("/ChessPieces/BlackKing.png");
        blankPiece = createImageIcon("/ChessPieces/NoPiece.png");

        start.addActionListener(this);
        quit.addActionListener(this);
    }

    void updateBoard ( Board b ) {

        if (buttonBoard[0][0] != null) {
            clearBoard();
        }

        for (int i = 0 ; i < 8 ; i++){
            for (int j = 0 ; j < 8 ; j++) {
                updateButton(j, i, b);
            }
        }

        board.invalidate();
        board.validate();
        board.repaint();
    }

    private void clearBoard ( ) {

        for (int i = 7 ; i >= 0 ; i--) {
            for (int j = 7 ; j >= 0 ; j--) {
                board.remove(buttonBoard[j][i]);
            }
        }
    }

    // Button handler
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("start")) {
            newGame.setVisible(false);
            nGPanel.setVisible(false);
            window = new JFrame("Chess");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            board = new JPanel();
            board.setLayout(new GridLayout(8, 8));
            options = new JPanel(new GridLayout(0, 1));
            options.setBorder(new BorderUIResource.LineBorderUIResource(Color.DARK_GRAY));

            window.setContentPane(this);

            currentTurn= new JLabel("Current Turn: White");
            moveNum = new JLabel("Move Number: 1");
            check = new JLabel("In Check: No");
            JButton forfeit = new JButton("Forfeit");
            forfeit.setActionCommand("forfeit");
            forfeit.addActionListener(this);
            options.add(currentTurn);
            options.add(moveNum);
            options.add(check);
            options.add(forfeit);
            window.add(board);
            window.add(options);
            initBoard();

            window.getContentPane().remove(newGame);
            window.getContentPane().invalidate();
            window.getContentPane().validate();
            window.pack();
            window.invalidate();
            window.revalidate();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            switch (mode.getSelection().getActionCommand()) {
                case "hvc":
                    game = new Game(wp = new Player(Colours.White).setType(Players.Human), bp = new Player(Colours.Black).setPly(Integer.parseInt(ply.getSelection().getActionCommand())).setType(Players.Computer));
                    break;
                case "cvh":
                    game = new Game(wp = new Player(Colours.White).setPly(Integer.parseInt(ply.getSelection().getActionCommand())).setType(Players.Computer), bp = new Player(Colours.Black).setType(Players.Human));
                    break;
                case "hvh":
                    game = new Game(wp = new Player(Colours.White).setType(Players.Human), bp = new Player(Colours.Black).setType(Players.Human));
                    break;
            }
            currentPlayer = wp;
        } else if (e.getActionCommand().equals("quit")) {
            newGame.dispose();
        } else if (e.getActionCommand().equals("forfeit")) {
            window.dispose();
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();

                }
            });
        } else {
            if (getPiece) {
                from = new Coordinate(parse(e.getActionCommand(), true), parse(e.getActionCommand(), false));
            } else {
                to = new Coordinate(parse(e.getActionCommand(), true), parse(e.getActionCommand(), false));
                if (from.getColumn() != to.getColumn() || from.getRow() != to.getRow()) {
                    game.getBoard().makeMove(new Move(from, to), moves);
                }
            }
            getPiece = !getPiece;
        }
        if (currentPlayer!= null) {
            if (getPiece) {
                if (currentPlayer.getType() == Players.Computer || from == null) {
                    simulate(null);
                } else if (from.getColumn() != to.getColumn() || from.getRow() != to.getRow()) {
                    simulate(new Move(from, to));
                } else {
                    from = null;
                    to = null;
                }
            } else {
                updateBoard(game.getBoard());
            }
            activateBoard();
        }
    }

    // activate or deactivate buttons
    void activateBoard ( ) {

        java.util.List<Move> moves = getAllMoves(game.getBoard(), currentPlayer.getColour(), inCheck);
        if (getPiece) {
            for (int j = 0 ; j < 8 ; j++) {
                for (int i = 0 ; i < 8 ; i++) {
                    buttonBoard[i][j].setEnabled(false);
                }
            }
            for (Move m : moves) {
                buttonBoard[m.getFrom().getColumn()][m.getFrom().getRow()].setEnabled(true);
            }
        } else {
            for (int j = 0 ; j < 8 ; j++) {
                for (int i = 0 ; i < 8 ; i++) {
                    buttonBoard[i][j].setEnabled(false);
                }
            }
            for (Move m : moves) {
                if (m.getFrom().getColumn() == from.getColumn() && m.getFrom().getRow() == from.getRow()) {
                    buttonBoard[m.getTo().getColumn()][m.getTo().getRow()].setEnabled(true);
                }
            }
            buttonBoard[from.getColumn()][from.getRow()].setEnabled(true);
        }
    }

    // returns listof all possible moves for a player
    private List<Move> getAllMoves (Board b, Colours colour, boolean inCheck ) {

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

    // Handles the move that was made by the player
    // and makes/handles the next move if computer
    void simulate ( Move move ) {

        boolean gO;

        do {
            if (currentPlayer.getType() == Players.Computer) {
                move = currentPlayer.getMove(game.getBoard(), inCheck);
                game.getBoard().makeMove(new Move(move.getFrom(), move.getTo()), moves);
            }

            if (move != null) {
                if (inCheck) { // move was only allowed if took out of check
                    inCheck = false;
                }

                //Pawn Promotion
                if ((move.getTo().getRow() == 0 || move.getTo().getRow() == 7) &&
                        game.getBoard().checkType(move.getTo()) == Pieces.Pawn) {
                    if (currentPlayer.getType() == Players.Human) {
                        promotePawn(move.getTo());
                    } else {
                        game.getBoard().swapPiece(move.getTo(), Pieces.Queen);
                    }
                }

                if (game.getBoard().anyCanAttackKing(currentPlayer.getColour())) {
                    inCheck = true;
                }

                // Switch player
                if (currentPlayer.getColour() == Colours.White) {
                    currentPlayer = bp;
                    currentTurn.setText("Current Turn: Black");
                } else {
                    currentPlayer = wp;
                    currentTurn.setText("Current Turn: White");
                }
                moves++;
                moveNum.setText("Move Number: " + moves);
                if (inCheck) {
                    check.setText("In Check: Yes");
                } else {
                    check.setText("In Check: No");
                }
            }
            updateBoard(game.getBoard());
        } while (currentPlayer.getType() == Players.Computer & !(gO = gameOver(currentPlayer.getColour(), inCheck)));
        if (gO) {
            endGame();
        }
    }

    // Handles stalemate/checkmate dialogs
    void endGame ( ) {

        String[] buttons = { "New Game", "Quit" };
        int choice;

        if (inCheck) {
            choice = JOptionPane.showOptionDialog(null, "Winner: " +
                    (currentPlayer.getColour() == Colours.White ? "Black" : "White"), "Checkmate!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        } else {
            choice = JOptionPane.showOptionDialog(null, "Stalemate!", "Game Over",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        }

        if (choice == 0) {
            window.dispose();
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();

                }
            });
        } else {
            window.dispose();
        }

    }

    // pawn promotion dialog
    void promotePawn ( Coordinate to ) {

        String[] buttons = { "Rook", "Knight", "Bishop", "Queen" };

        int choice = JOptionPane.showOptionDialog(null, "Pawn Promotion", "Promote pawn to:",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[2]);

        switch (choice) {
            case 0:
                game.getBoard().swapPiece(to, Pieces.Rook);
                break;
            case 1:
                game.getBoard().swapPiece(to, Pieces.Knight);
                break;
            case 2:
                game.getBoard().swapPiece(to, Pieces.Bishop);
                break;
            case 3:
                game.getBoard().swapPiece(to, Pieces.Queen);
                break;
        }
    }

    // determines if there are any moves left for the current player
    boolean gameOver( Colours currentTurn, boolean inCheck ) {

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if (!(game.getBoard().getMoves(currentTurn, new Coordinate(i, j), inCheck).isEmpty())) {
                    return false;
                }
            }
        }
        return true;
    }

    int parse (String s, boolean first) {

        return first ? Integer.parseInt(s.substring(0,1)) : Integer.parseInt(s.substring(1,2));
    }

    // uptions the icons on the buttons
    private void updateButton ( int i, int j, Board b ) {

        Coordinate c = new Coordinate(i, j);

        if (b.hasPiece(c)) {
            switch (b.checkColour(c)) {
                case White:
                    switch(b.checkType(c)) {
                        case Pawn:
                            buttonBoard[i][j] = new JButton(whitePawnIcon);
                            break;
                        case Rook:
                            buttonBoard[i][j] = new JButton(whiteRookIcon);
                            break;
                        case Knight:
                            buttonBoard[i][j] = new JButton(whiteKnightIcon);
                            break;
                        case Bishop:
                            buttonBoard[i][j] = new JButton(whiteBishopIcon);
                            break;
                        case Queen:
                            buttonBoard[i][j] = new JButton(whiteQueenIcon);
                            break;
                        case King:
                            buttonBoard[i][j] = new JButton(whiteKingIcon);
                            break;
                    }
                    break;
                case Black:
                    switch(b.checkType(c)) {
                        case Pawn:
                            buttonBoard[i][j] = new JButton(blackPawnIcon);
                            break;
                        case Rook:
                            buttonBoard[i][j] = new JButton(blackRookIcon);
                            break;
                        case Knight:
                            buttonBoard[i][j] = new JButton(blackKnightIcon);
                            break;
                        case Bishop:
                            buttonBoard[i][j] = new JButton(blackBishopIcon);
                            break;
                        case Queen:
                            buttonBoard[i][j] = new JButton(blackQueenIcon);
                            break;
                        case King:
                            buttonBoard[i][j] = new JButton(blackKingIcon);
                            break;
                    }
                    break;
            }
        } else {
            buttonBoard[i][j] = new JButton(blankPiece);
        }
        buttonBoard[i][j].addActionListener(this);
        buttonBoard[i][j].setActionCommand(i + "" + j);
        board.add(buttonBoard[i][j]);
    }

    // creates and shows the initial new game window
    static void createAndShowGUI() {

        newGame = new JFrame("Chess");
        newGame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        nGPanel = new JPanel(new GridLayout(2, 2));

        mode = new ButtonGroup();
        JRadioButton hvc = new JRadioButton("Human (White) vs Computer (Black)", true);
        JRadioButton cvh = new JRadioButton("Computer (White) vs Human (Black)", false);
        JRadioButton hvh = new JRadioButton("Human vs Human", false);
        ply = new ButtonGroup();
        JRadioButton two = new JRadioButton("Two", false);
        JRadioButton four = new JRadioButton("Four", true);
        start = new JButton("Start");
        quit = new JButton("Quit");

        Interface contentPane = new Interface();

        contentPane.setOpaque(true);
        newGame.setContentPane(contentPane);

        hvc.setActionCommand("hvc");
        cvh.setActionCommand("cvh");
        hvh.setActionCommand("hvh");
        mode.add(hvc);
        mode.add(cvh);
        mode.add(hvh);
        two.setActionCommand("2");
        four.setActionCommand("4");
        ply.add(two);
        ply.add(four);
        start.setActionCommand("start");
        quit.setActionCommand("quit");
        modePanel = new JPanel(new GridLayout(0, 1));
        modePanel.add(new JLabel("Game Mode:"));
        modePanel.add(hvc);
        modePanel.add(cvh);
        modePanel.add(hvh);
        plyPanel = new JPanel(new GridLayout(0, 1));
        plyPanel.add(new JLabel("Difficulty (Ply, only applies to computer players):"));
        plyPanel.add(two);
        plyPanel.add(four);

        nGPanel.add(modePanel);
        nGPanel.add(plyPanel);
        nGPanel.add(start);
        nGPanel.add(quit);
        newGame.add(nGPanel);
        newGame.pack();

        newGame.setLocationRelativeTo(null);
        newGame.setVisible(true);
    }

    //initializes the board
    void initBoard ( ) {

        for (int i = 0 ; i < 8 ; i++){
            for (int j = 0 ; j < 8 ; j++) {
                buttonBoard[i][j] = new JButton(blankPiece);
                board.add(buttonBoard[i][j]).setLocation(j, i);
            }
        }
    }

    // returns resource path of the image icon
    static ImageIcon createImageIcon( String path ) {

        java.net.URL image = Interface.class.getResource(path);

        if (image != null) {
            return new ImageIcon(image);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

            }
        });
    }
}
