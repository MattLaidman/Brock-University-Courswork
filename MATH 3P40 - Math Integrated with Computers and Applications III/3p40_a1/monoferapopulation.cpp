// Program for computing monofera population as a function of time
#include <iostream>
#include <cmath>
#include "rand.c"

using namespace std;

int main()
{
  //initialize RNG
  r250_init(1234);
  
  int M=100; //number of space sites
  int T=250; //number of iterations
  double lambda=0.15; //probability of monofera birth per unit time
  
  int world[M]; //array representing the world (1 if occupied, 0 if empty)
  int newworld[M]; //array representing the world (1 if occupied, 0 if empty)
  
  int i, target,t,population;
  
   world[M/2]=1; //only one monofera initially
  for (i=0; i<M; i++) if (dr250()<0.01) world[i]=1; else world[i]=0;//initially 1% of monofera
  for (i=0; i<M; i++) newworld[i]=world[i]; //new world initially the same
  
  
  
  for (t=0; t<T; t++) //loop over time
  {
   //first we are printing the population
   population=0;
   for (i=0; i<M; i++) if (world[i]>0) population++; 
   cout << 2*(double(t)-114.113)/(log(81)/((lambda/M)*M))<< " " << double(population)/double(M) <<endl; //note we output U(t), not P(t) 
  //  cout << t << " " << double(population)/double(M) <<endl; //note we output U(t), not P(t) 
   //now we update the world
   for (i=0; i<M; i++) //loop over sites
    {
      if (world[i]>0) //if occupied
      {
	if (dr250()<lambda) {target=floor(double(M)*dr250()); newworld[target]=1;} 
      }
    } //end i loop
   for (i=0; i<M; i++)  world[i]=newworld[i]; //copy new world to old   
  } //end t loop
    
  
return 0;
}
