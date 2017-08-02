#include <iostream>
#include "eca.h"

using namespace std;


// Elementary Cellular Automata Rule 30 Simulation
//
// MATH 3P40 - Mathematics Integrated with Computers and Application III
// Brock University
//
// Assignment 2
// Due Febraury 16, 2017
//
// Matt Laidman
// 5199807


// Parameters

const int NUM_CELLS = 10000;       // Lattice size
const int NUM_ITERATIONS = 10000;   // Number of iterations for each run


// Simulation

// Compile via:
//     g++ rule30sim.cpp -o rule30sim
// And exeute as:
//     ./rule30sim 1> rule30.dat

int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    ECA* ca;


    time(&currentTime); // Init timer with current time
    startTime = currentTime;

    // Create Rule 54
    ca = new Rule30(NUM_CELLS);
    // Get number of e vectors for each iteration
    cout << 0 << " " << ca->getNumOccupied() << endl;
    for (int i = 1 ; i < NUM_ITERATIONS ; i++) {
        ca->nextState();
        // Add to sum for that iteration
        cout << i << " " << ca->getNumOccupied() << endl;
    }
    delete ca;


    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}