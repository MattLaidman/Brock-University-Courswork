#ifndef FFM_H
#define FFM_H


#include <iostream>
#include <time.h>
#include <fstream>
#include <cstdlib>
#include <cmath>
#include <random>


// ffm.h
//
// An Implementation of the Drossel-Schwabl Self-Organizing Critical Forest-Fire Model.
//
// MATH 3P40 - Mathematics Integrated with Computers and Applications III
// Final Project
// Brock University, Winter 2017.
//
// Matt Laidman
// 5199807


using namespace std;

enum Tree {
    empty,
    tree,
    burning
};

class Forest {
    protected:
        int size; // lattice size
        double pProb, fProb; // parameters
        int theta;
        Tree** forest;
        bool** visited; // Used in cluster counting
        mt19937 generator; // Mersenne-Twister PRNG
        uniform_real_distribution<double> real_dist;
        uniform_int_distribution<int> int_dist;
        long* sizes;
        int countCluster(int, int, int);
    public:
        Forest(const double, const double, const int);
        ~Forest();
        double getDensity(); // returns the density of the forest
        int spreadFire(); // returns the number of trees burned down in each time step
        void regrowTrees(); // make theta growth attempts
        void regrowAllTrees(); // regrow all empty sites with probability p
        void strikeLightning(); // strike lightning on a random tree
        void strikeAllLightning(); // strike all green green trees with lightning with probability f
        int getClusterSizes(long*&); // returns the size of the largest cluster, sets passed reference to distribution
        void printForest(); // print ASCII representation of the forests current state
        void writeForestImage(string); // write a "Plain PPM" image to passed file name string
};

// Constructor
// Initializes the model
Forest::Forest(const double p, const double f, const int s) {
    
    pProb = p;
    fProb = f;
    theta = int(round(pProb / fProb));
    
    size = s;

    generator = mt19937(time(NULL));
    real_dist = uniform_real_distribution<double>(0.0, 1.0);
    int_dist = uniform_int_distribution<int>(0, size-1);

    forest = new Tree*[size]; // init forest with uniform density p
    for (int i = 0 ; i < size ; i++) {  
        forest[i] = new Tree[size];
        for (int j = 0 ; j < size ; j++) {
            if (real_dist(generator) < pProb) {
                forest[i][j] = tree;
            } else {
                forest[i][j] = empty;
            }
        }
    }

    // init visited lattice - used when counting clusters
    visited = new bool*[size];    
    for (int i = 0 ; i < size ; i++) {
        visited[i] = new bool[size];
    }
}

// clean up
Forest::~Forest() {
    for (int i = 0 ; i < size ; i++) {
        delete[] forest[i];
    }
    delete[] forest;
    delete[] visited;
    // do not delete size, it is caller's responsibility to manage.
}

// Returns the density of green trees in the forest.
double Forest::getDensity() {
    int count = 0;
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == tree) { // count each tree
                count++;
            }
        }
    } // return average
    return (((double) count) / ((double) size*size));
}

// Spreads the fire.
// Returns the number of trees burned down at each timestep.
int Forest::spreadFire() {

    int count = 0;

    Tree** tempForest = new Tree* [size]; // init temp forest for updating
    for (int i = 0; i < size; i++) {
        tempForest[i] = new Tree[size];
        for (int j = 0 ; j < size ; j++) {
            tempForest[i][j] = empty;
        }
    }

    for (int i = 0 ; i < size ; i++) { // spread fire
        for (int j = 0 ; j < size ; j++) {
            // if tree was burnings, spread to neighbours in new forest
            if (forest[i][j] == burning) {
                if (forest[(i+1)%size][j] == tree) {
                    tempForest[(i+1)%size][j] = burning;
                }
                if (forest[i][(j+1)%size] == tree) {
                    tempForest[i][(j+1)%size] = burning;
                }
                if (forest[(i+size-1)%size][j] == tree) {
                    tempForest[(i+size-1)%size][j] = burning;
                }
                if (forest[i][(j+size-1)%size] == tree) {
                    tempForest[i][(j+size-1)%size] = burning;
                }
                // burned down in new forest
                tempForest[i][j] = empty;
                count++; // count each tree burned down
            }
        }
    }

    for (int i = 0 ; i < size ; i++) { // copy over remaining trees
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == tree && tempForest[i][j] == empty) {
                tempForest[i][j] = tree;
            }
        }
    }

    // delete old forest and copy over new forest;
    for (int i = 0 ; i < size ; i++) {
        delete[] forest[i];
    }
    delete[] forest;
    forest = tempForest;

    // return number of trees burned down
    return count;
}

// Make exactly theta attempts at regrowing trees on empty sites
void Forest::regrowTrees() {
    int i, j;

    // Make theta attempts at finding an empty site
    for (int k = 0 ; k < theta ; k++) {
        i = int_dist(generator);
        j = int_dist(generator);
        // grow a tree if found
        if (forest[i][j] == empty) {
            forest[i][j] = tree;
        }  
    }
}

// Regrow all empty sites with probability p
void Forest::regrowAllTrees() {
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == empty && real_dist(generator) < pProb) {
                forest[i][j] = tree;
            }
        }
    }
}

// Strike a random tree with lighning
void Forest::strikeLightning() {
    int i, j;

    // Find a random tree
    do {
        i = int_dist(generator);
        j = int_dist(generator);
    } while(forest[i][j] != tree);

    // Strike it with lightning!
    forest[i][j] = burning;
}

// Strike at trees with lightning with probability f
void Forest::strikeAllLightning() {
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == tree && real_dist(generator) < fProb) {
                forest[i][j] = burning;
            }
        }
    }
}

// Returns the largest cluster in the distribution, and therefore the size
// of the array in which they are stored.
// The passed long pointer reference will be set to the created distribution array.
//
// Individual clusters are counted using a very simple recursize, depth-first algorithm: the
// countCluster function
int Forest::getClusterSizes(long*& distribution) {
    int length = 0;
    sizes = new long[1];
    sizes[0] = 0;
    int count;

    // set all sites to unvisited
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            visited[i][j] = false;
        }
    }


    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) { // for each site
            if (forest[i][j] == tree && !visited[i][j]) { // if it is a tree and not visited
                visited[i][j] = true; // visit it
                count = countCluster(i, j, 1); // get the size of the cluster and visit all trees in it
                if (count > length) { // if cluster size is larger than any previous
                    long* tempSizes = new long[count]; // make bigger array to store
                    for (int k = 0 ; k < count ; k++) {
                        tempSizes[k] = 0;
                    }
                    for (int k = 0 ; k < length ; k++) { // copy over old values
                        tempSizes[k] = sizes[k];
                    }
                    delete[] sizes; // delete old
                    sizes = tempSizes;
                    length = count;
                }
                sizes[count-1]++; // increment cluster size in distribution
            }
        }
    }
    distribution = sizes; // set reference
    return length; // return largest cluster
}

// Recursive function to visit and count all connected trees
// the initial call should have i and j being the coordinates of a tree, and count = 1.
// clusters are counted across the periodic boundaries.
int Forest::countCluster(int i, int j, int count) {
    if (forest[(i+1)%size][j] == tree && !visited[(i+1)%size][j]) { // if i+1 neighbougbour is tree
        visited[(i+1)%size][j] = true; // visit it
        count = countCluster((i+1)%size, j, count+1); // recursively find its unvisited neighbours
    }
    if (forest[i][(j+1)%size] == tree && !visited[i][(j+1)%size]) { // same for j+1
        visited[i][(j+1)%size] = true;
        count = countCluster(i, (j+1)%size, count+1);
    }
    if (forest[(i+size-1)%size][j] == tree && !visited[(i+size-1)%size][j]) { // same for i-1
        visited[(i+size-1)%size][j] = true;
        count = countCluster((i+size-1)%size, j, count+1);
    }
    if (forest[i][(j+size-1)%size] == tree && !visited[i][(j+size-1)%size]) { // same for j-1
        visited[i][(j+size-1)%size] = true;
        count = countCluster(i, (j+size-1)%size, count+1);
    }

    return count; // return number of visited trees
}

// Write a "Plain PPM" image to the passed file of the forest's current state
// See: http://netpbm.sourceforge.net/doc/ppm.html#plainppm
void Forest::writeForestImage(string filename) {
    ofstream file(filename);
    file << "P3\n" << size << " " << size << "\n255\n";
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == empty) {
                file << "255 255 255\t";
            } else if (forest[i][j] == tree) {
                file << "0 255 0\t";
            } else if (forest[i][j] == burning) {
                file << "255 0 0\t";
            }
        }
        file << endl;
    }
    file.close();
}

// Print an ASCII representation of the forest to standard out
void Forest::printForest() {
    for (int i = 0 ; i < size ; i++) {
        for (int j = 0 ; j < size ; j++) {
            if (forest[i][j] == empty) {
                cout << "  ";
            } else if (forest[i][j] == tree) {
                cout << "T ";
            } else if (forest[i][j] == burning) {
                cout << "B ";
            }
        }
        cout << endl;
    }
}


#endif