#ifndef BOARD_H
#define BOARD_H

#include <iostream>

// board.h

// A board object for the 8-Peg, Three Dimensional Tic-Tac-Toe game!

// Type Definitions

// Colour enum
enum Colour {
    None,
    Red,
    White
};

// Board class
class Board {
    private:
        Colour pegs[8][3];
        int moveNum;
    public:
        Board();
        void addPiece(int, Colour);
        void removePiece(int);
        int getNumPieces(int);
        int getMoveNum();
        bool validMove(int);
        void printBoard();
        int evalBoard(Colour);
};

// Board constructor
// creates an empty board for a new game
Board::Board() {
    moveNum = 0;
    for (int peg = 0 ; peg < 8 ; peg++) {
        for (int height = 0 ; height < 3 ; height++) {
            pegs[peg][height] = None;
        }
    }
}

// Class Method Implementations

// Board Class

// addPiece method
// adds a piece of the given colour to the given peg
void Board::addPiece(int peg, Colour colour) {
    for (int height = 0 ; height < 3 ; height++) {
        if (pegs[peg][height] == None) {
            pegs[peg][height] = colour;
            moveNum++;
            break;
        }
    }
    return;
}

// removePiece method
// removes the topmost piece fromt the given peg
void Board::removePiece(int peg) {
    for (int height = 2 ; height >= 0 ; height--) {
        if (pegs[peg][height] != None) {
            pegs[peg][height] = None;
            moveNum--;
            break;
        }
    }
    return;
}

// getNumPieces method
// returns the number of pieces on the peg
int Board::getNumPieces(int peg) {
    int numPieces = 0;
    for (int height = 0 ; height < 3 ; height++) {
        if (pegs[peg][height] == None) {
            break;
        }
        numPieces++;
    }
    return numPieces;
}

// getMoveNum method
int Board::getMoveNum() {
    return moveNum;
}

// validMove method
bool Board::validMove(int peg) {
    return ((peg >= 0) && (peg <= 7) && (getNumPieces(peg) < 3));
}

// evalBoard method
// Evaluates the board with respect to the given colour
// Checks all possible scoring positions
int Board::evalBoard(Colour colour) {
    int score = 0;
    for (int peg = 0 ; peg < 8 ; peg++)
        if (pegs[peg][0] == colour && pegs[peg][1] == colour && pegs[peg][2] == colour) score++;
    for (int height = 0 ; height < 3 ; height++) 
        if (pegs[0][height] == colour && pegs[1][height] == colour && pegs[2][height] == colour) score++;
    for (int height = 0 ; height < 3 ; height++)
        if (pegs[5][height] == colour && pegs[6][height] == colour && pegs[7][height] == colour) score++;
    for (int height = 0 ; height < 3 ; height++)
        if (pegs[7][height] == colour && pegs[4][height] == colour && pegs[1][height] == colour) score++;
    for (int height = 0 ; height < 3 ; height++)
        if (pegs[6][height] == colour && pegs[3][height] == colour && pegs[0][height] == colour) score++;
    for (int height = 0 ; height < 3 ; height++)
        if (pegs[5][height] == colour && pegs[3][height] == colour && pegs[1][height] == colour) score++;
    for (int height = 0 ; height < 3 ; height++)
        if (pegs[6][height] == colour && pegs[4][height] == colour && pegs[2][height] == colour) score++;
    if (pegs[0][0] == colour && pegs[1][1] == colour && pegs[2][2] == colour) score++;
    if (pegs[0][2] == colour && pegs[1][1] == colour && pegs[2][0] == colour) score++;
    if (pegs[5][0] == colour && pegs[6][1] == colour && pegs[7][2] == colour) score++;
    if (pegs[5][2] == colour && pegs[6][1] == colour && pegs[7][0] == colour) score++;
    if (pegs[0][0] == colour && pegs[3][1] == colour && pegs[6][2] == colour) score++;
    if (pegs[0][2] == colour && pegs[3][1] == colour && pegs[6][0] == colour) score++;
    if (pegs[1][0] == colour && pegs[4][1] == colour && pegs[7][2] == colour) score++;
    if (pegs[1][2] == colour && pegs[4][1] == colour && pegs[7][0] == colour) score++;
    if (pegs[1][0] == colour && pegs[3][1] == colour && pegs[5][2] == colour) score++;
    if (pegs[1][2] == colour && pegs[3][1] == colour && pegs[5][0] == colour) score++;
    if (pegs[2][0] == colour && pegs[4][1] == colour && pegs[6][2] == colour) score++;
    if (pegs[2][2] == colour && pegs[4][1] == colour && pegs[6][0] == colour) score++;
    return score;
}

// printBoard method
// Prints the Board, line by line..
void Board::printBoard() {

    std::cout<<"\t     "<<(pegs[5][2] == None ? "|" : (pegs[5][2] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[6][2] == None ? "|" : (pegs[6][2] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[7][2] == None ? "|" : (pegs[7][2] == Red ? "R" : "W"));
    std::cout<<"\n";
    std::cout<<"\t ----"<<(pegs[5][1] == None ? "|" : (pegs[5][1] == Red ? "R" : "W"));
    std::cout<<"---------"<<(pegs[6][1] == None ? "|" : (pegs[6][1] == Red ? "R" : "W"));
    std::cout<<"---------"<<(pegs[7][1] == None ? "|" : (pegs[7][1] == Red ? "R" : "W"));
    std::cout<<"----\n";
    std::cout<<"\t|    "<<(pegs[5][0] == None ? "|" : (pegs[5][0] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[6][0] == None ? "|" : (pegs[6][0] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[7][0] == None ? "|" : (pegs[7][0] == Red ? "R" : "W"));
    std::cout<<"    |\n";
    std::cout<<"\t|   (F)   "<<(pegs[3][2] == None ? "|" : (pegs[3][2] == Red ? "R" : "W"));
    std::cout<<"   (G)   "<<(pegs[4][2] == None ? "|" : (pegs[4][2] == Red ? "R" : "W"));
    std::cout<<"   (H)   |\n";
    std::cout<<"\t|         "<<(pegs[3][1] == None ? "|" : (pegs[3][1] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[4][1] == None ? "|" : (pegs[4][1] == Red ? "R" : "W"));
    std::cout<<"         |\n";
    std::cout<<"\t|         "<<(pegs[3][0] == None ? "|" : (pegs[3][0] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[4][0] == None ? "|" : (pegs[4][0] == Red ? "R" : "W"));
    std::cout<<"         |\n"; 
    std::cout<<"\t|    "<<(pegs[0][2] == None ? "|" : (pegs[0][2] == Red ? "R" : "W"));
    std::cout<<"   (D)   "<<(pegs[1][2] == None ? "|" : (pegs[1][2] == Red ? "R" : "W"));
    std::cout<<"   (E)   "<<(pegs[2][2] == None ? "|" : (pegs[2][2] == Red ? "R" : "W"));
    std::cout<<"    |\n";
    std::cout<<"\t|    "<<(pegs[0][1] == None ? "|" : (pegs[0][1] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[1][1] == None ? "|" : (pegs[1][1] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[2][1] == None ? "|" : (pegs[2][1] == Red ? "R" : "W"));
    std::cout<<"    |\n";
    std::cout<<"\t|    "<<(pegs[0][0] == None ? "|" : (pegs[0][0] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[1][0] == None ? "|" : (pegs[1][0] == Red ? "R" : "W"));
    std::cout<<"         "<<(pegs[2][0] == None ? "|" : (pegs[2][0] == Red ? "R" : "W"));
    std::cout<<"    |\n";
    std::cout<<"\t|   (A)       (B)       (C)   |\n";
    std::cout<<"\t|                             |\n";
    std::cout<<"\t -----------------------------"<<std::endl;
    return;
}

#endif