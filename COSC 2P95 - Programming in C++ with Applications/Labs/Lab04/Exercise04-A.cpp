// Matt Laidman
// 5199807, ml12ef
//
// COSC 2P95 (FALL 2016)
// Lab Exercise 4, Part B

#include "Exercise04-A.h"

using namespace std;

const double MAX_X = 6.0;
const double MIN_X = -4.0;
const double MAX_Y = 5.0;
const double MIN_Y = -12.0;

int main(int argc, char *argv[]) {
    int g;
    double xdiff = MAX_X - MIN_X;
    double ydiff = MAX_Y - MIN_Y;
    
    g = getNumGraduations(); // get graduations

    double xs[2 + g]; // x values {min, v1, .., vg, max}
    double ys[2 + g]; // y values {min, v1, .., vg, max}
    double values[(2 + g)*(2 + g)]; // x*y array for function values at (x, y)

    xs[0] = MIN_X; // set x min
    xs[g + 1] = MAX_X; // set x max
    ys[0] = MIN_Y; // set y min
    ys[g + 1] = MAX_Y; // set y max
    for (int i = 1 ; i <= g ; i++) { // for (v1, .., vg)
        xs[i] = MIN_X + (i)*(xdiff/((double)(g + 1))); // get x value
        ys[i] = MIN_Y + (i)*(ydiff/((double)(g + 1))); // get y value
    }

    for (int y = 0 ; y < 2 + g ; y++) { // for each x value
        for (int x = 0 ; x < 2 + g ; x++) { // for each y value
            values[x*(2 + g) + y] = getFunctionValue(xs[x], ys[y], 0);
        }
    }
    makePGM(g, values);
    return 0;
}

// get number of graduations
int getNumGraduations() {
    int g;

    std::cerr<<"Number of graduations per axis: ";
    std::cin>>g;

    return g;
}

// compute sin(x)*cos(y)
double getFunctionValue(double x, double y, double z) {
    return cos(z)*(1.0/2.0)*sin(x)+(1.0/2.0)*cos(y);
}

// print bitmap version of values
void makePGM(int g, double *values) {
    char filename[] = "file0001.pgm";
    fstream file(filename, ios::out | ios::trunc);

    file<<"P2"<<endl<<(2+g)<<" "<<(2+g)<<endl<<"255"<<endl;

    for (int y = 0 ; y < 2 + g ; y++) { // for each x value
        for (int x = 0 ; x < 2 + g ; x++) { // for each y value
            file<<setfill(' ')<<setw(3)<<((int)((values[x*(2 + g) + y] + 1)*127.5))<<" "; // print x
        }
        file<<std::endl;
    }
    file.close();

    return;
}