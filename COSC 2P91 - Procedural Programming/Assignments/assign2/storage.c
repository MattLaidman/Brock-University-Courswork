#include <stdio.h>
#include <stdlib.h>
#include "storage.h"

/**
 * add function will add a given integer, i, to the given list, 
 * head. It was be inserted into the list in ascending order.
 * 
 * @param	head	The head of the list to add to
 * @param	i	The int to add
 * @return	head	The new head to the list
 */

struct node *add (struct node *head, int i) {
	
	struct node *temp;
	struct node *temp2;
	
	if (head == '\0') {		// if null head, init i as head
		head = (struct node *)malloc(sizeof(struct node));
		head->digit = i;
		head->next = '\0';
	} else if (head->digit == 0) {	// if inited, change val
		head->digit = i;
	} else {		// otherwise find insert point
		if (i < head->digit) { 	// if less than head, insert at front
			temp = (struct node *)malloc(sizeof(struct node));
			temp->digit = i;
			temp->next = head;
			head = temp;
		} else if (i != head->digit) {	// ignore if equal
			temp = head;		// loop until insert spot found
			while(temp->next != '\0' && i > temp->next->digit) {
				temp = temp->next;
			}
			if (temp->next == '\0') {	// if largest value, insert at end
				temp->next = (struct node *)malloc(sizeof(struct node));
				temp->next->digit = i;
				temp->next->next = '\0';
			} else if (i != temp->next->digit) {	// else insert between
				temp2 = temp->next;
				temp->next = (struct node *)malloc(sizeof(struct node));
				temp->next->digit = i;
				temp->next->next = temp2;
			}
		}
	}
	
	return head;	// return head to new list
}

/**
 * unadd function removes the front item from the list and
 * prints it to the console if it is not 0.
 * remove function is used by stdio.
 * 
 * @param	head	The head of the list
 * @return 	head	The new head of the list
 */

struct node *unadd (struct node *head) {
	
	struct node * temp;
	
	if (head->digit != 0) {	// if non-zero, print
		printf("%i\n", head->digit);
	}
	temp = head;		// move head forward one
	head = head->next;
	free(temp);		// free memory
	return head;		// return new head pointer
}
