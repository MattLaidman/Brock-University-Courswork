// Example of random number generation
#include <iostream>
#include "rand.c"

using namespace std;

int main(int argc, char* argv[]) {
  //initialize RNG
  r250_init(1234);
  int i;
  
  cout << "Some random numbers  from uniform distribution on [0,1)" << endl;
  for (i=0; i<10; i++)
    cout <<dr250()<<endl;
  cout <<endl;
  
  cout << "Some random integers from the set {0,1,2,...,M-1}" << endl;
  int M=5;
  for (i=0; i<40; i++)
    cout << floor(double(M)*dr250());
  cout << endl << endl;
  
  cout << "Some random numbers {0,1} from Bernoulli distribution, Pr(1)=p, Pr(0)=1-p " << endl;
  double p=0.5;
  for (i=0; i<20; i++)
     if ( dr250()<p ) cout << "1"; else cout <<"0";
  cout <<endl;
  
return 0;
}
