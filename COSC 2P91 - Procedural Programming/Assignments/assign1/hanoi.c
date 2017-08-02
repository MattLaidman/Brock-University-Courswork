#include "hanoi.h"

// hanoi.c program produces the complete solution with all moves made
// for the towers of hanoi puzzle of the given size.
//
// Author:		Matt Laidman
// Student Number:	5199807
// Login:		ml12ef
// Course:		COSC 2P91
// Assignment Number:	1
// Date:		February 5, 2015

int main (int argc, char *argv[]) {

	if (argc != 2) {
		printf("Invalid Arguments\n");
	} else {
		pegs.n = atoi(argv[1]);
		init();
		printf("Initial\n");
		draw();
		int c = 0;
		solve(pegs.n, &pegs.a, &pegs.c, &pegs.b, &c);
		printf("%i moves\n", c);
	}

	return 0;
}

void init () {

	int i, nDisks = pegs.n;
        pegs.a.stack = malloc(nDisks);
	pegs.a.top = 0;
	pegs.a.label = 'A';
        pegs.b.stack = malloc(nDisks);
	pegs.b.top = 0;
	pegs.b.label = 'B';
        pegs.c.stack = malloc(nDisks);
	pegs.c.top = 0;
	pegs.c.label = 'C';

        for (i = nDisks ; i > 0 ; i--) {
		push(i, &pegs.a);
        }
}

void push (int i, struct peg *p) {

	(*p).top++;
       	(*p).stack[(*p).top] = i;
}

int pop (struct peg *p) {

	(*p).top--;
	return (*p).stack[((*p).top+1)];
}

void solve (int n, struct peg *from, struct peg *to, struct peg *aux, int *c) {

	if (n != 0) {
		if (n == 1) {
			printf("Move 1 from %c to %c\n", (*from).label, (*to).label);
			push(pop(from), to);
			(*c)++;
			draw();
		} else {
			solve(n-1, from, aux, to, c);
			printf("Move 1 from %c to %c\n", (*from).label, (*to).label);
			push(pop(from), to);
			(*c)++;
			draw();
			solve(n-1, aux, to, from, c);
		}
	}
}

void draw () {

	int i, j;
	int nDisks = pegs.n;

	for (i = nDisks ; i > 0 ; i--) {
		if (pegs.a.top < i) {
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                } else {
                        for (j = 0 ; j < nDisks - pegs.a.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("[");
                        for (j = 1 ; j < pegs.a.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j < pegs.a.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("]");
                        for (j = 0 ; j < nDisks - pegs.a.stack[i] ; j++) {
                                printf(" ");
                        }
                }
		printf(" ");
		if (pegs.b.top < i) {
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                } else {
                        for (j = 0 ; j < nDisks - pegs.b.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("[");
                        for (j = 1 ; j < pegs.b.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j < pegs.b.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("]");
                        for (j = 0 ; j < nDisks - pegs.b.stack[i] ; j++) {
                                printf(" ");
                        }
                }
                printf(" ");
		if (pegs.c.top < i) {
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j <= nDisks ; j++) {
                                printf(" ");
                        }
                } else {
                        for (j = 0 ; j < nDisks - pegs.c.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("[");
                        for (j = 1 ; j < pegs.c.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("|");
                        for (j = 1 ; j < pegs.c.stack[i] ; j++) {
                                printf(" ");
                        }
                        printf("]");
                        for (j = 0 ; j < nDisks - pegs.c.stack[i] ; j++) {
                                printf(" ");
                        }
                }
		printf("\n");
	}
	for (i = 0 ; i < ((3 * ((2 * nDisks) + 1)) + 2) ; i++) {
		printf("=");
	}
	printf("\n\n");
}
