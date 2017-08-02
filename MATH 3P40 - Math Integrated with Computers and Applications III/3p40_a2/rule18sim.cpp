#include <iostream>
#include "eca.h"

using namespace std;


// Elementary Cellular Automata Rule 18 Simulation
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

const int NUM_CELLS = 100000;       // Lattice size
const int NUM_ITERATIONS = 2500;    // Number of iterations for each run
const int NUM_SIMULATIONS = 100;    // Number of simulations to take average of


// Simulation

// 100 simulations of ca rule 18 with lattice size of 100000 for 2500 iterations.
// Average number of pairs at each iteration is printed to cout, status updates
// are printed to cerr.

// This will take a LONG time, just under 20 minutes on my slow laptop
// (Intel m3-6Y30 @ 2.2GHz).

// Compile via:
//     g++ rule18sim.cpp -o rule18sim
// And exeute as:
//     ./rule18sim 1> rule18pairs.dat

int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    long long sums[NUM_ITERATIONS];
    ECA* ca;


    time(&currentTime); // Init timer with current time
    startTime = currentTime;
    
    // Init sums to 0
    for (int i = 0 ; i < NUM_ITERATIONS ; i++) {
        sums[i] = 0;
    }

    for (int s = 0 ; s < NUM_SIMULATIONS ; s++) {
        // Create Rule 18
        ca = new Rule18(NUM_CELLS);
        cerr << "Running Simulation " << s+1 << endl;
        // Get number of each pairs for each iteration
        sums[0] += ca->getNumPairs();
        for (int i = 1 ; i < NUM_ITERATIONS ; i++) {
            ca->nextState();
            // Add to sum for that iteration
            sums[i] += ca->getNumPairs();
        }
        delete ca;
    }

    // Compute averages and output
    for (int i = 0 ; i < NUM_ITERATIONS ; i++) {
        cout << i << " " << double(sums[i])/double(NUM_SIMULATIONS) << endl;
    }

    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}