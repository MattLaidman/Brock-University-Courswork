#include "factorize.h"

// factorize.c program produces the factors of a given number, or the
// prime factors of the given number depending on user input.
//
// Author:		Matt Laidman
// Student Number:	5199807
// Login:		ml12ef
// Course:		COSC 2P91
// Assignment Number:	1
// Date:		February 5, 2015

int main (int argc, char *argv[]) {

	int in = 0;
	int num;

	do {
		in = getInput();
		if (in != 0) {
			printf("Number to factorize: ");
			scanf("%d", &num);
			if (in == 1) {
				factors(num);
			}
			if (in == 2) {
				pFactors(num);
			}
		}
	} while (in != 0);

	return 0;
}

int getInput ( ) {

	int c = 0;

	printf("1. Factors\n");
	printf("2. Prime Factors\n");
	printf("0. Stop\n");
	do {
		scanf("%d", &c);
	} while (c < 0 || c > 2);

	return c;
}

void factors (int num) {

	int i;

	for (i = 1 ; i <= num ; i++) {;
		if (num % i == 0) {
			printf("%i\t", i);
		}
	}
	printf("\n");
}

void pFactors (int num) {

	int i;

	for (i = 2; i <= num/2; i++) {
		while (num % i == 0) {
			printf("%i\t", i);
			num = num/i;
		}
	}

	if (num > 2) {
		printf ("%i", num);
	}
	printf("\n");
}
