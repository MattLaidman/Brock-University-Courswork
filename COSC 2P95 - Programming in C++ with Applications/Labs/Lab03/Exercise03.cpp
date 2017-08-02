// Matt Laidman
// 5199807, ml12ef
//
// COSC 2P95 (FALL 2016)
// Lab Exercise 3

#include "Exercise03.h"

const double MAX_X = 6.0;
const double MIN_X = -4.0;
const double MAX_Y = 5.0;
const double MIN_Y = -12.0;


int main (int argc, char *argv[]) {
    int c, g, t;
    double xdiff = MAX_X - MIN_X;
    double ydiff = MAX_Y - MIN_Y;
    
    while(true) { // Loop until quit
        c = getChoice(); // get function choice
        if (c >=0 && c <= 4) { // if valid choice
        if (c == 0) return 0;
            g = getNumGraduations(); // get graduations
            t = getOutputType(); // get output type

            double xs[2 + g]; // x values {min, v1, .., vg, max}
            double ys[2 + g]; // y values {min, v1, .., vg, max}
            double values[(2 + g)*(2 + g)]; // x*y array for function values at (x, y)

            xs[0] = MIN_X; // set x min
            xs[g + 1] = MAX_X; // set x max
            ys[0] = MIN_Y; // set y min
            ys[g + 1] = MAX_Y; // set y max
            for (int i = 1 ; i <= g ; i++) { // for (v1, .., vg)
                xs[i] = MIN_X + (i)*(xdiff/(double)(g + 1)); // get x value
                ys[i] = MIN_Y + (i)*(ydiff/(double)(g + 1)); // get y value
            }

            for (int x = 0 ; x < 2 + g ; x++) { // for each x value
                for (int y = 0 ; y < 2 + g ; y++) { // for each y value
                    switch (c) { // choose function based on choice
                        case 1:
                            values[x*(1 + g) + y] = funcOne(xs[x], ys[y]);
                            break;
                        case 2:
                            values[x*(1 + g) + y] = funcTwo(xs[x], ys[y]);
                            break;
                        case 3:
                            values[x*(1 + g) + y] = funcThree(xs[x], ys[y]);
                            break;
                        case 4:
                            values[x*(1 + g) + y] = funcFour(xs[x], ys[y]);
                            break;
                    }
                }
            }

            if (t == 0) { // if 0 print bitmap
                printBitmap(g, values);
            } else { // else values
                printValues(g, values);
            }
        }
    }
}

// compute sin(x)*cos(y)
double funcOne (double x, double y) {
    return sin(x)*cos(y);
}

// compute sin(x) + cos^2(y/2) - x/y
double funcTwo (double x, double y) {
    return sin(x) + pow(cos(y/2.0), 2.0) - x/y;
}

// compute (1/2)*sin(x) + (1/2)cos(y)
double funcThree (double x, double y) {
    return (1.0/2.0)*sin(x) + (1.0/2.0)*cos(y);
}

//compue (1/2)*sin(x) + xcos(3y)
double funcFour (double x, double y) {
    return (1.0/2.0)*sin(x) + x*cos(3.0*y);
}

// print bitmap version of values
void printBitmap (int g, double *values) {
    for (int x = 0 ; x < 2 + g ; x++) { // for each x value
        for (int y = 0 ; y < 2 + g ; y++) { // for each y value
            if (values[x*(2 + g) + y] < 0) { // if value is negative
                std::cout<<"X"; // print x
            } else { // else value is positive
                std::cout<<"O"; // print O
            }
        }
        std::cout<<std::endl;
    }
    return;
}

// print values
void printValues (int g, double *values) {
    for (int x = 0 ; x < 2 + g ; x++) { // for each x value
        for (int y = 0 ; y < 2 + g ; y++) { // for each y value
            std::cout<<values[x*(2 + g) + y]<<" "; // print value
        }
        std::cout<<std::endl;
    }
    return;
}

// get function choice
int getChoice ( ) {
    int c;
    
    std::cerr<<"Select your function:"<<std::endl;
    std::cerr<<"1. sin(x)cos(y)"<<std::endl;
    std::cerr<<"2. sin(x)+cos^2(y/2)-x/y"<<std::endl;
    std::cerr<<"3. 1/2 sin(x) + 1/2 cos(y)"<<std::endl;
    std::cerr<<"4. 1/2 sin(x) + xcos(3y)"<<std::endl;
    std::cerr<<"0) Quit"<<std::endl;
    std::cin>>c;
    
    return c;
}

// get number of graduations
int getNumGraduations ( ) {
    int g;

    std::cerr<<"Number of graduations per axis: ";
    std::cin>>g;

    return g;
}

// get desired output type
int getOutputType ( ) {
    int t;

    std::cerr<<"(0) Bitmap or (1) Values? ";
    std::cin>>t;

    return t;
}
