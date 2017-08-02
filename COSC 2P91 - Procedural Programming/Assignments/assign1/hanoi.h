#include <stdio.h>
#include <stdlib.h>

struct towers {
	int n;
	struct peg {
		char label;
		int *stack;
		int top;
	} a, b, c;
} pegs;

void init ();
void push (int, struct peg *);
int pop (struct peg *);
void solve (int n, struct peg *, struct peg *, struct peg *, int *);
void draw ();

