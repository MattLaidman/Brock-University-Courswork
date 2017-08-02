#include "calculate.h"

// calculate.c program produces the fibonacci number at the given index, or
// the factorial of the given number depending on command line options.
//
// Author:		Matt Laidman
// Student Number:	5199807
// Login:		ml12ef
// Course:		COSC 2P91
// Assignment Number:	1
// Date:		February 5, 2015

int main (int argc, char *argv[]) {

        char *fibonacci = "fibonacci";
        char *factorial = "factorial";

        if (argc != 3) {
                printf("Invalid Parameters\n");
        } else {
                if (equalStr(argv[1], fibonacci)) {
                        printf("fib(%i)=%li\n", atoi(argv[2]), fib(atoi(argv[2])));
                } else if (equalStr(argv[1], factorial)) {
                        printf("%i!=%li\n", atoi(argv[2]), fac(atoi(argv[2])));
                } else {
                        printf("Invalid Parameters\n");
                }
        }

        return 0;
}

int equalStr (char *first, char *second) {

        int i = 0;

        while ((first[i] == second[i]) && (first[i] != '\0' || second[i] != '\0')) {
                i++;
        }
        if (first[i] == '\0' && second[i] == '\0') {
                return 1;
        } else {
                return 0;
        }
}

long int fib (int index) {

        long int cur = 0;
        long int oPrev = 1;
        long int prev = 1;
        int i;

        if (index <= 0) return 0;
        if (index == 1 || index == 2) return 1;

        for (i = 2 ; i < index ; i++) {
                cur = oPrev + prev;
                oPrev = prev;
                prev = cur;
        }

        return cur;
}

long int fac (int num) {

	long int total;

	if (num <= 0) return 0;

	total = num;
	num--;

	while (num != 1) {
		total = total * num;
		num--;
	}

	return total;
}

