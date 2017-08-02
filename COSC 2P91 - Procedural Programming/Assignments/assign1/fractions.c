#include "fractions.h"

// fractions.c program produces the reduces all given improper fractions
// to their reduced, proper, form.
//
// Author:		Matt Laidman
// Student Number:	5199807
// Login:		ml12ef
// Course:		COSC 2P91
// Assignment Number:	1
// Date:		February 5, 2015

int main (int argc, char *argv[]) {

	struct fraction frac;

	do {
		frac.num = 0;
		frac.den = 1;
		printf("Enter fraction (0 to end): ");
		scanf("%i/%i", &frac.num, &frac.den);
		if (frac.num != 0) {
			if (frac.den == 0) {
				printf("Division by zero");
			} else {
				reduce(&frac);
				printf("%i/%i\n", frac.num, frac.den);
			}
		}
	} while (frac.num != 0);

	return 0;
}

void reduce (struct fraction *frac) {

	int d;

	if (frac->den != 1) {
		d = getGCD(frac->num, frac->den);
		if (d != 0) {
			frac->num = frac->num/d;
			frac->den = frac->den/d;
		}
	}
}

int getGCD (int n, int d) {

	int t;

	while (d != 0) {
		t = d;
		d = n % d;
		n = t;
	}

	return n;
}
