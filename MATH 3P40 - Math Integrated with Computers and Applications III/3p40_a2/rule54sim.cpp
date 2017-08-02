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

const int NUM_CELLS = 1000;            // Lattice size
const int NUM_ITERATIONS = 10000000;     // Number of iterations for each run
const int WINDOW_SIZE = 500;
const int NUM_SIMULATIONS = 10;    // Number of simulations to take average of

// Simulation

// Compile via:
//     g++ rule54sim.cpp -o rule54sim
// And exeute as:
//     ./rule54sim 1> rule54.dat

int main(int argc, char* argv[]) {
    time_t currentTime;
    int startTime;
    int timeElapsed;
    int numSums = NUM_ITERATIONS / WINDOW_SIZE;
    float temp = 0;
    float sums[NUM_ITERATIONS / WINDOW_SIZE];
    ECA* ca;

    time(&currentTime); // Init timer with current time
    startTime = currentTime;
    // Init sums to 0
    for (int i = 0 ; i < NUM_ITERATIONS / WINDOW_SIZE ; i++) {
        sums[i] = 0;
    }

    for (int s = 0 ; s < NUM_SIMULATIONS ; s++) {
        // Create Rule 54
        ca = new Rule54(NUM_CELLS);
        cerr << "Running Simulation " << s+1 << endl;
        // Get number of e vectors for each iteration
        temp += (double(ca->getNumLargeSpaces())/double(NUM_CELLS));
        // cout << 0 << " " << abs(0.5 - double(ca->getNumOccupied())/double(NUM_CELLS)) << endl;
        for (int i = 1 ; i < NUM_ITERATIONS ; i++) {
            ca->nextState();
            // Add to sum for that iteration
            temp += (double(ca->getNumLargeSpaces())/double(NUM_CELLS));
            if (i % WINDOW_SIZE == 0) {
                sums[(i / WINDOW_SIZE) - 1] = temp / WINDOW_SIZE;
                // cout << i << " " << temp / WINDOW_SIZE << endl;
                temp = 0;
            }
        }
        delete ca;
    }

    // Compute averages and output
    for (int i = 0 ; i < NUM_ITERATIONS / WINDOW_SIZE ; i++) {
        cout << i << " " << sums[i]/double(NUM_SIMULATIONS) << endl;
    }


    time(&currentTime); // Stop timer
    timeElapsed = currentTime - startTime;
    cerr << "Time Elapsed: " << timeElapsed << " seconds" << endl;   
}