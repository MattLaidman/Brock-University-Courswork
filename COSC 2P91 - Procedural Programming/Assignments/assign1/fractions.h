#include <stdio.h>
#include <stdlib.h>

struct fraction {

	int num;
	int den;
};

void reduce (struct fraction*);
int getGCD (int, int);
