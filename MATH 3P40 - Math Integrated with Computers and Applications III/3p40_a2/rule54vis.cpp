#include <iostream>
#include "eca.h"

using namespace std;


// Elementary Cellular Automata Rule 18 Visualization
//
// Written by: Matt Laidman, February 2017


// Parameters

const int NUM_CELLS = 1000;         // Lattice size
const int NUM_ITERATIONS = 1000;    // Number of iterations for each run


// Simulation

// Visualization of ECA Rule 54, 

// Compile via:
//     g++ rule54vis.cpp -o rule54vis
// And exeute as:
//     ./rule54vis 1> 54.dat


int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    ECA* ca;


    time(&currentTime); // Init timer with current time
    startTime = currentTime;

    // Create Rule 54
    ca = new Rule54(NUM_CELLS);
    ca->printState();
    for (int i = 1 ; i < NUM_ITERATIONS ; i++) {
        ca->nextState()->printState();
    }
    delete ca;

    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}