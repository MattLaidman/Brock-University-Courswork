#ifndef ECA_H
#define ECA_H


#include <iostream>

#include "r250.c" // Recommended RNG from Dr. Fuks


using namespace std;


// Elementary Cellular Automata Types
//
// Implemented Rules (Identified by their Wolfram number):
//     - 18
//     - 30
//     - 54
//
// Written By: Matt Laidman, Febrauary 2017

// Parameters

const int SEED = time(NULL); // RNG seed
const float INIT_PROB = 0.5; // Initial state probability of 1


// ECA Class Declaration

class ECA {
    protected:
        int size;
        bool* state;
        void initializeState();
        virtual bool getState(bool, bool, bool) = 0;
    public:
        ECA();
        ECA(int);
        virtual ~ECA();
        ECA* nextState();
        void printState();
        void printAccentedState();
        int getNumOccupied();
        int getNumPairs();
        int getNumLargeSpaces();
};

// Rule18 Subtype

class Rule18 : public ECA {
    protected:
        bool getState(bool, bool, bool);
    public:
        using ECA::ECA;
};

// Rule30 Subtype

class Rule30 : public ECA {
    protected:
        bool getState(bool, bool, bool);
    public:
        using ECA::ECA;
};

// Rule54 Subtype

class Rule54 : public ECA {
    protected:
        bool getState(bool, bool, bool);
    public:
        using ECA::ECA;
};


// Default Constructor
// Instantiates class with size 100
ECA::ECA() : ECA(100) {}

// Constructor
// Instantiates class with given size
ECA::ECA(int s) {
    r250_init(SEED);
    size = s;
    state = new bool[size];
    for (int i = 0 ; i < size ; i++) {
        state[i] = false;
    }
    initializeState();
}

// Destructor
ECA::~ECA() {
    delete[] state;
}


// Public Functions

// Generates the next state, returns the object for function chaining
ECA* ECA::nextState() {
    bool* newState = new bool[size];
    for (int i = 0 ; i < size ; i++) {
        // Get state given current and immediate neighbours
        newState[i] = getState(state[((i-1)%size+size)%size],
                               state[(i%size+size)%size],
                               state[((i+1)%size+size)%size]);
    }
    // Copy new state to old state
    for (int i = 0 ; i < size ; i++) {
        state[i] = newState[i];
    }
    delete[] newState;
    return this;
}

// Prints the current state
// 1 implies the cell is occupied
void ECA::printState() {
    for (int i = 0 ; i < size ; i++) {
        if (state[i]) {
            cout << "1 ";
        } else {
            cout << "0 ";
        }
    }
    cout << endl;
}

// Prints the current state with accents on occupied cells which are boundaries to gaps > 1
// 1 implies the cell is occupied, 2 is an accented occupied cell
void ECA::printAccentedState() {
    for (int i = 0 ; i < size ; i++) {
        if (state[i]) {
            if ((!state[((i+1)%size+size)%size] && !state[((i+2)%size+size)%size]) || (!state[((i-1)%size+size)%size] && !state[((i-2)%size+size)%size])) {
                cout << "2 ";
            } else {
                cout << "1 ";
            }
        } else {
            cout << "0 ";
        }
    }
    cout << endl;
}

// Returns the number of distinct pairs in the current state
int ECA::getNumPairs() {
    int count = 0;
    for (int i = 0 ; i < size ; i++) {
        if (state[i] && state[(i+1)%size]) {
            count++;
        }
    }
    return count;
}

// Returns the number of occupied cells
int ECA::getNumOccupied() {
    int count = 0;
    for (int i = 0 ; i < size ; i++) {
        if (state[i]) {
            count ++;
        }
    }
    return count;
}

// Returns the number of "large" (>3) empty spaces
int ECA::getNumLargeSpaces() {
    int count = 0;
    int temp = 0;
    for (int i = 0 ; i < size ; i++) {
        if (state[i]) {
            if (temp > 3) {
                count++;
            }
            temp = 0;
        } else {
            temp++;
        }
    }
    return count;
}

// Private Functions

// Randomly initializes the states
void ECA::initializeState() {
    for (int i = 0 ; i < size ; i++) {
        if (dr250() < INIT_PROB) {
            state[i] = true;
        }
    }
    // state[size/2] = true;
}

// SubType getState functions
// Returns the state of the cell given it's left neighbour, current state, and
// right neighbour

// Rule18
bool Rule18::getState(bool left, bool current, bool right) {
    return left + right - right*current - current*left - 2*right*left +
           2*right*current*left;
}

// Rule30
bool Rule30::getState(bool left, bool current, bool right) {
    return left + current + right - 2*left*current - 2*left*right -
           current*right + 2*left*current*right;
}


// Rule54
bool Rule54::getState(bool left, bool current, bool right) {
    return left - 2*left*current + right + current - left*right +
           2*left*right*current - 2*right*current;
}


#endif