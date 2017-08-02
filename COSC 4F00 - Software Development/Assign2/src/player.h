#ifndef PLAYER_H
#define PLAYER_H

#include <cstdlib>
#include <ctime>
#include <iostream>
#include <unistd.h>

#include "board.h"

// player.h

// Type Definitions

// Player class
class Player {
    protected:
        Colour colour;
        bool human;
        int score;
    public:
        Player(Colour, bool);
        virtual ~Player() {}; 
        Colour getColour();
        bool isHuman();
        int getScore();
        void setScore(int);
        virtual int getMove(Board) = 0;

        static void* getMoveWrap(void* arg);
        int __aiMove; // secret
};

// Constructor
// Creates a Player of given colour
Player::Player(Colour colour, bool human) : colour(colour), human(human), score(0) {}

// Helper struct to be passed to getMoveWrap...
// Can only pass one parameter to pthread.
struct GetMoveStruct {
    Player* p;
    Board b;
    GetMoveStruct(Player* p, Board b) : p(p), b(b) {}
};

// HumanPlayer class
class HumanPlayer : public Player {
    public:
        HumanPlayer(Colour);
        int getMove(Board);
};

// Constructor
// Calls the Player constructor
HumanPlayer::HumanPlayer(Colour colour) : Player(colour, true) {}

// ComputerPlayer class
class ComputerPlayer : public Player {
    private:
        int maxDepth;
        int bestPeg;
        int negamax(Board, int, int, int, Colour);
    public:
        ComputerPlayer(Colour);
        int getMove(Board);
};

// Constructor
// Calls the Player constructor
ComputerPlayer::ComputerPlayer(Colour colour) : Player(colour, false), maxDepth(12) {
    srand(time(NULL)); // seed first move random number with current time
}

// Class Method Implementations

// Player class

// getColour method
Colour Player::getColour() {
    return colour;
}

// isHuman method
bool Player::isHuman() {
    return human;
}

// getScore method
int Player::getScore() {
    return score;
}

// setScore method
void Player::setScore(int score) {
    this->score = score;
    return;
}

// messy pthreads...
// This is literally just a horrifying wrapper so getmove can be given
// as pthread start routine...
void* Player::getMoveWrap(void* arg) {
    GetMoveStruct* gms = (GetMoveStruct*)arg;
    Player* player = gms->p;
    player->__aiMove = player->getMove(gms->b);
    return (void*)(&(player->__aiMove));
}

// HumanPlayer class

// getMove method
// Creates a move from user input
int HumanPlayer::getMove(Board board) {
    char peg;
    int ipeg;
    do {
        std::cout<<"Peg ([A..F], case-sensitive): ";
        std::cin>>peg;
        ipeg = ((int)peg)-65;
    } while((ipeg < 0) || (ipeg > 7));
    return ipeg;
}

// ComputerPlayer class

// getMove method
int ComputerPlayer::getMove(Board board) {
    if (board.getMoveNum() <= 1) {
        sleep(1); // some "realness" to the first move...
        return (rand() % 8);
    }
    negamax(board, maxDepth, -1000, 1000, colour);
    return bestPeg;
}   

// NegaMax with Alpha-Beta Pruning
// Calculates the move to be played.

// function negamax(board, depth, alpha, beta, colour)
//     if depth = 0 or no moves left
//         return colour*board
//     bestValue := −1000
//     foreach possible move
//         value := −negamax(child, depth−1, −beta, −alpha, -Colour)
//         bestValue := max(bestValue, value)
//         alpha := max(alpha, value)
//         if alpha >= beta
//             break
//     return bestValue
int ComputerPlayer::negamax(Board board, int depth, int alpha, int beta, Colour colour) {
    if (depth == 0 || board.getMoveNum() == 24) { // if max depth or final move
        if (colour == this->colour) { // return board evaluation
            return board.evalBoard(colour);
        } else {
            return (-1)*board.evalBoard(colour);
        }
    }
    int bestValue = -1000;
    for (int peg = 0 ; peg < 8 ; peg++) { // for each possible move
        if (board.getNumPieces(peg) != 3) { // check legal move
            board.addPiece(peg, colour);
            int value = (-1)*negamax(board, depth-1, (-1)*beta, (-1)*alpha, colour == Red ? White : Red);
            board.removePiece(peg);
            if (value > bestValue) { // if better move
                bestValue = value; // update best move
                bestPeg = peg;
            }
            if (value > alpha) {
                alpha = value;
            }
            if (alpha >= beta) { // prune
                break;
            }
        }
    }
    return bestValue; // return the best evaluation
}



#endif