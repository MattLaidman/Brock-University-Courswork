#include <iostream>
#include "eca.h"

using namespace std;


// Elementary Cellular Automata Rule 30 Visualization
//
// Written by: Matt Laidman, February 2017


// Parameters

const int NUM_CELLS = 100;       // Lattice size
const int NUM_ITERATIONS = 100;    // Number of iterations for each run


// Simulation

// Visualization of ECA Rule 30, 

// Compile via:
//     g++ rule30vis.cpp -o rule30vis
// And exeute as:
//     ./rule30vis 1> 30.dat

int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    ECA* ca;


    time(&currentTime); // Init timer with current time
    startTime = currentTime;

    // Create Rule 30
    ca = new Rule30(NUM_CELLS);
    ca->printAccentedState();
    for (int i = 1 ; i < NUM_ITERATIONS ; i++) {
        ca->nextState()->printAccentedState();
    }
    delete ca;

    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}