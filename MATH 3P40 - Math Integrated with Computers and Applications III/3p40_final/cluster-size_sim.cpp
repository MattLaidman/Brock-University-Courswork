#include <iostream>

#include "ffm.h"

using namespace std;

// Compile as:
//  g++ -Ofast cluster-size_sim.cpp -o cluster-size_sim
// and execute as:
//  ./cluster-size_sim 1> cluster-sizes.dat

int size = 1024;
double p = 0.1;
double f = 0.0002;

int numSkips = 1000;
int numIterations = 1500;
int numSimulations = 5;

int main(int argc, char** argv) {

    // Array to sum results from eah simulation

    int sumSize = 0;
    long* countSums = new long[1];
    countSums[0] = 0;

    for (int s = 0 ; s < numSimulations ; s++) {

        // Init forest
        Forest forest(p, f, size);


        cerr << "Initializing forest to critical state..." << endl;

        for (int i = 0 ; i < numSkips ; i++) { // Ignore first few firres
            forest.strikeLightning();
            do {} while(forest.spreadFire() != 0);
            forest.regrowTrees();
        }


        cerr << "Simulating..." << endl;

        int countSize = 0;
        long* counts = new long[1];
        counts[0] = 0;

        for (int i = 0 ; i < numIterations ; i++) {

            long* tempCounts;
            int tempCountSize;

            tempCountSize = forest.getClusterSizes(tempCounts);

            // for (int j = 0 ; j < tempCountSize ; j++) {
            //     cout << (j+1) << " " << tempCounts[j] << endl;
            // }

            if (tempCountSize > countSize) {
                for (int j = 0 ; j < countSize ; j++) {
                    tempCounts[j] += counts[j];
                }
                delete[] counts;
                counts = tempCounts;
                countSize = tempCountSize;
            } else {
                for (int j = 0 ; j < tempCountSize ; j++) {
                    counts[j] += tempCounts[j];
                }
                delete[] tempCounts;
            }

            forest.strikeLightning();
            do {} while(forest.spreadFire() != 0);
            forest.regrowTrees();
        }


        if (countSize > sumSize) {
            for (int i = 0 ; i < sumSize ; i++) {
                counts[i] += countSums[i];
            }
            delete[] countSums;
            countSums = counts;
            sumSize = countSize;
        } else {
            for (int i = 0 ; i < countSize ; i++) {
                countSums[i]+=counts[i];
            }
            delete[] counts;
        }
    }

    for (int i = 0 ; i < sumSize ; i++) {
        cout << (i + 1) << "\t" << (((double) countSums[i])/((double) numSimulations))/(((double) countSums[0])/((double) numSimulations)) << endl;
    }


    return 0;
}