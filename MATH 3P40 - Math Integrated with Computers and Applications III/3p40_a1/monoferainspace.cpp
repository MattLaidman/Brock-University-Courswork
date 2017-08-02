// Program for plotting monofera configurations
#include <iostream>
#include "rand.c"
using namespace std;

int main()
{
   //initialize RNG
  r250_init(1234);
  
  int M=100; //number of space sites
  int T=50; //number of iterations
  double lambda=0.15; //probability of monofera birth per unit time
  int i, target, t; //other needed variables
  
  int world[M]; //array representing the world (1 if occupied, 0 if empty)
  int newworld[M]; //array representing the world (1 if occupied, 0 if empty)
  
  
  for (i=0; i<M; i++) world[i]=0; //set all to zero
  // world[M/2]=1; //only one monofera initially
  for (i=0; i<M; i++) newworld[i]=world[i]; //new world initially the same
  
  
  
  for (t=0; t<T; t++) //loop over time
  {
   //first printing the configuration 
   for (i=0; i<M; i++) cout <<world[i]; cout <<endl;   
   //now we will update the world
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
