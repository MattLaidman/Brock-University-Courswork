#include <stdio.h>
#include <stdlib.h>
#include "storage.h"
#include "program.h"

/**
 * COSC 2P91 Assignment 2
 * February 23, 2015
 * 
 * Matt Laidman
 * 5199807
 * ml12ef
 * 
 * **** Listing of Files and Functions ****
 * 
 * 	- program.h
 *	- program.c
 * 		- main
 * 		- dump
 * 	- storage.h
 * 	- storage.c
 * 		- add
 * 		- unadd
 */

/**
 * main function loops accepts integer input to a sorted list
 * until 0 is entered, at which point it dumps the contents
 * of the list to the console. The program exits when EOF (^D)
 * is entered. If the list has any values remaining in it, they
 * will be dumped before exiting.
 * 
 * @param	argc	Unused
 * @param	argv	Unused
 * @return 		0 on successful execution
 */

int main (int argc, char **argv) {
	
	struct node *head = (struct node *)malloc(sizeof(struct node));
	head->digit = 0;
	head->next = '\0';
	
	int i;
	
	printf("Enter numbers one at a time.\n");
	printf("0 to  dump, ^D to dump and exit.\n");
	
	while (scanf("%i", &i) != EOF) {
		if (i == 0) {		// dump on 0 and reinit head
			dump(head);
			head = (struct node *)malloc(sizeof(struct node));
			head->digit = 0;
			head->next = '\0';
			continue;
		} else if (i > 0) {		// ensure not eof
			head = add(head, i);	// add i
		}
	}
	if (head->digit != 0) {
		dump(head);
	}
	
	return 0;
}

/**
 * dump calls the remove function until the list
 * is empty.
 * 
 * @param	head	The head of the list
 */

void dump (struct node *head) {
	
	while (head != '\0') {
		head = unadd(head);	// remove all nodes
	}
}
