#include <iostream>

#include "ballisticdeposition.h"

unsigned long numIterations = 100000000;
unsigned long size = 100000;

int main(int argc, char** argv) {

    BallisticDeposition model(size);

    cout << "0 " << model.getInterfaceWidth() << endl;
    for (unsigned long i = 0 ; i < numIterations ; i++) {
        model.dropParticle();
        if (i % size == 0) {
            cout << float(i)/float(size) << " " << model.getInterfaceWidth() << endl;
        }
    }

    return 0;
}