#include <iostream>

#include "ffm.h"

using namespace std;


// Compile as:
//  g++ -Ofast fire-size_sim.cpp -o fire-size_sim
// and execute as:
//  ./fire-size_sim 1> fire-sizes.dat


int size = 1024;
double p = 0.1;
double f = 0.0002;

int numSkips = 1000;
int numIterations = 1500;
int numSimulations = 100;


int main(int argc, char** argv) {

    // Array to sum results from eah simulation

    int sumSize = 1;
    long* countSums = new long[1];
    countSums[0] = 0;

    for (int s = 0 ; s < numSimulations ; s++) {

        // Init forest
        Forest forest(p, f, size);


        cerr << "Initializing forest to critical state..." << endl;

        int count = 0;
        int result = 0;
        for (int i = 0 ; i < numSkips ; i++) { // Ignore first few firres
            forest.strikeLightning();
            do {
                result = forest. spreadFire();
            } while(result != 0);
            forest.regrowTrees();
        }


        cerr << "Simulating..." << endl;

        int countSize = 1;
        long* counts = new long[1];
        counts[0] = 0;

        count = 0;
        result = 0;


        for (int i = 0 ; i < numIterations ; i++) {
            forest.strikeLightning();
            do {
                // forest.printForest();
                result = forest.spreadFire();
                count+=result;
            } while(result != 0);

            if (count > countSize) {
                long* tempCounts = new long[count];
                for (int j = 0 ; j < count ; j++) {
                    tempCounts[j] = 0;
                }
                for (int j = 0 ; j < countSize ; j++) {
                    tempCounts[j] = counts[j];
                }
                delete[] counts;
                counts = tempCounts;
                countSize = count;
            }
            counts[count-1]++;

            count = 0;
            forest.regrowTrees();
        }

        if (countSize > sumSize) {
            long* tempSums = new long[countSize];
            for (int i = 0 ; i < countSize ; i++) {
                tempSums[i] = 0;
            }
            for (int i = 0 ; i < sumSize ; i++) {
                tempSums[i] = countSums[i];
            }
            delete[] countSums;
            countSums = tempSums;
            sumSize = countSize;
        }
        for (int i = 0 ; i < countSize ; i++) {
            countSums[i]+=counts[i];
        }

        delete[] counts;
    }

    for (int i = 0 ; i < sumSize ; i++) {
        cout << (i + 1) << "\t" << (((double) countSums[i])/((double) numSimulations))/(((double) countSums[0])/((double) numSimulations)) << endl;
    }


    return 0;
}