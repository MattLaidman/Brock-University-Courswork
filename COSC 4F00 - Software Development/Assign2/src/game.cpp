#include "game.h"

// main function
// Displays the menus, starts and drives the game.
int main(int argc, char* argv[]) {
    
    char c, gc;
    int ic, igc;
    bool pa;

    std::cout<<"3D Tic Tac Toe!\n\n";
    do {
        // Main Menu
        std::cout<<"Menu:\n\t1)\tPlay\n\t2)\tDisplay Rules\n\n\t0)\tQuit\n\n";
        std::cout<<"Enter a selection ([0..2]): ";
        std::cin>>c;
        ic = ((int)c)-48;
        if (ic == 0) {
            return 0;
        }
        // Display Rules
        if (ic == 2) {
            std::cout<<"\nRules:\n";
            std::cout<<"There are eight pegs arranged as follows:\n\n";
            board->printBoard();
            std::cout<<"\nEach player is given twelve pieces, the player with ";
            std::cout<<"the red pieces goes first.\n";
            std::cout<<"Players will alternate placing all twelve of their ";
            std::cout<<"pieces on to the pegs, one at\n\ta time.\n";
            std::cout<<"A peg can hold at most 3 pieces.\n";
            std::cout<<"At the end of the twenty-four turns, the player with ";
            std::cout<<"the most pieces forming a\n\tline of three is the ";
            std::cout<<"winner.\n";
            std::cout<<"A line of three occupy either one vertical level, or ";
            std::cout<<"all three vertical levels,\n\tand can be vertical, ";
            std::cout<<"horizontal, or diagonal in direction.\n";
            std::cout<<std::endl;
        }
    } while(ic != 1);
    do {
        board = new Board();
        // Game mode selection menu
        do {
            std::cout<<"\nSelect a game mode:\n";
            std::cout<<"\t1)\tHuman Player vs Human Player\n";
            std::cout<<"\t2)\tHuman Player vs Computer Player\n";
            // AI vs AI is mostly for fun..
            std::cout<<"\t3)\tComputer Player vs Computer Player\n\n";
            std::cout<<"Choose a game mode ([1..3]): ";
            std::cin>>gc;
            igc = ((int)gc)-48;
        } while((igc < 1) || (igc > 3));
        switch (igc) {
            case 1:
                p1 = new HumanPlayer(Red);
                p2 = new HumanPlayer(White);
                break;
            case 2:
                // if human vs ai get human to choose colour
                do {
                    std::cout<<"\nChoose a colour:\n\t1)\tRed\n\t2)\tWhite\n\n";
                    std::cout<<"Choice ([1..2]): ";
                    std::cin>>c;
                    ic = ((int)c)-48;
                } while((ic < 1) || (ic > 2));
                if (ic == 1) {
                    p1 = new HumanPlayer(Red);
                    p2 = new ComputerPlayer(White);
                } else {
                    p1 = new ComputerPlayer(Red);
                    p2 = new HumanPlayer(White);
                }
                break;
            case 3:
                p1 = new ComputerPlayer(Red);
                p2 = new ComputerPlayer(White);
                break;
            default:
                return 1;
        }
        std::cout<<std::endl;
        playGame();
        delete board;
        delete p1;
        delete p2;
        // play again?
        do {
            std::cout<<"\nPlay Again:\n\t1)\tYes\n\t2)\tNo\n\n";
            std::cout<<"Choice ([1..2]): ";
            std::cin>>c;
            ic = ((int)c)-48;
        } while((ic < 1) || (ic > 2));
        pa = (ic == 1 ? true : false);
    } while(pa);

    return 0;
}

// playGame procedure
// Loops until the game is over, getting player's move, printing the board, and
// keeping the score.
void playGame() {
    int peg;
    Player* current = p1;

    pthread_t t;
    void* aiMove; // Really an int ... pthreads ...

    board->printBoard();
    std::cout<<"              R: "<<p1->getScore()<<"           W: "<<
            p2->getScore()<<"\n\n";
    do {
        std::cout<<(current->getColour() == Red ? "Red" :
            "White")<<"'s turn!\n";

        // get move, if computer player join with thread
        if (current->isHuman() || board->getMoveNum() == 0) {
            peg = current->getMove(*board);
        } else {
            pthread_join(t, &aiMove);
            peg = *((int*)aiMove);
        }
        
        std::cout<<std::endl;
        board->addPiece(peg, current->getColour()); // add the mvoe

        // if other player is computer, immediatly start thinking about next move
        if (!(current == p1 ? p2 : p1)->isHuman() && board->getMoveNum() < 24) {
            pthread_create(&t, NULL, Player::getMoveWrap, new GetMoveStruct((current == p1 ? p2 : p1), *board));
        }

        // update scores
        current->setScore(board->evalBoard(current->getColour()));
        current = (current == p1 ? p2 : p1);
        board->printBoard(); // print board and score
        std::cout<<"              R: "<<p1->getScore()<<"           W: "<<
            p2->getScore()<<"\n\n";
    } while (board->getMoveNum() < 24); // loop until no moves left
    std::cout<<"Game Over!\n"; // declare winner
    std::cout<<(p1->getScore() > p2->getScore() ? "Red is the winner!" : 
        (p1->getScore() == p2->getScore() ? "The game is a tie!" :
        "White is the winner!"))<<std::endl;
    
    return;
}