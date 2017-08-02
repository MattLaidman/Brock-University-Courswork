// Program for plotting ca rule 54 (problem 2)
#include <iostream>
#include <cmath>
#include "r250.c"

using namespace std;

//definition of the rule
int f(int x0, int x1, int x2) {
	return x0 - 2*x0*x1 + x2 + x1 - x0*x2 + 2*x0*x1*x2 - 2*x2*x1;
}

int main() {

	//initialize RNG
	r250_init(1234);
	int M=100000; //number of space sites
	int T=1000000000; //number of iterations

	int w = 500;
	int values[w];
	int count, total;
	double avdensity;

	int world[M]; //array representing the world (1 if occupied, 0 if empty)
	int newworld[M]; //array representing the world (1 if occupied, 0 if empty)
	int i,t;

	//create initial array with probability p of site in state 1
	double p=0.4;

	for (i=0; i<M; i++) if (dr250()<p) world[i]=1; else world[i]=0;

	// for (int i = 0; i < M; i++)
	// 	cout << world[i] << " ";
	// cout << endl;
/*
	int *firstarr, *secondarr;
	firstarr = world;
	secondarr = newworld;
*/
	for (t=1; t <= T; t++) {	//loop over time

		//first printing the configuration
		/*
		int nt = 0;
		for (i=0; i<M; i++) {
			if (world[i] == 1 && world[(i+1)%M] == 1) nt++;
		}
		cout << t << " " << nt << endl; 
		*/

		count = 0;
		for (i = 0; i < M; i++) count += world[i];

		if (t % w == 0) {
			total = 0;
			for (int j = 0; j < w; j++) total = total + values[j];
			avdensity = double(total) / (double(w)*double(M));
			cout << log(double(t)) << " " << log(abs(0.5 - avdensity)) << endl;
		} else values[t%w] = count;
		
		//now we will update the world, loop over sites EXCEPT BOUNDARY
		for (i=0; i<M; i++) newworld[i]=f(world[i-1], world[i], world[i+1]);

		//now we take care of boundaries (periodic)
		newworld[0] = f(world[M-1], world[0], world[1]);
		newworld[M-1]=f(world[M-2], world[M-1], world[0]);
/*
		for (i=0; i<M; i++) {
			cout << world[i] << " ";
		}
		cout << endl;
*/
		for (i=0; i<M; i++) world[i] = newworld[i]; //copy new world to old
	/*	int* temp = firstarr;
		firstarr = secondarr;
		secondarr = temp;
	*/
	} //end t loop


	return 0;
}
