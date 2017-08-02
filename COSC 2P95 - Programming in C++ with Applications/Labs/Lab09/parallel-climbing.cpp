#include "parallel-climbing.h"

// interrupted signal handler procedure 
void interrupted(int sig) {
    continuing = false;
    return;
}

// printMin procedure
// Display the current global best
void printMin() {
    cout<<"\nMinimum found so far: z = "<<bestZ<<"\n(x = "<<bestX<<", y = "<<bestY<<")"<<endl;
    return;
}

// printMin signal handler procedure
void printMin(int sig) {
    printMin();
    return;
}

// getRandStart function
// return a random value across the entire domain, ([-512..512], [-512..512])
float getRandStart() {
    return ((((float)rand())/(float)RAND_MAX)*1024)-512;
}

// getRandMod function
// return a random modifier [-5..5]
float getRandMod() {
    return ((((float)rand())/(float)RAND_MAX)*10)-5;
}

// eggHolder function
// computes the function value at given x,y
float eggHolder(float x, float y) {
    return (-1)*((y+47)*sin(sqrt(abs(x/2+y+47)))-x*sin(sqrt(abs(x-y-47))));
}

// climb function
// to be called as pthread start routine
void* climb(void* ignore) {
    float x, y, z;
    float xmod, ymod;
    float tempz;

    do {
        // get random init start pos
        x = getRandStart(); // [-512..512]
        y = getRandStart(); // [-512..512]
        z = eggHolder(x, y); // consider start coord
        do {
            // generate 4 steps
            for (int i = 0 ; i < 4 ; i++) {
                xmod = getRandMod();
                ymod = getRandMod();
                tempz = eggHolder(x+xmod, y+ymod);
                // keep track of best.
                if (tempz < z) {
                    z = tempz;
                    x = x+xmod;
                    y = y+ymod;
                }
            }
            // update global best
            pthread_mutex_lock(&bestLock);
            if (z < bestZ) {
                bestX = x;
                bestY = y;
                bestZ = z;
            }
            pthread_mutex_unlock(&bestLock);
        } while(x >= -512 && x <= 512 && y >= -512 && y <= -512);
        // restart if out of domain
    } while(continuing);

    occupied--;

    return 0;
}

// main
int main (int argc, char* argv[]) {

    char c;
    int ic;

    pthread_t threads[8];
    int numThreads;

    bestZ = FLT_MAX;

    srand(time(NULL));

    signal(SIGINT, interrupted);
    signal(SIGUSR1, printMin);

    while(true) {
        do { // get num threads
            cout<<"Number of climbers ([0..8], 0 to quit): ";
            cin>>c;
            ic = ((int)c)-48;
        } while (ic < 0 || ic > 8);
        if (ic == 0) return 0;
        numThreads = ic;
        
        // create threads
        continuing = true;
        for (int i = 0 ; i < numThreads ; i++) {
            pthread_create(&threads[i], NULL, &climb, NULL);
            occupied++;
        }
        cout<<"\nClimbing!"<<endl;
        // wait for threads to finish
        while(continuing && occupied > 0) usleep(100000);

        printMin();
        cout<<endl;
    }

    return 0;
}