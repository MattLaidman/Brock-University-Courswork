#include <iostream>
#include <math.h>

#include "r250.c" // Recommended RNG from Dr. Fuks

using namespace std;


const int SEED = time(NULL); // RNG seed


class BallisticDeposition {
    public:
        BallisticDeposition(int);
        ~BallisticDeposition();
        void dropParticle();
        float getMeanHeight();
        float getInterfaceWidth();
        void printLattice();
    private:
        int* lattice;
        int size;
        int getMax(int, int, int);
};

BallisticDeposition::BallisticDeposition(int size) {
    r250_init(SEED);
    this->size = size;
    lattice = new int[size];
}

BallisticDeposition::~BallisticDeposition() {
    delete[] lattice;
}

void BallisticDeposition::dropParticle() {
    int index = (r250()%size+size)%size;
    lattice[index] = getMax(lattice[((index-1)%size+size)%size], lattice[index]+1, lattice[((index+1)%size+size)%size]);
}

float BallisticDeposition::getMeanHeight() {
    int sum = 0;
    for (int i = 0 ; i < size ; i++) {
        sum += lattice[i];
    }
    return float(sum)/float(size);
}

float BallisticDeposition::getInterfaceWidth() {
    float sum = 0;
    float meanHeight = getMeanHeight();
    for (int i = 0 ; i < size ; i++) {
        sum += pow((float(lattice[i]) - meanHeight), 2.0); 
    }
    return sqrt((1/float(size))*sum);
}

void BallisticDeposition::printLattice() {
    for (int i = 0 ; i < size ; i++) {
        cout << lattice[i] << " ";
    }
    cout << endl;
}

int BallisticDeposition::getMax(int a, int b, int c) {
   int max = ( a < b ) ? b : a;
   return ((max < c) ? c : max);
}
