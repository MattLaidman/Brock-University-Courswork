#include <iostream>
#include "eca.h"

using namespace std;


// Directed site Percolation as a Cellular Automaton
//
// MATH 3P40 - Mathematics Integrated with Computers and Application III
// Brock University
//
// Assignment 4
// Due April 4, 2017
//
// Matt Laidman
// 5199807


// Parameters

const int NUM_CELLS = 10000;       // Lattice size

// Simulation

// Compile via:
//     g++ problem2.cpp -o problem2
// And exeute as:
//     ./problem2 1> problem2.dat

int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    ECA* ca;

    time(&currentTime); // Init timer with current time
    startTime = currentTime;

    float p = 0.1;
    while (p <= 0.9) {
        ca = new DirectedSitePercolation(NUM_CELLS, p);
        for (int i = 1 ; i < NUM_CELLS ; i++) {
            ca->nextState();
        }
        cout << p << " " << float(ca->getNumOccupied())/float(NUM_CELLS) << endl;
        delete ca;
        p += 0.01;
    }
    
    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}