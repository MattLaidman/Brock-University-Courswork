#include <iostream>

#include "ballisticdeposition.h"


int main(int argc, char** argv) {

    int size = atoi(argv[1]);
    int numIterations = atoi(argv[2]);

    BallisticDeposition model(size);

    cout << "0 " << model.getInterfaceWidth() << endl;
    for (int i = 1 ; i < numIterations ; i++) {
        model.dropParticle();
        if (i % size == 0) {
            cout << i/size << " " << model.getInterfaceWidth() << endl;
        }
    }

    return 0;
}