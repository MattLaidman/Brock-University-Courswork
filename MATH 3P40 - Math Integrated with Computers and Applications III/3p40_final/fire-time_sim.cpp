#include <iostream>

#include "ffm.h"

using namespace std;

// Compile as:
//  g++ -Ofast fire-time_sim.cpp -o fire-time_sim
// and execute as:
//  ./fire-time_sim 1> fire-times.dat

int size = 1024;

int numSkips = 1000;
int numIterations = 10000;


int main(int argc, char** argv) {

    // theta = 500
    double p = 0.1;
    double f = 0.0002;

    double theta1 = p / f;
    double average1 = 0;

    // Init forest
    Forest* forest = new Forest(p, f, size);
    int count;

    cerr << "Initializing forest to critical state..." << endl;

    for (int i = 0 ; i < numSkips ; i++) { // Ignore first few fires
        forest->strikeLightning();
        do {} while(forest->spreadFire());
        forest->regrowTrees();
    }


    cerr << "Simulating..." << endl;

    count = 0;
    for (int i = 0 ; i < numIterations ; i++) {
        forest->strikeLightning();
        do {count++;} while(forest->spreadFire());
        forest->regrowTrees();
        average1+=count;
    }

    average1 = average1 / ((double) numIterations);
    delete forest;

    // theta = 1000
    p = 0.1;
    f = 0.0001;

    double theta2 = p / f;
    double average2 = 0;

    // Init forest
    forest = new Forest(p, f, size);


    cerr << "Initializing forest to critical state..." << endl;

    for (int i = 0 ; i < numSkips ; i++) { // Ignore first few fires
        forest->strikeLightning();
        do {} while(forest->spreadFire());
        forest->regrowTrees();
    }


    cerr << "Simulating..." << endl;

    count = 0;
    for (int i = 0 ; i < numIterations ; i++) {
        forest->strikeLightning();
        do {count++;} while(forest->spreadFire());
        forest->regrowTrees();
        average2+=count;
    }

    average2 = average2 / ((double) numIterations);
    delete forest;


    // theta = 1500
    p = 0.1;
    f = 0.00006666666;

    double theta3 = p / f;
    double average3 = 0;

    // Init forest
    forest = new Forest(p, f, size);


    cerr << "Initializing forest to critical state..." << endl;

    for (int i = 0 ; i < numSkips ; i++) { // Ignore first few fires
        forest->strikeLightning();
        do {} while(forest->spreadFire());
        forest->regrowTrees();
    }


    cerr << "Simulating..." << endl;

    count = 0;
    for (int i = 0 ; i < numIterations ; i++) {
        forest->strikeLightning();
        do {count++;} while(forest->spreadFire());
        forest->regrowTrees();
        average3+=count;
    }

    average3 = average3 / ((double) numIterations);
    delete forest;


    // theta = 2000
    p = 0.1;
    f = 0.00005;

    double theta4 = p / f;
    double average4 = 0;

    // Init forest
    forest = new Forest(p, f, size);


    cerr << "Initializing forest to critical state..." << endl;

    for (int i = 0 ; i < numSkips ; i++) { // Ignore first few fires
        forest->strikeLightning();
        do {} while(forest->spreadFire());
        forest->regrowTrees();
    }


    cerr << "Simulating..." << endl;

    count = 0;
    for (int i = 0 ; i < numIterations ; i++) {
        forest->strikeLightning();
        do {count++;} while(forest->spreadFire());
        forest->regrowTrees();
        average4+=count;
    }

    average4 = average4 / ((double) numIterations);
    delete forest;


    // theta = 2500
    p = 0.1;
    f = 0.00004;

    double theta5 = p / f;
    double average5 = 0;

    // Init forest
    forest = new Forest(p, f, size);


    cerr << "Initializing forest to critical state..." << endl;

    for (int i = 0 ; i < numSkips ; i++) { // Ignore first few fires
        forest->strikeLightning();
        do {} while(forest->spreadFire());
        forest->regrowTrees();
    }


    cerr << "Simulating..." << endl;

    count = 0;
    for (int i = 0 ; i < numIterations ; i++) {
        forest->strikeLightning();
        do {count++;} while(forest->spreadFire());
        forest->regrowTrees();
        average5+=count;
    }

    average5 = average5 / ((double) numIterations);
    delete forest;


    cout << theta1 << "\t" << average1 << endl;
    cout << theta2 << "\t" << average2 << endl;
    cout << theta3 << "\t" << average3 << endl;
    cout << theta4 << "\t" << average4 << endl;
    cout << theta5 << "\t" << average5 << endl;


    return 0;
}