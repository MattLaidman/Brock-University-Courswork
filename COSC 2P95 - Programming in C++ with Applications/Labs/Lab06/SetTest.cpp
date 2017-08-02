#include <iostream>

#include "Set.h"

int main(int argc, char* argv[]) {
    int sA,sB,sC;
    std::cout<<"Capacity of A [0..255]: ";
    std::cin>>sA;
    Set A(sA);
    std::cout<<"A (\"{x,y,z}\"): ";
    std::cin>>A;
    std::cout<<"Capacity of B: ";
    std::cin>>sB;
    Set B(sB);
    std::cout<<"B: ";
    std::cin>>B;
    std::cout<<"Capacity of C: ";
    std::cin>>sC;
    Set C(sC);
    std::cout<<"C: ";
    std::cin>>C;
    
    std::cout<<std::endl<<"A: "<<A<<std::endl;
    std::cout<<"B: "<<B<<std::endl;
    std::cout<<"C: "<<C<<std::endl<<std::endl;

    std::cout<<"A<=B: "<<((A)<=(B))<<std::endl;
    std::cout<<"A<B: "<<((A)<(B))<<std::endl;
    std::cout<<"A>=B: "<<((A)>=(B))<<std::endl;
    std::cout<<"A>B: "<<((A)>(B))<<std::endl<<std::endl;

    std::cout<<"A+B: "<<((A)+(B))<<std::endl;
    std::cout<<"A^B: "<<((A)^(B))<<std::endl;
    std::cout<<"A-B: "<<((A)-(B))<<std::endl;
    std::cout<<"(A+B)-C: "<<(((A)+(B))-C)<<std::endl;
    std::cout<<"C-(A+B): "<<(C-((A)+(B)))<<std::endl;
    std::cout<<"~A: "<<(~(A))<<std::endl;
    std::cout<<"~(A-B): "<<(~((A)-(B)))<<std::endl;
    std::cout<<"U^A: "<<((+A)^(A))<<std::endl;

    return 0;
}